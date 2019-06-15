package com.zl.way.commodity.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.commodity.api.model.WayCommodityRequest;
import com.zl.way.commodity.api.model.WayCommodityResponse;
import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.commodity.service.WayCommodityService;
import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.service.WayShopService;
import com.zl.way.util.*;

@RestController
@RequestMapping("/commodity")
public class WayCommodityApi {

    private Logger logger = LoggerFactory.getLogger(WayCommodityApi.class);

    @Autowired
    private WayCommodityService wayCommodityService;

    @Autowired
    private WayShopService wayShopService;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseResult<WayCommodityResponse> getCommodityDetail(@RequestBody WayCommodityRequest request) {
        if (NumberUtil.isNotLongKey(request.getCommodityId())) {
            logger.info("参数id={}不正确", request.getCommodityId());
            return ResponseResultUtil.wrapWrongParamResponseResult("商品信息不存在");
        }

        WayCommodity wayCommodity = wayCommodityService.getCommodityDetail(request.getCommodityId());
        if (null == wayCommodity) {
            logger.info("商品不存在id={}", request.getCommodityId());
            return ResponseResultUtil.wrapNotExistResponseResult("商品信息不存在");
        }

        WayShop wayShop = wayShopService.getPromoShopDetail(wayCommodity.getShopId());
        if (null == wayShop) {
            logger.info("商家不存在id={}", wayShop.getId());
            return ResponseResultUtil.wrapNotExistResponseResult("商家信息不存在");
        }

        WayCommodityResponse wayCommodityResponse = BeanMapper.map(wayCommodity, WayCommodityResponse.class);
        wayCommodityResponse.setShopName(wayShop.getShopName());
        wayCommodityResponse.setShopAddress(wayShop.getShopAddress());
        wayCommodityResponse.setShopLogoUrl(wayShop.getShopLogoUrl());

        if (logger.isDebugEnabled()) {
            logger.debug("商品信息返回{}", wayCommodityResponse);
        }

        return ResponseResultUtil.wrapSuccessResponseResult(wayCommodityResponse);
    }

    @RequestMapping(value = "/queryByCondition", method = RequestMethod.POST)
    public ResponseResult<List<WayCommodityResponse>> queryByCondition(@RequestBody WayCommodityRequest request) {

        WayCommodityParam wayCommodityParam = new WayCommodityParam();
        wayCommodityParam.setName(request.getCommodityName());
        wayCommodityParam.setShopId(request.getShopId());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayCommodityBo> wayCommodityBoList =
            wayCommodityService.pageCommodityByCondition(wayCommodityParam, pageParam);
        List<WayCommodityResponse> wayCommodityResponseList =
            BeanMapper.mapAsList(wayCommodityBoList, WayCommodityResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayCommodityResponseList);
    }

    @RequestMapping(value = "/queryRelationCommodity", method = RequestMethod.POST)
    public ResponseResult<List<WayCommodityResponse>> queryRelationCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityParam wayCommodityParam = new WayCommodityParam();
        wayCommodityParam.setId(request.getCommodityId());

        List<WayCommodityBo> wayCommodityBoList = wayCommodityService.queryRelationCommodity(wayCommodityParam);
        List<WayCommodityResponse> wayCommodityResponseList =
            BeanMapper.mapAsList(wayCommodityBoList, WayCommodityResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayCommodityResponseList);
    }
}
