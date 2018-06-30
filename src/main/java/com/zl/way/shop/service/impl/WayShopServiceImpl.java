package com.zl.way.shop.service.impl;

import com.zl.way.shop.mapper.WayShopMapper;
import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.model.WayShopBo;
import com.zl.way.shop.model.WayShopParam;
import com.zl.way.shop.model.WayShopQueryCondition;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class WayShopServiceImpl implements WayShopService {

    @Autowired
    private WayShopMapper wayShopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayShop getPromoShopDetail(Long id) {
        if (NumberUtil.isNotLongKey(id)) {
            return null;
        }
        return wayShopMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopBo> pageWayShopByCondition(WayShopParam wayShopParam, PageParam pageParam) {
        if (null == wayShopParam) {
            return Collections.emptyList();
        }

        WayShopQueryCondition condition = BeanMapper.map(wayShopParam, WayShopQueryCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayShop> wayShopList = wayShopMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(wayShopList)) {
            return Collections.emptyList();
        }

        List<WayShopBo> wayShopBoList = BeanMapper.mapAsList(wayShopList, WayShopBo.class);
        for (WayShopBo wayShopBo : wayShopBoList) {
            if (null != wayShopParam.getClientLng() &&
                    null != wayShopParam.getClientLat() &&
                    null != wayShopBo.getShopLng() &&
                    null != wayShopBo.getShopLat()) {
                BigDecimal distance = GeoUtil.getDistance(wayShopParam.getClientLng(), wayShopParam.getClientLat(), wayShopBo.getShopLng(), wayShopBo.getShopLat());
                wayShopBo.setShopDistance(GeoUtil.getDistanceDesc(distance.intValue()));
            }
        }

        return wayShopBoList;
    }
}
