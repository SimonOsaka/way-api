package com.zl.way.sp.service.impl;

import com.zl.way.sp.enums.WayCommodityStatusEnum;
import com.zl.way.sp.enums.WayShopStatusEnum;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.mapper.WayDiscountJpushMapper;
import com.zl.way.sp.mapper.WayDiscountMapper;
import com.zl.way.sp.mapper.WayShopMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("spWayDiscountService") public class WayDiscountServiceImpl implements WayDiscountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WayDiscountMapper discountMapper;

    private final WayCommodityMapper commodityMapper;

    private final WayShopMapper shopMapper;

    private final WayDiscountJpushMapper discountJpushMapper;

    @Autowired public WayDiscountServiceImpl(WayDiscountMapper discountMapper, WayCommodityMapper commodityMapper,
        WayShopMapper shopMapper, WayDiscountJpushMapper discountJpushMapper) {
        this.discountMapper = discountMapper;
        this.commodityMapper = commodityMapper;
        this.shopMapper = shopMapper;
        this.discountJpushMapper = discountJpushMapper;
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayDiscountBo> queryDiscountList(WayDiscountParam param, PageParam pageParam) {

        WayDiscountCondition condition = BeanMapper.map(param, WayDiscountCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayDiscount> discountList = discountMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(discountList)) {
            return Collections.emptyList();
        }
        return BeanMapper.mapAsList(discountList, WayDiscountBo.class);
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
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

    @Override @Transactional(rollbackFor = Exception.class) public WayDiscountBo createDiscount(WayDiscountParam param)
        throws BusinessException {

        Long commodityId = param.getCommodityId();
        WayDiscountCondition discountCondition = new WayDiscountCondition();
        discountCondition.setCommodityId(commodityId);
        discountCondition.setLimitTimeExpireEnable(true);//未过期
        List<WayDiscount> discountList = discountMapper.selectByCondition(discountCondition, WayPageRequest.ONE);
        if (CollectionUtils.isNotEmpty(discountList)) {
            throw new BusinessException("此商品优惠已存在");
        }

        WayCommodity wayCommodity = commodityMapper.selectByPrimaryKey(commodityId);
        if (!WayCommodityStatusEnum.NORMAL.getValue().equals(wayCommodity.getIsDeleted())) {
            throw new BusinessException("商品未上线，无法发布优惠");
        }

        if (param.getCommodityPrice().compareTo(wayCommodity.getPrice()) >= 0) {
            throw new BusinessException("优惠价格应低于商品价格");
        }

        Long shopId = wayCommodity.getShopId();
        WayShop wayShop = shopMapper.selectByPrimaryKey(shopId);
        if (!WayShopStatusEnum.NORMAL.getValue().equals(wayShop.getIsDeleted())) {
            throw new BusinessException("商家未上线，无法发布优惠");
        }

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

        try {
            WayDiscountJpush discountJpushRecord = new WayDiscountJpush();
            discountJpushRecord.setDiscountId(record.getId());
            discountJpushMapper.insertSelective(discountJpushRecord);
        } catch (Exception e) {
            logger.error("sp创建jpush异常", e);
        }
        return BeanMapper.map(record, WayDiscountBo.class);
    }
}
