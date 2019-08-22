package com.zl.way.sp.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.sp.api.validation.WayShopApiValidation;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.ApiValidationService;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("spWayShopApi")
@RequestMapping("/sp/shop")
public class WayShopApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopService shopService;

    @Autowired
    private ApiValidationService apiValidationService;

    @PostMapping(value = "/list")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> queryShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = new WayShopParam();
        shopParam.setShopName(request.getShopName());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayShopBo> shopBoList = shopService.queryShopList(shopParam, pageParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBoList(shopBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> getShop(@RequestBody WayShopRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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

        WayShopBo shopBo = shopService.getShop(shopParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBo(shopBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> createShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopLogoUrl().shopHeadTel().shopName()
            .shopCateLeafId().shopAddress().shopTel().shopBusinessTime1().shopLocation().shopInfo()
            .qualificationIdcard().qualificationShopInOut().qualificationLicense();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
        shopParam.setSpUserLoginId(request.getUserLoginId());
        WayShopQualificationParam shopQualificationParam = BeanMapper.map(request, WayShopQualificationParam.class);
        shopParam.setShopQualificationParam(shopQualificationParam);
        try {
            shopParam.setShopPinyin(PinyinHelper.convertToPinyinString(request.getShopName(), StringUtils.EMPTY,
                PinyinFormat.WITHOUT_TONE));
            shopParam.setShopPy(PinyinHelper.getShortPinyin(request.getShopName()));
        } catch (PinyinException e) {
        }
        try {
            shopService.createShop(shopParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapException(e);
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/update")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> updateShop(@RequestBody WayShopRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopHeadTel().updateType().shopName()
            .shopTel().shopAddress().shopBusinessTime1().shopLocation().shopLogoUrl().shopId().shopCateLeafId()
            .shopInfo().qualificationIdcard().qualificationShopInOut().qualificationLicense();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        try {
            apiValidationService.validateUserShop(userLoginId, request.getId());
        } catch (BusinessException be) {
            return ResponseResultUtil.wrapWrongParamResponseResult(be.getMessage());
        }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
        WayShopQualificationParam shopQualificationParam = BeanMapper.map(request, WayShopQualificationParam.class);
        shopParam.setShopQualificationParam(shopQualificationParam);
        try {
            shopParam.setShopPinyin(PinyinHelper.convertToPinyinString(request.getShopName(), StringUtils.EMPTY,
                PinyinFormat.WITHOUT_TONE));
            shopParam.setShopPy(PinyinHelper.getShortPinyin(request.getShopName()));
        } catch (PinyinException e) {
        }
        try {
            shopService.updateShop(shopParam);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapException(e);
        }
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> deleteShop(@RequestBody WayShopRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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

        shopService.deleteShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/online")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> online(@RequestBody WayShopRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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
    }

    @PostMapping(value = "/offline")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopResponse> offline(@RequestBody WayShopRequest request,
        @RequestHeader("X-userLoginId") Long userLoginId) {

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
    }
}
