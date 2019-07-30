package com.zl.way.mp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.mp.api.validation.WayShopExtraApiValidation;
import com.zl.way.mp.model.WayShopExtraParam;
import com.zl.way.mp.model.WayShopExtraRequest;
import com.zl.way.mp.model.WayShopResponse;
import com.zl.way.mp.service.WayShopExtraService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;

@RestController("mpWayShopExtraApi")
@RequestMapping("/mp/shop/extra")
public class WayShopExtraApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private WayShopExtraService shopExtraService;

    @Autowired
    public WayShopExtraApi(WayShopExtraService shopExtraService) {
        this.shopExtraService = shopExtraService;
    }

    @PostMapping(value = "/owner/manager")
    public ResponseResult<WayShopResponse> changeManager(@RequestBody WayShopExtraRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopExtraApiValidation validation = new WayShopExtraApiValidation(request).shopExtraId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopExtraParam shopExtraParam = new WayShopExtraParam();
        shopExtraParam.setId(request.getId());

        shopExtraService.changeToManager(shopExtraParam);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/owner/self")
    public ResponseResult<WayShopResponse> changeSelf(@RequestBody WayShopExtraRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopExtraApiValidation validation = new WayShopExtraApiValidation(request).shopExtraId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayShopExtraParam shopExtraParam = new WayShopExtraParam();
        shopExtraParam.setId(request.getId());

        shopExtraService.changeToSelf(shopExtraParam);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
