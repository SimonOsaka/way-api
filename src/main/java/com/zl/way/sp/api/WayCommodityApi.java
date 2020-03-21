package com.zl.way.sp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.sp.api.validation.WayCommodityApiValidation;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.sp.model.WayCommodityRequest;
import com.zl.way.sp.model.WayCommodityResponse;
import com.zl.way.sp.service.ApiValidationService;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("spWayCommodityApi")
@RequestMapping("/sp/commodity")
public class WayCommodityApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityService commodityService;

    @Autowired
    private ApiValidationService apiValidationService;

    @PostMapping(value = "/list")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> queryCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayCommodityBo> commodityBoList = commodityService.queryCommodityList(commodityParam, pageParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBoList(commodityBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> getCommodity(@RequestBody WayCommodityRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> createCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityApiValidation validation =
            new WayCommodityApiValidation(request).shopId().name()./*price().*/imgUrl().abstractWordIds();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);
        try {
            commodityService.createCommodity(commodityParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/update")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> updateCommodity(@RequestBody WayCommodityRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

        WayCommodityApiValidation validation =
            new WayCommodityApiValidation(request).commodityId().shopId().name()./*price().*/imgUrl().abstractWordIds();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        try {
            apiValidationService.validateUserCommodity(userLoginId, request.getId());
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        try {
            commodityService.updateCommodity(commodityParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> deleteCommodity(@RequestBody WayCommodityRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> onlineCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);
        commodityParam.setIsDeleted((byte)0);

        WayCommodityBo commodityBo = commodityService.updateStatus(commodityParam);

        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/offline")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityResponse> offlineCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);
        WayCommodityBo commodityBo = commodityService.offlineCommodity(commodityParam);

        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
