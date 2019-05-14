package com.zl.way.mp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.mp.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.mp.model.WayCommodityAbstractWord;
import com.zl.way.mp.model.WayCommodityAbstractWordBo;
import com.zl.way.mp.model.WayCommodityAbstractWordCondition;
import com.zl.way.mp.model.WayCommodityAbstractWordParam;
import com.zl.way.mp.service.WayCommodityAbstractWordService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mpWayCommodityAbstractWordService") public class WayCommodityAbstractWordServiceImpl
    implements WayCommodityAbstractWordService {
    private WayCommodityAbstractWordMapper commodityAbstractWordMapper;

    @Autowired public WayCommodityAbstractWordServiceImpl(WayCommodityAbstractWordMapper commodityAbstractWordMapper) {
        this.commodityAbstractWordMapper = commodityAbstractWordMapper;
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodityAbstractWordBo queryAbstractWord(WayCommodityAbstractWordParam param, PageParam pageParam) {

        WayCommodityAbstractWordCondition condition = new WayCommodityAbstractWordCondition();
        condition.setShopCateLeafId(param.getShopCateLeafId());
        condition.setPid(param.getPid());
        condition.setPathPid(param.getPathPid());
        condition.setLeaf(param.getLeaf());
        List<WayCommodityAbstractWord> commodityAbstractWordList =
            commodityAbstractWordMapper.selectByCondition(condition, WayPageRequest.of(pageParam));

        WayCommodityAbstractWordBo commodityAbstractWordBo = new WayCommodityAbstractWordBo();
        commodityAbstractWordBo.setCommodityAbstractWordList(commodityAbstractWordList);
        return commodityAbstractWordBo;
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public WayCommodityAbstractWordBo createAbstractWord(WayCommodityAbstractWordParam param) {
        WayCommodityAbstractWord record = new WayCommodityAbstractWord();
        record.setName(param.getName());
        record.setShopCateLeafId(param.getShopCateLeafId());

        Map<String, Object> jsonData = new HashMap<>(2);
        //默认增加是叶子节点
        jsonData.put("leaf", 1);

        if (null != param.getPid()) {
            WayCommodityAbstractWord selectParentWord = commodityAbstractWordMapper.selectByPrimaryKey(param.getPid());
            if (null != selectParentWord) {
                String parentJsonData = selectParentWord.getJsonData();
                JSONObject json = JSON.parseObject(parentJsonData);
                JSONArray pathJsonArray;
                if (json.containsKey("path")) {
                    // 父节点非根节点
                    pathJsonArray = json.getJSONArray("path");
                } else {
                    // 父节点为根节点
                    pathJsonArray = new JSONArray();
                }
                pathJsonArray.add(param.getPid());
                jsonData.put("path", pathJsonArray);

                //更新父节点为非叶子leaf = 0
                Integer leaf = json.getInteger("leaf");
                if (null != leaf && leaf.equals(1)) {
                    WayCommodityAbstractWord updateLeafRecord = new WayCommodityAbstractWord();
                    updateLeafRecord.setLeaf((byte)0);
                    updateLeafRecord.setId(param.getPid());
                    updateLeafRecord.setUpdateTime(DateTime.now().toDate());
                    commodityAbstractWordMapper.updateLeafByPrimaryKey(updateLeafRecord);
                }
            }
        }
        record.setJsonData(JSON.toJSONString(jsonData));
        commodityAbstractWordMapper.insertSelective(record);
        return BeanMapper.map(record, WayCommodityAbstractWordBo.class);
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public WayCommodityAbstractWordBo updateAbstractWord(WayCommodityAbstractWordParam param) {
        WayCommodityAbstractWord record = new WayCommodityAbstractWord();
        record.setId(param.getId());
        record.setUpdateTime(DateTime.now().toDate());
        record.setName(param.getName());
        commodityAbstractWordMapper.updateByPrimaryKeySelective(record);
        return null;
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public WayCommodityAbstractWordBo deleteAbstractWord(WayCommodityAbstractWordParam param) throws Exception {
        WayCommodityAbstractWord word = commodityAbstractWordMapper.selectByPrimaryKey(param.getId());
        if (word.getLeaf() == 0) {
            throw new Exception("还有子数据，无法删除");
        }

        // 删除叶子
        WayCommodityAbstractWord updateRecord = new WayCommodityAbstractWord();
        updateRecord.setId(param.getId());
        updateRecord.setIsDeleted((byte)1);
        updateRecord.setUpdateTime(DateTime.now().toDate());
        commodityAbstractWordMapper.updateByPrimaryKeySelective(updateRecord);

        // 检查所有同级叶子是否都被删除
        WayCommodityAbstractWordCondition condition = new WayCommodityAbstractWordCondition();
        condition.setPid(word.getPid());
        List<WayCommodityAbstractWord> resultList =
            commodityAbstractWordMapper.selectByCondition(condition, WayPageRequest.ONE);
        // 还有同级叶子节点
        if (CollectionUtils.isNotEmpty(resultList)) {
            return null;
        }

        // 更新父节点为叶子节点leaf = 1
        WayCommodityAbstractWord updateLeafRecord = new WayCommodityAbstractWord();
        updateLeafRecord.setLeaf((byte)1);
        updateLeafRecord.setId(word.getPid());
        updateLeafRecord.setUpdateTime(DateTime.now().toDate());
        commodityAbstractWordMapper.updateLeafByPrimaryKey(updateLeafRecord);
        return null;
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public WayCommodityAbstractWordBo moveAbstractWord(WayCommodityAbstractWordParam param) throws Exception {
        Integer moveFromId = param.getId();
        Integer moveToId = param.getPid();
        WayCommodityAbstractWord word = commodityAbstractWordMapper.selectByPrimaryKey(moveFromId);
        if (word.getLeaf() == 0) {
            throw new Exception("暂不支持非子数据移动");
        }

        // 更新新父节点为非叶子节点leaf = 0，更新新的path
        Map<String, Object> jsonData = new HashMap<>(2);
        jsonData.put("leaf", word.getLeaf());
        WayCommodityAbstractWord selectParentWord = commodityAbstractWordMapper.selectByPrimaryKey(moveToId);
        if (null != selectParentWord) {
            String parentJsonData = selectParentWord.getJsonData();
            JSONObject json = JSON.parseObject(parentJsonData);
            JSONArray pathJsonArray;
            if (json.containsKey("path")) {
                // 父节点非根节点
                pathJsonArray = json.getJSONArray("path");
            } else {
                // 父节点为根节点
                pathJsonArray = new JSONArray();
            }
            pathJsonArray.add(moveToId);
            jsonData.put("path", pathJsonArray);

            WayCommodityAbstractWord moveFromRecord = new WayCommodityAbstractWord();
            moveFromRecord.setJsonData(JSON.toJSONString(jsonData));
            moveFromRecord.setId(moveFromId);
            moveFromRecord.setUpdateTime(DateTime.now().toDate());
            commodityAbstractWordMapper.updateByPrimaryKeySelective(moveFromRecord);

            //更新父节点为非叶子leaf = 0
            Integer leaf = json.getInteger("leaf");
            if (null != leaf && leaf.equals(1)) {
                WayCommodityAbstractWord moveToRecord = new WayCommodityAbstractWord();
                moveToRecord.setLeaf((byte)0);
                moveToRecord.setId(moveToId);
                moveToRecord.setUpdateTime(DateTime.now().toDate());
                commodityAbstractWordMapper.updateLeafByPrimaryKey(moveToRecord);
            }
        }

        // 检查所有同级叶子是否都被删除
        WayCommodityAbstractWordCondition condition = new WayCommodityAbstractWordCondition();
        condition.setPid(word.getPid());
        List<WayCommodityAbstractWord> resultList =
            commodityAbstractWordMapper.selectByCondition(condition, WayPageRequest.ONE);
        // 还有同级叶子节点
        if (CollectionUtils.isEmpty(resultList)) {
            // 更新旧父节点为叶子节点leaf = 1
            WayCommodityAbstractWord updateLeafRecord = new WayCommodityAbstractWord();
            updateLeafRecord.setLeaf((byte)1);
            updateLeafRecord.setId(word.getPid());
            updateLeafRecord.setUpdateTime(DateTime.now().toDate());
            commodityAbstractWordMapper.updateLeafByPrimaryKey(updateLeafRecord);
        }
        return null;
    }
}
