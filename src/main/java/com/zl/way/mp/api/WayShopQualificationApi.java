package com.zl.way.mp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.mp.api.validation.WayShopQualificationApiValidation;
import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayShopExtraService;
import com.zl.way.mp.service.WayShopQualificationService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;

@RestController("mpWayShopQualificationApi")
@RequestMapping("/mp/qualification")
public class WayShopQualificationApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private WayShopQualificationService shopQualificationService;

    private WayShopExtraService shopExtraService;

    @Autowired
    public WayShopQualificationApi(WayShopQualificationService shopQualificationService,
        WayShopExtraService shopExtraService) {
        this.shopQualificationService = shopQualificationService;
        this.shopExtraService = shopExtraService;
    }

    @PostMapping(value = "/get")
    public ResponseResult<WayShopQualificationResponse> getShop(@RequestBody WayShopQualificationRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopQualificationApiValidation validation = new WayShopQualificationApiValidation(request);
        validation.id();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopQualificationParam param = BeanMapper.map(request, WayShopQualificationParam.class);
        WayShopQualificationBo shopQualificationBo = shopQualificationService.getShopQualification(param);

        WayShopExtraParam shopExtraParam = new WayShopExtraParam();
        shopExtraParam.setId(request.getShopExtraId());
        WayShopExtraBo shopExtraBo = shopExtraService.getShopExtra(shopExtraParam);

        WayShopQualificationResponse response = new WayShopQualificationResponse();
        response.setShopQualificationBo(shopQualificationBo);
        response.setShopExtraBo(shopExtraBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
