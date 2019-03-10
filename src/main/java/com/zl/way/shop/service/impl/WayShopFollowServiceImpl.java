package com.zl.way.shop.service.impl;

import com.zl.way.shop.mapper.WayShopFollowMapper;
import com.zl.way.shop.model.WayShopFollow;
import com.zl.way.shop.model.WayShopFollowBo;
import com.zl.way.shop.model.WayShopFollowParam;
import com.zl.way.shop.model.WayShopFollowQueryCondition;
import com.zl.way.shop.service.WayShopFollowService;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class WayShopFollowServiceImpl implements WayShopFollowService {

    @Autowired
    private WayShopFollowMapper shopFollowMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopFollow updateShopFollowed(Long shopId, Long userLoginId) {

        WayShopFollow shopFollow = getShopFollow(shopId, userLoginId);
        if (null == shopFollow) {
            shopFollow = new WayShopFollow();
            shopFollow.setShopId(shopId);
            shopFollow.setUserLoginId(userLoginId);
            shopFollowMapper.insertSelective(shopFollow);
        } else {
            Long id = shopFollow.getId();

            WayShopFollow updateWayShopFollow = new WayShopFollow();
            updateWayShopFollow.setId(id);
            updateWayShopFollow.setHasFollowed((byte) 0);

            shopFollowMapper.updateByPrimaryKeySelective(updateWayShopFollow);
        }
        shopFollow.setHasFollowed((byte) 0);
        return shopFollow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopFollow cancelShopFollowed(Long shopId, Long userLoginId) {

        WayShopFollow shopFollow = getShopFollow(shopId, userLoginId);
        if (null == shopFollow) {
            return null;
        }
        Long id = shopFollow.getId();

        WayShopFollow updateWayShopFollow = new WayShopFollow();
        updateWayShopFollow.setId(id);
        updateWayShopFollow.setHasFollowed((byte) 1);

        shopFollowMapper.updateByPrimaryKeySelective(updateWayShopFollow);
        return shopFollow;
    }

    private WayShopFollow getShopFollow(Long shopId, Long userLoginId) {

        WayShopFollowQueryCondition condition = new WayShopFollowQueryCondition();
        condition.setShopId(shopId);
        condition.setUserLoginId(userLoginId);

        List<WayShopFollowBo> shopFollowList = shopFollowMapper
                .selectByCondition(condition, WayPageRequest.ONE);
        if (CollectionUtils.isEmpty(shopFollowList)) {
            return null;
        }

        return shopFollowList.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopFollowBo> selectByCondition(WayShopFollowParam wayShopFollowParam,
            PageParam pageParam) {

        WayShopFollowQueryCondition condition = new WayShopFollowQueryCondition();
        condition.setShopId(wayShopFollowParam.getShopId());
        condition.setUserLoginId(wayShopFollowParam.getUserLoginId());
        condition.setHasFollowed(wayShopFollowParam.getHasFollowed());

        List<WayShopFollowBo> shopFollowList = shopFollowMapper
                .selectByCondition(condition, WayPageRequest.of(pageParam));
        if (CollectionUtils.isEmpty(shopFollowList)) {
            return Collections.emptyList();
        }
        return shopFollowList;
    }
}
