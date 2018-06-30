package com.zl.way.discount.service.impl;

import com.zl.way.discount.mapper.WayDiscountMapper;
import com.zl.way.discount.model.WayDiscount;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.model.WayDiscountQueryCondition;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.GeoUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class WayDiscountServiceImpl implements WayDiscountService {

    @Autowired
    private WayDiscountMapper wayDiscountMapper;


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayDiscountBo> selectByCondition(WayDiscountParam wayDiscountParam, PageParam pageParam) {
        WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
        condition.setClientLat(wayDiscountParam.getClientLat());
        condition.setClientLng(wayDiscountParam.getClientLng());
        condition.setDiscountId(wayDiscountParam.getDiscountId());

        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayDiscount> wayDiscountList = wayDiscountMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(wayDiscountList)) {
            return Collections.emptyList();
        }
        List<WayDiscountBo> wayDiscountBoList = BeanMapper.mapAsList(wayDiscountList, WayDiscountBo.class);
        for (WayDiscountBo wayDiscountBo : wayDiscountBoList) {
            if (null != wayDiscountParam.getClientLng() &&
                    null != wayDiscountParam.getClientLat() &&
                    null != wayDiscountBo.getShopLng() &&
                    null != wayDiscountBo.getShopLat()) {
                BigDecimal distance = GeoUtil.getDistance(wayDiscountParam.getClientLng(), wayDiscountParam.getClientLat(), wayDiscountBo.getShopLng(), wayDiscountBo.getShopLat());
                wayDiscountBo.setShopDistance(GeoUtil.getDistanceDesc(distance.intValue()));
            }
        }
        return wayDiscountBoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayDiscount selectOne(Long discountId) {
        WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
        condition.setDiscountId(discountId);
        List<WayDiscount> wayDiscountList = wayDiscountMapper.selectByCondition(condition, null);
        if (CollectionUtils.isNotEmpty(wayDiscountList)) {
            return wayDiscountList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void createDiscount(WayDiscountParam wayDiscountParam) {
        WayDiscount wayDiscount = BeanMapper.map(wayDiscountParam, WayDiscount.class);
        wayDiscount.setShopLng(wayDiscountParam.getClientLng());
        wayDiscount.setShopLat(wayDiscountParam.getClientLat());
        wayDiscountMapper.insertSelective(wayDiscount);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void increateReal(Long discountId) {
        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
        int commodityReal = wayDiscount.getCommodityReal();
        commodityReal = commodityReal + 1;
        WayDiscount upWayDiscount = new WayDiscount();
        upWayDiscount.setId(discountId);
        upWayDiscount.setCommodityReal(commodityReal);
        wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);
    }
}
