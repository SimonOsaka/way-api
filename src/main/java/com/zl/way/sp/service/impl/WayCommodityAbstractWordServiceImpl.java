package com.zl.way.sp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.sp.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.sp.model.WayCommodityAbstractWord;
import com.zl.way.sp.model.WayCommodityAbstractWordBo;
import com.zl.way.sp.model.WayCommodityAbstractWordCondition;
import com.zl.way.sp.model.WayCommodityAbstractWordParam;
import com.zl.way.sp.service.WayCommodityAbstractWordService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("spWayCommodityAbstractWordService")
public class WayCommodityAbstractWordServiceImpl
        implements WayCommodityAbstractWordService {
    private WayCommodityAbstractWordMapper commodityAbstractWordMapper;

    @Autowired
    public WayCommodityAbstractWordServiceImpl(WayCommodityAbstractWordMapper commodityAbstractWordMapper) {
        this.commodityAbstractWordMapper = commodityAbstractWordMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodityAbstractWordBo queryAbstractWord(WayCommodityAbstractWordParam param, PageParam pageParam) {

        WayCommodityAbstractWordCondition condition = new WayCommodityAbstractWordCondition();
        condition.setShopCateLeafId(param.getShopCateLeafId());
        condition.setPid(param.getPid());
        condition.setPathPid(param.getPathPid());
        condition.setLeaf(param.getLeaf());
        List<WayCommodityAbstractWord> commodityAbstractWordList =
                commodityAbstractWordMapper.selectByCondition(condition, null);

        condition = new WayCommodityAbstractWordCondition();
        List<WayCommodityAbstractWord> allAbstractWordList =
                commodityAbstractWordMapper.selectByCondition(condition, null);

        List<WayCommodityAbstractWordBo> commodityAbstractWordBoList = new ArrayList<>(commodityAbstractWordList.size());
        for (WayCommodityAbstractWord abstractWord : commodityAbstractWordList) {
            WayCommodityAbstractWordBo commodityAbstractWordBo = BeanMapper.map(abstractWord, WayCommodityAbstractWordBo.class);
            JSONObject jsonObject = JSON.parseObject(commodityAbstractWordBo.getJsonData());
            if (jsonObject.containsKey("path")) {
                JSONArray jsonArray = jsonObject.getJSONArray("path");
                List<Integer> wordPathIdList = jsonArray.toJavaList(Integer.class);
                String pathName = getWordPathName(wordPathIdList, allAbstractWordList);
                commodityAbstractWordBo.setAbstractWordPathName(pathName + commodityAbstractWordBo.getName());
                commodityAbstractWordBoList.add(commodityAbstractWordBo);
            }
        }

        WayCommodityAbstractWordBo commodityAbstractWordBo = new WayCommodityAbstractWordBo();
        commodityAbstractWordBo.setCommodityAbstractWordBoList(commodityAbstractWordBoList);
        return commodityAbstractWordBo;
    }

    private String getWordPathName(List<Integer> wordPathIdList, List<WayCommodityAbstractWord> commodityAbstractWordList) {
        StringBuilder pathNamesSb = new StringBuilder();
        for (WayCommodityAbstractWord word : commodityAbstractWordList) {
            for (Integer pathId : wordPathIdList) {
                if (pathId.equals(word.getId())) {
                    pathNamesSb.append(word.getName()).append("/");
                }
            }
        }

        return pathNamesSb.toString();
    }

}
