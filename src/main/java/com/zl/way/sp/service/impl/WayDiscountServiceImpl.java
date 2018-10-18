package com.zl.way.sp.service.impl;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.mapper.WayDiscountMapper;
import com.zl.way.sp.mapper.WayShopMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("spWayDiscountService")
public class WayDiscountServiceImpl implements WayDiscountService {

    @Autowired
    private WayDiscountMapper discountMapper;

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Autowired
    private WayShopMapper shopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayDiscountBo> queryDiscountList(WayDiscountParam param, PageParam pageParam) {

        WayDiscountCondition condition = BeanMapper.map(param, WayDiscountCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayDiscount> discountList = discountMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(discountList)) {
            return Collections.emptyList();
        }
        return BeanMapper.mapAsList(discountList, WayDiscountBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayDiscountBo getDiscount(WayDiscountParam param) {

        WayDiscountCondition condition = BeanMapper.map(param, WayDiscountCondition.class);
        Pageable pageable = WayPageRequest.ONE;
        condition.setLimitTimeExpireEnable(true);
        List<WayDiscount> discountList = discountMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(discountList)) {
            return null;
        }
        return BeanMapper.map(discountList.get(0), WayDiscountBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WayDiscountBo createDiscount(WayDiscountParam param) throws BusinessException {

        Long commodityId = param.getCommodityId();
        WayDiscountCondition discountCondition = new WayDiscountCondition();
        discountCondition.setCommodityId(commodityId);
        discountCondition.setLimitTimeExpireEnable(true);//未过期
        List<WayDiscount> discountList = discountMapper
                .selectByCondition(discountCondition, WayPageRequest.ONE);
        if (CollectionUtils.isNotEmpty(discountList)) {
            throw new BusinessException("此商品优惠已存在");
        }

        WayCommodity wayCommodity = commodityMapper.selectByPrimaryKey(commodityId);

        if (param.getCommodityPrice().compareTo(wayCommodity.getPrice()) >= 0) {
            throw new BusinessException("优惠价格应低于商品价格");
        }

        Long shopId = wayCommodity.getShopId();
        WayShop wayShop = shopMapper.selectByPrimaryKey(shopId);

        WayDiscount record = BeanMapper.map(param, WayDiscount.class);
        record.setCommodityName(wayCommodity.getName());
        record.setCommodityCate(param.getCommodityCate());
        record.setAdCode(wayShop.getAdCode());
        record.setCityCode(wayShop.getCityCode());
        record.setCommodityPrice(param.getCommodityPrice());
        record.setLimitTimeExpire(param.getLimitTimeExpire());
        record.setShopPosition(wayShop.getShopAddress());
        record.setShopLng(wayShop.getShopLng());
        record.setShopLat(wayShop.getShopLat());
        record.setUserLoginId(param.getUserLoginId());
        record.setCommodityId(commodityId);

        discountMapper.insertSelective(record);
        return BeanMapper.map(record, WayDiscountBo.class);
    }
}
