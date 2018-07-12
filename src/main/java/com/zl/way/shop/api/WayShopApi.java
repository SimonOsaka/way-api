package com.zl.way.shop.api;

import com.zl.way.shop.api.model.WayShopRequest;
import com.zl.way.shop.api.model.WayShopResponse;
import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.model.WayShopBo;
import com.zl.way.shop.model.WayShopParam;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class WayShopApi {

    private Logger logger = LoggerFactory.getLogger(WayShopApi.class);

    @Autowired
    private WayShopService wayShopService;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseResult<WayShopResponse> getShopDetail(
            @RequestBody WayShopRequest wayShopRequest) {

        if (NumberUtil.isNotLongKey(wayShopRequest.getShopId())) {
            logger.info("参数id={}不正确", wayShopRequest.getShopId());
            return ResponseResultUtil.wrapWrongParamResponseResult("店铺信息不存在");
        }

        WayShop wayShop = wayShopService.getPromoShopDetail(wayShopRequest.getShopId());
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
    public ResponseResult<List<WayShopResponse>> queryByCondition(
            @RequestBody WayShopRequest request) {

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

        List<WayShopBo> promoShopList = wayShopService
                .pageWayShopByCondition(wayShopParam, pageParam);
        List<WayShopResponse> wayShopResponseList = BeanMapper
                .mapAsList(promoShopList, WayShopResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayShopResponseList);
    }
}
