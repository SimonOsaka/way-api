package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.model.WayCommodity;
import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityCondition;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("spWayCommodityService")
public class WayCommodityServiceImpl implements WayCommodityService {

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Override
    public List<WayCommodityBo> queryCommodityList(WayCommodityParam shopParam,
            PageParam pageParam) {

        Pageable pageable = WayPageRequest.of(pageParam);
        WayCommodityCondition condition = BeanMapper.map(shopParam, WayCommodityCondition.class);
        List<WayCommodity> commodityList = commodityMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(commodityList)) {
            return Collections.emptyList();
        }
        return BeanMapper.mapAsList(commodityList, WayCommodityBo.class);
    }

    @Override
    public WayCommodityBo getCommodity(WayCommodityParam commodityParam) {

        Pageable pageable = WayPageRequest.of(1, 1);
        WayCommodityCondition condition = BeanMapper
                .map(commodityParam, WayCommodityCondition.class);
        List<WayCommodity> shopList = commodityMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return null;
        }
        return BeanMapper.map(shopList.get(0), WayCommodityBo.class);
    }

    @Override
    public WayCommodityBo createCommodity(WayCommodityParam commodityParam) {

        WayCommodity wayShopRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        commodityMapper.insertSelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayCommodityBo.class);
    }

    @Override
    public WayCommodityBo updateCommodity(WayCommodityParam commodityParam) {

        WayCommodity wayShopRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        commodityMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayCommodityBo.class);
    }

    @Override
    public WayCommodityBo deleteCommodity(WayCommodityParam commodityParam) {

        WayCommodity wayShopRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayShopRecord.setIsDeleted((byte) 1);
        commodityMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayCommodityBo.class);
    }
}
