package com.zl.way.discount.api;

import com.zl.way.discount.api.model.WayDiscountRequest;
import com.zl.way.discount.api.model.WayDiscountResponse;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.model.WayDiscountRealBo;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/discount")
public class WayDiscountApi {

    private final Logger logger = LoggerFactory.getLogger(WayDiscountApi.class);

    @Autowired
    private WayDiscountService wayDiscountService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseResult<List<WayDiscountResponse>> queryDiscountList(
            @RequestBody WayDiscountRequest wayDiscountRequest) {

        WayDiscountParam wayDiscountParam = new WayDiscountParam();
        wayDiscountParam.setClientLng(wayDiscountRequest.getClientLng());
        wayDiscountParam.setClientLat(wayDiscountRequest.getClientLat());
        wayDiscountParam.setLimitTimeExpireEnable(Boolean.TRUE);
        wayDiscountParam.setCityCode(wayDiscountRequest.getCityCode());
        wayDiscountParam.setRealUserLoginId(wayDiscountRequest.getRealUserLoginId());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(wayDiscountRequest.getPageNum());
        pageParam.setPageSize(wayDiscountRequest.getPageSize());

        List<WayDiscountBo> wayDiscountBoList = wayDiscountService
                .selectByCondition(wayDiscountParam, pageParam);
        if (CollectionUtils.isEmpty(wayDiscountBoList)) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        List<WayDiscountResponse> wayDiscountResponseList = new ArrayList<>();
        for (WayDiscountBo wayDiscountBo : wayDiscountBoList) {
            WayDiscountResponse wayDiscountResponse = BeanMapper
                    .map(wayDiscountBo, WayDiscountResponse.class);
            if (null != wayDiscountBo.getWayDiscountReal()) {
                wayDiscountResponse
                        .setDiscountId(wayDiscountBo.getWayDiscountReal().getDiscountId());
                wayDiscountResponse
                        .setRealUserLoginId(wayDiscountBo.getWayDiscountReal().getUserLoginId());
            }
            wayDiscountResponseList.add(wayDiscountResponse);
        }

        return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponseList);
    }

    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public ResponseResult<WayDiscountResponse> getDiscountDetail(
            WayDiscountRequest wayDiscountRequest, @RequestHeader("token") String userToken) {

        if (NumberUtil.isLongKey(wayDiscountRequest.getRealUserLoginId()) && !TokenUtil
                .validToken(String.valueOf(wayDiscountRequest.getRealUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayDiscountRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayDiscountParam wayDiscountParam = new WayDiscountParam();
        wayDiscountParam.setDiscountId(wayDiscountRequest.getDiscountId());
        wayDiscountParam.setRealUserLoginId(wayDiscountRequest.getRealUserLoginId());

        WayDiscountBo wayDiscountBo = wayDiscountService.selectOne(wayDiscountParam);
        if (null == wayDiscountBo) {
            return ResponseResultUtil.wrapSuccessResponseResult(null);
        }

        WayDiscountResponse wayDiscountResponse = BeanMapper
                .map(wayDiscountBo, WayDiscountResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponse);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult<WayDiscountResponse> createDiscount(
            @RequestBody WayDiscountRequest wayDiscountRequest,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(wayDiscountRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayDiscountRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (StringUtils.isBlank(wayDiscountRequest.getCommodityName())) {

            return ResponseResultUtil.wrapWrongParamResponseResult("商品名称必填");
        }

        if (wayDiscountRequest.getCommodityName().length() > 100) {
            return ResponseResultUtil.wrapWrongParamResponseResult("商品名称超长[限制100字内]");
        }

        if (SensiWordsUtil.isSensiWords(wayDiscountRequest.getCommodityName())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("商品名称包含敏感字");
        }

        WayDiscountParam wayDiscountParam = BeanMapper
                .map(wayDiscountRequest, WayDiscountParam.class);
        wayDiscountService.createDiscount(wayDiscountParam);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @RequestMapping(value = "/real/increase", method = RequestMethod.POST)
    public ResponseResult<WayDiscountRealBo> increaseDiscountReal(
            @RequestBody WayDiscountRequest wayDiscountRequest,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil
                .validToken(String.valueOf(wayDiscountRequest.getRealUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayDiscountRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (NumberUtil.isNotLongKey(wayDiscountRequest.getDiscountId())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("优惠id不能为空");
        }

        if (NumberUtil.isNotLongKey(wayDiscountRequest.getRealUserLoginId())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户登录id不能为空");
        }

        WayDiscountParam wayDiscountParam = BeanMapper
                .map(wayDiscountRequest, WayDiscountParam.class);
        try {
            WayDiscountRealBo wayDiscountRealBo = null;
            if ("real".equalsIgnoreCase(wayDiscountRequest.getRealType())) {
                wayDiscountRealBo = wayDiscountService.increaseReal(wayDiscountParam);
            } else if ("unreal".equalsIgnoreCase(wayDiscountRequest.getRealType())) {
                wayDiscountRealBo = wayDiscountService.increaseUnReal(wayDiscountParam);
            } else {
                return ResponseResultUtil.wrapWrongParamResponseResult("类型不正确");
            }

            return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountRealBo);
        } catch (Exception e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

    }

    @RequestMapping(value = "/real/decrease", method = RequestMethod.POST)
    public ResponseResult<WayDiscountRealBo> decreaseDiscountReal(
            @RequestBody WayDiscountRequest wayDiscountRequest,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil
                .validToken(String.valueOf(wayDiscountRequest.getRealUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayDiscountRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (NumberUtil.isNotLongKey(wayDiscountRequest.getDiscountId())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("优惠id不能为空");
        }

        if (NumberUtil.isNotLongKey(wayDiscountRequest.getRealUserLoginId())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户登录id不能为空");
        }

        WayDiscountParam wayDiscountParam = BeanMapper
                .map(wayDiscountRequest, WayDiscountParam.class);
        try {
            WayDiscountRealBo wayDiscountRealBo = null;
            if ("real".equalsIgnoreCase(wayDiscountRequest.getRealType())) {
                wayDiscountRealBo = wayDiscountService.decreaseReal(wayDiscountParam);
            } else if ("unreal".equalsIgnoreCase(wayDiscountRequest.getRealType())) {
                wayDiscountRealBo = wayDiscountService.decreaseUnReal(wayDiscountParam);
            } else {
                return ResponseResultUtil.wrapWrongParamResponseResult("类型不正确");
            }

            return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountRealBo);
        } catch (Exception e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

    }

}
