package com.zl.way.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.commodity.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.commodity.mapper.model.WayCommodityAbstractWordCondition;
import com.zl.way.commodity.model.WayCommodityAbstractWord;
import com.zl.way.shop.mapper.WayShopFollowMapper;
import com.zl.way.shop.mapper.WayShopMapper;
import com.zl.way.shop.model.*;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class WayShopServiceImpl implements WayShopService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopMapper wayShopMapper;

    @Autowired
    private WayShopFollowMapper shopFollowMapper;

    @Autowired
    private WayCommodityAbstractWordMapper commodityAbstractWordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayShop getPromoShopDetail(Long id) {

        return getPromoShopDetail(id, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayShop getPromoShopDetail(Long id, Long userLoginId) {

        if (NumberUtil.isNotLongKey(id)) {
            logger.info("商家详情不正确id={}", id);
            return null;
        }
        WayShop wayShop = wayShopMapper.selectByPrimaryKey(id);
        if (null != wayShop) {
            WayShopFollow follow = new WayShopFollow();
            if (null == userLoginId) {
                follow.setHasFollowed((byte)1);
            } else {
                WayShopFollowQueryCondition followQueryCondition = new WayShopFollowQueryCondition();
                followQueryCondition.setUserLoginId(userLoginId);
                followQueryCondition.setShopId(id);
                List<WayShopFollowBo> shopFollowBoList =
                    shopFollowMapper.selectByCondition(followQueryCondition, WayPageRequest.ONE);

                if (CollectionUtils.isEmpty(shopFollowBoList)) {
                    follow.setHasFollowed((byte)1);
                } else {
                    follow.setHasFollowed(shopFollowBoList.get(0).getHasFollowed());
                }
            }
            wayShop.setFollow(follow);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("商家详情sql结果={}", JSON.toJSONString(wayShop));
        }

        return wayShop;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopBo> pageWayShopByCondition(WayShopParam wayShopParam, PageParam pageParam) {

        if (null == wayShopParam) {
            return Collections.emptyList();
        }

        WayShopQueryCondition condition = BeanMapper.map(wayShopParam, WayShopQueryCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        logger.info("商铺列表sql条件{}{}", condition, pageable);

        WayCommodityAbstractWordCondition wordCondition = new WayCommodityAbstractWordCondition();
        wordCondition.setName(wayShopParam.getCommodityName());
        List<WayCommodityAbstractWord> wordList =
            commodityAbstractWordMapper.selectByCondition(wordCondition, WayPageRequest.ONE);

        List<WayShop> wayShopList;
        if (CollectionUtils.isNotEmpty(wordList)) {
            condition.setPathPid(wordList.get(0).getId());
            wayShopList = wayShopMapper.selectByAbstractWord(condition, pageable);
        } else {
            wayShopList = wayShopMapper.selectByCondition(condition, pageable);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("商铺列表结果={}", wayShopList);
        }
        if (CollectionUtils.isEmpty(wayShopList)) {
            return Collections.emptyList();
        }

        List<WayShopBo> wayShopBoList = BeanMapper.mapAsList(wayShopList, WayShopBo.class);
        for (WayShopBo wayShopBo : wayShopBoList) {
            if (null != wayShopParam.getClientLng() && null != wayShopParam.getClientLat()
                && null != wayShopBo.getShopLng() && null != wayShopBo.getShopLat()) {
                BigDecimal distance = GeoUtil.getDistance(wayShopParam.getClientLng(), wayShopParam.getClientLat(),
                    wayShopBo.getShopLng(), wayShopBo.getShopLat());
                wayShopBo.setShopDistance(GeoUtil.getDistanceDesc(distance.intValue()));
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("商铺列表组装结果={}", JSON.toJSONString(wayShopBoList));
        }
        return wayShopBoList;
    }
}
