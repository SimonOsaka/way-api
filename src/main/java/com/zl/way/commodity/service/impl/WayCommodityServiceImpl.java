package com.zl.way.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.commodity.mapper.WayCommodityMapper;
import com.zl.way.commodity.mapper.model.WayCommodityQueryCondition;
import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.commodity.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.NumberUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class WayCommodityServiceImpl implements WayCommodityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityMapper wayCommodityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodity getCommodityDetail(Long id) {
        if (NumberUtil.isNotLongKey(id)) {
            logger.info("商品详情id={}", id);
            return null;
        }

        WayCommodity wayCommodity = wayCommodityMapper.selectByPrimaryKey(id);
        if (logger.isDebugEnabled()) {
            logger.debug("商品详情结果={}", JSON.toJSONString(wayCommodity, true));
        }
        return wayCommodity;
    }

    @Override
    public List<WayCommodityBo> pageCommodityByCondition(WayCommodityParam wayCommodityParam, PageParam pageParam) {
        if (null == wayCommodityParam) {
            logger.info("商品列表查询参数={}", wayCommodityParam);
            return Collections.emptyList();
        }

        WayCommodityQueryCondition condition = BeanMapper.map(wayCommodityParam, WayCommodityQueryCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        logger.info("商品列表sql条件condition={},pageable={}", condition, pageable);

        List<WayCommodity> wayCommodityList = wayCommodityMapper.selectByCondition(condition, pageable);
        if (logger.isDebugEnabled()) {
            logger.debug("商品列表sql结果={}", JSON.toJSONString(wayCommodityList, true));
        }
        return BeanMapper.mapAsList(wayCommodityList, WayCommodityBo.class);
    }

}
