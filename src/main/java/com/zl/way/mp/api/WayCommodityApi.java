package com.zl.way.mp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.WayCommodityApiValidation;
import com.zl.way.mp.enums.WayCommodityStatusEnum;
import com.zl.way.mp.exception.BusinessException;
import com.zl.way.mp.model.WayCommodityBo;
import com.zl.way.mp.model.WayCommodityParam;
import com.zl.way.mp.model.WayCommodityRequest;
import com.zl.way.mp.model.WayCommodityResponse;
import com.zl.way.mp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayCommodityApi")
@RequestMapping("/mp/commodity")
public class WayCommodityApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityService commodityService;

    // @Autowired
    // private ApiValidationService apiValidationService;

    @PostMapping(value = "/list")
    @WayTokenValidation
    public ResponseResult<WayCommodityResponse> queryCommodity(@RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayCommodityBo> commodityBoList = commodityService.queryCommodityList(commodityParam, pageParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBoList(commodityBoList);
        response.setCommodityTotal(commodityService.queryCommodityCount(commodityParam));
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    /*@PostMapping(value = "/get")
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
    }*/

    /*@PostMapping(value = "/create")
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
    }*/

    /*@PostMapping(value = "/update")
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
    }*/

    /*@PostMapping(value = "/delete")
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
    }*/

    @PostMapping(value = "/status")
    @WayTokenValidation
    public ResponseResult<WayCommodityResponse> modifyCommodityStatus(@RequestBody WayCommodityRequest request) {

        WayCommodityApiValidation validation = new WayCommodityApiValidation(request).commodityId().commodityStatus();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        try {
            commodityService.updateCommodityStatus(commodityParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapNotExistResponseResult("商品不存在");
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/allCommodityStatus")
    @WayTokenValidation
    public ResponseResult<WayCommodityResponse> queryAllCommodityStatus() {

        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityStatusMap(commodityService.getAllCommodityStatus());
        response.setCommodityStatusDefault(WayCommodityStatusEnum.AUDITTING.getValue().toString());
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
