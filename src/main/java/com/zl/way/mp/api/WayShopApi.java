package com.zl.way.mp.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.WayShopApiValidation;
import com.zl.way.mp.enums.WayShopStatusEnum;
import com.zl.way.mp.exception.BusinessException;
import com.zl.way.mp.model.WayShopBo;
import com.zl.way.mp.model.WayShopParam;
import com.zl.way.mp.model.WayShopRequest;
import com.zl.way.mp.model.WayShopResponse;
import com.zl.way.mp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayShopApi")
@RequestMapping("/mp/shop")
public class WayShopApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopService shopService;

    // @Autowired
    // private ApiValidationService apiValidationService;

    @PostMapping(value = "/list")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> queryShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = new WayShopParam();
        shopParam.setShopName(request.getShopName());
        shopParam.setId(request.getId());
        shopParam.setIsDeleted(request.getShopStatus());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayShopBo> shopBoList = shopService.queryShopList(shopParam, pageParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBoList(shopBoList);
        response.setShopBoTotal(shopService.queryShopCount(shopParam));
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> getShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        // try {
        // apiValidationService.validateUserShop(userLoginId, request.getId());
        // } catch (BusinessException be) {
        // return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        // }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        WayShopBo shopBo = shopService.getShop(shopParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBo(shopBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    /*@PostMapping(value = "/create")
    public ResponseResult<WayShopResponse> createShop(@RequestBody WayShopRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {
    
        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
    
        WayShopApiValidation validation = new WayShopApiValidation(request).shopLogoUrl().shopName()
                .shopCateLeafId().shopAddress().shopTel().shopBusinessTime1().shopLocation()
                .shopInfo();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }
    
        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
        shopParam.setSpUserLoginId(request.getUserLoginId());
        try {
            shopParam.setShopPinyin(PinyinHelper
                    .convertToPinyinString(request.getShopName(), StringUtils.EMPTY,
                            PinyinFormat.WITHOUT_TONE));
            shopParam.setShopPy(PinyinHelper.getShortPinyin(request.getShopName()));
        } catch (PinyinException e) {
        }
        shopService.createShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }*/

    @PostMapping(value = "/update")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> updateShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopName().shopTel().shopAddress()
            .shopBusinessTime1().shopLocation().shopLogoUrl().shopId().shopCateLeafId().shopInfo();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        // try {
        // apiValidationService.validateUserShop(userLoginId, request.getId());
        // } catch (BusinessException be) {
        // return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        // }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
        try {
            shopParam.setShopPinyin(PinyinHelper.convertToPinyinString(request.getShopName(), StringUtils.EMPTY,
                PinyinFormat.WITHOUT_TONE));
            shopParam.setShopPy(PinyinHelper.getShortPinyin(request.getShopName()));
        } catch (PinyinException e) {
        }
        shopService.updateShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> deleteShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        // try {
        // apiValidationService.validateUserShop(userLoginId, request.getId());
        // } catch (BusinessException be) {
        // return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        // }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.deleteShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    /*@PostMapping(value = "/online")
    public ResponseResult<WayShopResponse> online(@RequestBody WayShopRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {
    
        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
    
        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }
    
        try {
            apiValidationService.validateUserShop(userLoginId, request.getId());
        } catch (BusinessException be) {
            return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        }
    
        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
    
        try {
            shopService.onlineShop(shopParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }*/

    /*@PostMapping(value = "/offline")
    public ResponseResult<WayShopResponse> offline(@RequestBody WayShopRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {
    
        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
    
        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }
    
        try {
            apiValidationService.validateUserShop(userLoginId, request.getId());
        } catch (BusinessException be) {
            return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        }
    
        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
    
        shopService.offlineShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }*/

    @PostMapping(value = "/status")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> modifyStatus(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId().shopStatus();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopParam shopParam = new WayShopParam();
        shopParam.setId(request.getId());
        shopParam.setIsDeleted(request.getShopStatus());
        shopParam.setRejectContent(request.getRejectContent());

        try {
            shopService.updateShopStatus(shopParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapWrongParamResponseResult("商家不存在");
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/allShopStatus")
    @WayTokenValidation
    public ResponseResult<WayShopResponse> queryAllShopStatus() {

        WayShopResponse response = new WayShopResponse();
        response.setShopStatusMap(shopService.getAllShopStatus());
        response.setShopStatusDefault(WayShopStatusEnum.AUDITTING.getValue().toString());
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
