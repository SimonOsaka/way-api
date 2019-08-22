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
import com.zl.way.mp.model.WayShopLogBo;
import com.zl.way.mp.model.WayShopLogParam;
import com.zl.way.mp.model.WayShopLogRequest;
import com.zl.way.mp.model.WayShopLogResponse;
import com.zl.way.mp.service.WayShopLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayShopLogApi")
@RequestMapping("/mp/shop/log")
public class WayShopLogApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopLogService shopLogService;

    @PostMapping("list")
    @WayTokenValidation
    public ResponseResult<WayShopLogResponse> shopLogList(@RequestBody WayShopLogRequest request) {

        WayShopLogParam shopLogParam = BeanMapper.map(request, WayShopLogParam.class);
        List<WayShopLogBo> wayShopLogBoList = shopLogService.queryShopLogList(shopLogParam);

        WayShopLogResponse response = new WayShopLogResponse();
        response.setShopLogBoList(wayShopLogBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
