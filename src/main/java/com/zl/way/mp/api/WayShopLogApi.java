package com.zl.way.mp.api;

import com.zl.way.mp.model.WayShopLogBo;
import com.zl.way.mp.model.WayShopLogParam;
import com.zl.way.mp.model.WayShopLogRequest;
import com.zl.way.mp.model.WayShopLogResponse;
import com.zl.way.mp.service.WayShopLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("mpWayShopLogApi")
@RequestMapping("/mp/shop/log")
public class WayShopLogApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopLogService shopLogService;

    @PostMapping("list")
    public ResponseResult<WayShopLogResponse> shopLogList(@RequestBody WayShopLogRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopLogParam shopLogParam = BeanMapper.map(request, WayShopLogParam.class);
        List<WayShopLogBo> wayShopLogBoList = shopLogService.queryShopLogList(shopLogParam);

        WayShopLogResponse response = new WayShopLogResponse();
        response.setShopLogBoList(wayShopLogBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
