package com.zl.way.shop.service;

import com.zl.way.shop.model.WayShopFollow;
import com.zl.way.shop.model.WayShopFollowBo;
import com.zl.way.shop.model.WayShopFollowParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayShopFollowService {

    /**
     * 修改为商家已关注
     *
     * @param shopId
     * @return
     */
    WayShopFollow updateShopFollowed(Long shopId, Long userLoginId);

    /**
     * 修改为取消商家关注
     *
     * @param shopId
     * @return
     */
    WayShopFollow cancelShopFollowed(Long shopId, Long userLoginId);

    /**
     * 根据条件查询用户关注商家信息
     *
     * @param wayShopFollowParam
     * @param pageParam
     * @return
     */
    List<WayShopFollowBo> selectByCondition(WayShopFollowParam wayShopFollowParam, PageParam pageParam);
}
