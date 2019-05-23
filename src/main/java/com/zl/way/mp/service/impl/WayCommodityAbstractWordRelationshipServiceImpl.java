package com.zl.way.mp.service.impl;

import java.util.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.mp.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.mp.mapper.WayCommodityAbstractWordRelationshipMapper;
import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayCommodityAbstractWordRelationshipService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service("mpWayCommodityAbstractWordRelationshipService")
public class WayCommodityAbstractWordRelationshipServiceImpl implements WayCommodityAbstractWordRelationshipService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private WayCommodityAbstractWordRelationshipMapper abstractWordRelationshipMapper;
    private WayCommodityAbstractWordMapper abstractWordMapper;

    @Autowired
    public WayCommodityAbstractWordRelationshipServiceImpl(
        WayCommodityAbstractWordRelationshipMapper abstractWordRelationshipMapper,
        WayCommodityAbstractWordMapper abstractWordMapper) {
        this.abstractWordRelationshipMapper = abstractWordRelationshipMapper;
        this.abstractWordMapper = abstractWordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayCommodityAbstractWordRelationshipBo>
        queryAbstractRelationShip(WayCommodityAbstractWordRelationshipParam params, PageParam pageParam) {
        WayCommodityAbstractWordRelationshipCondition condition = new WayCommodityAbstractWordRelationshipCondition();
        condition.setAbstractWordId(params.getAbstractWordId());
        List<WayCommodityAbstractWordRelationship> abstractWordRelationshipList =
            abstractWordRelationshipMapper.selectByCondition(condition, WayPageRequest.of(pageParam));
        if (CollectionUtils.isEmpty(abstractWordRelationshipList)) {
            return Collections.emptyList();
        }
        // 获取所有抽象词
        List<WayCommodityAbstractWord> allAbstractWordList = getAllAbstractWord();
        // new relationship list
        List<WayCommodityAbstractWordRelationshipBo> abstractWordRelationshipBoList =
            new ArrayList<>(abstractWordRelationshipList.size());
        // 循环获取全路径抽象词名称，格式：111/222/333
        for (WayCommodityAbstractWordRelationship abstractWordRelationship : abstractWordRelationshipList) {
            // 转换为bo
            WayCommodityAbstractWordRelationshipBo abstractWordRelationshipBo =
                BeanMapper.map(abstractWordRelationship, WayCommodityAbstractWordRelationshipBo.class);
            // 设置fullname
            List<Integer> relationShipIdList =
                JSONArray.parseArray(abstractWordRelationshipBo.getAbstractWordIds(), Integer.class);
            if (CollectionUtils.isNotEmpty(relationShipIdList)) {
                List<Map<String, Object>> relationshipList = new ArrayList<>(relationShipIdList.size());
                for (Integer relationshipId : relationShipIdList) {
                    String fullName = getRelationshipFullName(relationshipId, allAbstractWordList);
                    Map<String, Object> relationshipMap = new HashMap<>(2);
                    relationshipMap.put("fullName", fullName);
                    relationshipMap.put("abstractWordId", relationshipId);
                    relationshipList.add(relationshipMap);
                    abstractWordRelationshipBo.setAbstractWordRelationshipList(relationshipList);
                }
            } else {
                abstractWordRelationshipBo.setAbstractWordRelationshipList(Collections.emptyList());
            }
            // 添加到relationship list
            abstractWordRelationshipBoList.add(abstractWordRelationshipBo);
        }

        return abstractWordRelationshipBoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAbstractWordRelationship(WayCommodityAbstractWordRelationshipParam params) {
        Integer abstractWordId = params.getAbstractWordId();
        WayCommodityAbstractWord abstractWord = abstractWordMapper.selectByPrimaryKey(abstractWordId);
        // 不存在还扯啥蛋啊~
        if (null == abstractWord) {
            logger.warn("抽象词不存在，id={}", abstractWordId);
            return;
        }
        // 非叶子节点不能关联
        if (!abstractWord.getLeaf().equals((byte)1)) {
            logger.warn("抽象词关联必须是叶子，id={}，leaf={}", abstractWordId, abstractWord.getLeaf());
            return;
        }
        List<Integer> abstractWordIdList = params.getAbstractWordIdList();
        if (CollectionUtils.isNotEmpty(abstractWordIdList)) {
            List<WayCommodityAbstractWord> abstractWordList =
                abstractWordMapper.selectByPrimaryKeys(abstractWordIdList);
            if (CollectionUtils.size(abstractWordList) != abstractWordIdList.size()) {
                logger.warn("要关联的抽象词不存在，ids={}，list={}", JSON.toJSONString(abstractWordIdList),
                    JSON.toJSONString(abstractWordList));
                return;
            }
            for (WayCommodityAbstractWord word : abstractWordList) {
                if (!word.getLeaf().equals((byte)1)) {
                    logger.warn("要关联的抽象词有不是叶子的节点，id={}，leaf={}", word.getId(), word.getLeaf());
                    return;
                } else if (word.getId().equals(abstractWordId)) {
                    logger.warn("关联的抽象词不能关联自己，id={}", word.getId());
                    return;
                }
            }
        }

        WayCommodityAbstractWordRelationship relationship =
            abstractWordRelationshipMapper.selectByAbstractWordId(params.getAbstractWordId());

        WayCommodityAbstractWordRelationship record = new WayCommodityAbstractWordRelationship();
        record.setAbstractWordId(abstractWordId);
        StringBuilder wordSb = new StringBuilder("[");
        if (null != abstractWordIdList) {
            wordSb.append(StringUtils.join(abstractWordIdList, ','));
        }
        wordSb.append("]");
        record.setAbstractWordIds(wordSb.toString());
        if (null == relationship) {
            logger.info("保存-增加抽象词关联，id={}，ids={}", abstractWordId, JSON.toJSONString(abstractWordIdList));
            abstractWordRelationshipMapper.insertSelective(record);
        } else {
            record.setId(relationship.getId());
            record.setUpdateTime(DateTime.now().toDate());
            logger.info("保存-更新抽象词关联，id={}，ids={}", abstractWordId, JSON.toJSONString(abstractWordIdList));
            abstractWordRelationshipMapper.updateByPrimaryKeySelective(record);
        }
    }

    /**
     * 获取所有抽象词
     * 
     * @return
     */
    private List<WayCommodityAbstractWord> getAllAbstractWord() {
        return abstractWordMapper.selectByCondition(new WayCommodityAbstractWordCondition(), null);
    }

    /**
     * 根据id获取指定的抽象词对象
     * 
     * @param id
     * @param abstractWordList
     * @return
     */
    private WayCommodityAbstractWord getAbstractWordById(Integer id, List<WayCommodityAbstractWord> abstractWordList) {
        for (WayCommodityAbstractWord abstractWord : abstractWordList) {
            if (id.equals(abstractWord.getId())) {
                return abstractWord;
            }
        }
        return null;
    }

    /**
     * 组装抽象词路径字符串（父->子）。如：111/222/333
     * 
     * @param wordPathIdList
     * @param allAbstractWordList
     * @return
     */
    private String getWordPathName(List<Integer> wordPathIdList, List<WayCommodityAbstractWord> allAbstractWordList) {
        List<String> abstractWordNameList = new ArrayList<>(wordPathIdList.size());

        for (WayCommodityAbstractWord word : allAbstractWordList) {
            for (Integer pathId : wordPathIdList) {
                if (pathId.equals(word.getId())) {
                    abstractWordNameList.add(word.getName());
                    break;
                }
            }
        }

        return StringUtils.join(abstractWordNameList, "/");
    }

    /**
     * 获取指定的抽象词全路径字符串结果
     * 
     * @param abstractWordId
     * @param allAbstractWordList
     * @return
     */
    private String getRelationshipFullName(Integer abstractWordId, List<WayCommodityAbstractWord> allAbstractWordList) {

        WayCommodityAbstractWord abstractWord = getAbstractWordById(abstractWordId, allAbstractWordList);
        JSONObject jsonObject = JSON.parseObject(abstractWord.getJsonData());
        if (jsonObject.containsKey("path")) {
            JSONArray jsonArray = jsonObject.getJSONArray("path");
            List<Integer> wordPathIdList = jsonArray.toJavaList(Integer.class);
            wordPathIdList.add(abstractWordId);
            return getWordPathName(wordPathIdList, allAbstractWordList);
        }
        return StringUtils.EMPTY;
    }
}
