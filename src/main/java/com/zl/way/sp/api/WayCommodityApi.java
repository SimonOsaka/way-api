package com.zl.way.sp.api;

import com.zl.way.sp.api.validation.WayCommodityApiValidation;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.sp.model.WayCommodityRequest;
import com.zl.way.sp.model.WayCommodityResponse;
import com.zl.way.sp.service.ApiValidationService;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("spWayCommodityApi")
@RequestMapping("/sp/commodity")
public class WayCommodityApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityService commodityService;

    @Autowired
    private ApiValidationService apiValidationService;

    @PostMapping(value = "/list")
    public ResponseResult<WayCommodityResponse> queryCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayCommodityBo> commodityBoList = commodityService
                .queryCommodityList(commodityParam, pageParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBoList(commodityBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    public ResponseResult<WayCommodityResponse> getCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityApiValidation validation = new WayCommodityApiValidation(request).commodityId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        try {
            apiValidationService.validateUserCommodity(userLoginId, request.getId());
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        WayCommodityBo commodityBo = commodityService.getCommodity(commodityParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    public ResponseResult<WayCommodityResponse> createCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityApiValidation validation = new WayCommodityApiValidation(request).shopId()
                .name().price().imgUrl();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.createCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/update")
    public ResponseResult<WayCommodityResponse> updateCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityApiValidation validation = new WayCommodityApiValidation(request).commodityId()
                .shopId().name().price().imgUrl();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        try {
            apiValidationService.validateUserCommodity(userLoginId, request.getId());
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.updateCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    public ResponseResult<WayCommodityResponse> deleteCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityApiValidation validation = new WayCommodityApiValidation(request).commodityId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        try {
            apiValidationService.validateUserCommodity(userLoginId, request.getId());
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.deleteCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/online")
    public ResponseResult<WayCommodityResponse> onlineCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);
        commodityParam.setIsDeleted((byte) 0);

        WayCommodityBo commodityBo = commodityService.updateStatus(commodityParam);

        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/offline")
    public ResponseResult<WayCommodityResponse> offlineCommodity(
            @RequestBody WayCommodityRequest request, @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);
        WayCommodityBo commodityBo = commodityService.offlineCommodity(commodityParam);

        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
