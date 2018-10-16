package com.zl.way.sp.api;

import com.zl.way.sp.api.validation.WayDiscountApiValidation;
import com.zl.way.sp.constants.ApiConstants;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayDiscountBo;
import com.zl.way.sp.model.WayDiscountParam;
import com.zl.way.sp.model.WayDiscountRequest;
import com.zl.way.sp.model.WayDiscountResponse;
import com.zl.way.sp.service.WayDiscountService;
import com.zl.way.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("spWayDiscountApi")
@RequestMapping("/sp/discount")
public class WayDiscountApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayDiscountService discountService;

    @PostMapping("list")
    public ResponseResult<WayDiscountResponse> queryDiscountList(
            @RequestBody WayDiscountRequest request,
            @RequestHeader(ApiConstants.X_TOKEN) String userToken,
            @RequestHeader(ApiConstants.X_USERLOGINID) Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        PageParam pageParam = BeanMapper.map(request, PageParam.class);

        List<WayDiscountBo> discountBoList = discountService.queryDiscountList(param, pageParam);

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBoList(discountBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("get")
    public ResponseResult<WayDiscountResponse> getDiscount(@RequestBody WayDiscountRequest request,
            @RequestHeader(ApiConstants.X_TOKEN) String userToken,
            @RequestHeader(ApiConstants.X_USERLOGINID) Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayDiscountApiValidation validation = new WayDiscountApiValidation(request).commodityId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getFirstError());
        }

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        WayDiscountBo discountBo = discountService.getDiscount(param);

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBo(discountBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("create")
    public ResponseResult<WayDiscountResponse> createDiscount(
            @RequestBody WayDiscountRequest request,
            @RequestHeader(ApiConstants.X_TOKEN) String userToken,
            @RequestHeader(ApiConstants.X_USERLOGINID) Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayDiscountApiValidation validation = new WayDiscountApiValidation(request).commodityId()
                .discountCommodityCate().discountCommodityPrice().discountLimitTimeExpire();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getFirstError());
        }

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        param.setUserLoginId(userLoginId);
        WayDiscountBo discountBo = null;
        try {
            discountBo = discountService.createDiscount(param);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapException(e);
        }

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBo(discountBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
