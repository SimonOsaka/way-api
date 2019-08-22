package com.zl.way.mp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.WayShopQualificationApiValidation;
import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayShopExtraService;
import com.zl.way.mp.service.WayShopQualificationService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayShopQualificationApi")
@RequestMapping("/mp/qualification")
public class WayShopQualificationApi extends BaseRestController {

    private WayShopQualificationService shopQualificationService;

    private WayShopExtraService shopExtraService;

    @Autowired
    public WayShopQualificationApi(WayShopQualificationService shopQualificationService,
        WayShopExtraService shopExtraService) {
        this.shopQualificationService = shopQualificationService;
        this.shopExtraService = shopExtraService;
    }

    @PostMapping(value = "/get")
    @WayTokenValidation
    public ResponseResult<WayShopQualificationResponse> getShop(@RequestBody WayShopQualificationRequest request) {

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
