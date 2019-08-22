package com.zl.way.mp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.WayShopExtraApiValidation;
import com.zl.way.mp.model.WayShopExtraParam;
import com.zl.way.mp.model.WayShopExtraRequest;
import com.zl.way.mp.model.WayShopResponse;
import com.zl.way.mp.service.WayShopExtraService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

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
    @WayTokenValidation
    public ResponseResult<WayShopResponse> changeManager(@RequestBody WayShopExtraRequest request) {

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
    @WayTokenValidation
    public ResponseResult<WayShopResponse> changeSelf(@RequestBody WayShopExtraRequest request) {

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
