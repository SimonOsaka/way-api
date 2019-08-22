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
import com.zl.way.mp.api.model.SpUserShopReq;
import com.zl.way.mp.api.model.SpUserShopResp;
import com.zl.way.mp.api.validation.SpUserShopApiValidation;
import com.zl.way.mp.model.SpUserShop;
import com.zl.way.mp.service.SpUserShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpSpUserShopApi")
@RequestMapping("/mp/sp/usershop")
public class SpUserShopApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SpUserShopService spUserShopService;

    @PostMapping("list")
    @WayTokenValidation
    public ResponseResult<SpUserShopResp> queryUserList(@RequestBody SpUserShopReq request) {

        SpUserShop params = BeanMapper.map(request, SpUserShop.class);
        List<SpUserShop> list = spUserShopService.querySpUserShopList(params, request);
        SpUserShopResp response = new SpUserShopResp();
        response.setSpUserShopList(list);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("update")
    @WayTokenValidation
    public ResponseResult<SpUserShopResp> updateSpUserShop(@RequestBody SpUserShopReq request) {

        SpUserShopApiValidation validation = new SpUserShopApiValidation(request).id().userId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }
        SpUserShop params = BeanMapper.map(request, SpUserShop.class);
        spUserShopService.updateSpUserShop(params);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}