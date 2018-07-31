package com.zl.way.sp.api;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.sp.api.validation.WayShopApiValidation;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayShopBo;
import com.zl.way.sp.model.WayShopParam;
import com.zl.way.sp.model.WayShopRequest;
import com.zl.way.sp.model.WayShopResponse;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("spWayShopApi")
@RequestMapping("/sp/shop")
public class WayShopApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopService shopService;

    @PostMapping(value = "/list")
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
    public ResponseResult<WayShopResponse> getShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        WayShopBo shopBo = shopService.getShop(shopParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBo(shopBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    public ResponseResult<WayShopResponse> createShop(@RequestBody WayShopRequest request,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopApiValidation validation = new WayShopApiValidation(request).shopLogoUrl().shopName()
                .shopCateLeafId().shopAddress().shopTel().shopBusinessTime().shopLocation();
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
    }

    @PostMapping(value = "/update")
    public ResponseResult<WayShopResponse> updateShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopName().shopTel()
                .shopAddress().shopBusinessTime().shopLocation().shopLogoUrl().shopId()
                .shopCateLeafId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }
        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);
        try {
            shopParam.setShopPinyin(PinyinHelper
                    .convertToPinyinString(request.getShopName(), StringUtils.EMPTY,
                            PinyinFormat.WITHOUT_TONE));
            shopParam.setShopPy(PinyinHelper.getShortPinyin(request.getShopName()));
        } catch (PinyinException e) {
        }
        shopService.updateShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    public ResponseResult<WayShopResponse> deleteShop(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.deleteShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/online")
    public ResponseResult<WayShopResponse> online(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
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
    public ResponseResult<WayShopResponse> offline(@RequestBody WayShopRequest request) {

        WayShopApiValidation validation = new WayShopApiValidation(request).shopId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.offlineShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
