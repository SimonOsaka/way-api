package com.zl.way.shop.api;

import com.zl.way.shop.api.model.WayShopFollowRequest;
import com.zl.way.shop.api.model.WayShopFollowResponse;
import com.zl.way.shop.api.model.WayShopRequest;
import com.zl.way.shop.api.model.WayShopResponse;
import com.zl.way.shop.model.*;
import com.zl.way.shop.service.WayShopFollowService;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class WayShopApi {

    private Logger logger = LoggerFactory.getLogger(WayShopApi.class);

    @Autowired
    private WayShopService wayShopService;

    @Autowired
    private WayShopFollowService shopFollowService;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseResult<WayShopResponse> getShopDetail(@RequestBody WayShopRequest wayShopRequest,
        @RequestHeader(value = "token", defaultValue = "") String userToken) {

        if (null != wayShopRequest.getUserLoginId()
            && !TokenUtil.validToken(String.valueOf(wayShopRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayShopRequest.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (NumberUtil.isNotLongKey(wayShopRequest.getShopId())) {
            logger.info("参数id={}不正确", wayShopRequest.getShopId());
            return ResponseResultUtil.wrapWrongParamResponseResult("店铺信息不存在");
        }

        WayShop wayShop =
            wayShopService.getPromoShopDetail(wayShopRequest.getShopId(), wayShopRequest.getUserLoginId());
        if (null == wayShop) {
            logger.info("店铺不存在id={}", wayShopRequest.getShopId());
            return ResponseResultUtil.wrapNotExistResponseResult("店铺信息不存在");
        }
        WayShopResponse wayShopResponse = BeanMapper.map(wayShop, WayShopResponse.class);
        if (null != wayShop.getWayShopCateLeaf()) {
            wayShopResponse.setShopCateLeafName(wayShop.getWayShopCateLeaf().getCateName());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("商家信息返回{}", wayShopResponse);
        }

        return ResponseResultUtil.wrapSuccessResponseResult(wayShopResponse);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseResult<List<WayShopResponse>> queryByCondition(@RequestBody WayShopRequest request) {

        WayShopParam wayShopParam = new WayShopParam();
        wayShopParam.setShopCateLeafId(request.getShopCateLeafId());
        wayShopParam.setShopName(request.getKeywords());
        wayShopParam.setId(request.getShopId());
        wayShopParam.setCommodityName(request.getKeywords());
        wayShopParam.setClientLat(request.getClientLat());
        wayShopParam.setClientLng(request.getClientLng());
        wayShopParam.setCityCode(request.getCityCode());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayShopBo> promoShopList = wayShopService.pageWayShopByCondition(wayShopParam, pageParam);
        List<WayShopResponse> wayShopResponseList = BeanMapper.mapAsList(promoShopList, WayShopResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayShopResponseList);
    }

    @PostMapping("/follow")
    public ResponseResult<WayShopFollowResponse> shopFollowed(@RequestBody WayShopFollowRequest shopFollowRequest,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(shopFollowRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", shopFollowRequest.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        shopFollowService.updateShopFollowed(shopFollowRequest.getShopId(), shopFollowRequest.getUserLoginId());

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/follow/cancel")
    public ResponseResult<WayShopFollowResponse> shopFollowCancelled(
        @RequestBody WayShopFollowRequest shopFollowRequest, @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(shopFollowRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", shopFollowRequest.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        shopFollowService.cancelShopFollowed(shopFollowRequest.getShopId(), shopFollowRequest.getUserLoginId());

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/user/follows")
    public ResponseResult<WayShopFollowResponse> userShopFollows(@RequestBody WayShopFollowRequest shopFollowRequest,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(shopFollowRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", shopFollowRequest.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (null == shopFollowRequest.getUserLoginId()) {
            WayShopFollowResponse response = new WayShopFollowResponse();
            response.setShopFollowList(Collections.emptyList());
            return ResponseResultUtil.wrapSuccessResponseResult(response);
        }
        WayShopFollowParam param = new WayShopFollowParam();
        param.setUserLoginId(shopFollowRequest.getUserLoginId());
        param.setHasFollowed((byte)0);

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(shopFollowRequest.getPageNum());
        pageParam.setPageSize(shopFollowRequest.getPageSize());
        List<WayShopFollowBo> shopFollowBoList = shopFollowService.selectByCondition(param, pageParam);

        WayShopFollowResponse response = new WayShopFollowResponse();
        response.setShopFollowList(shopFollowBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
