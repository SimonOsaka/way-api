package com.zl.way.sp.api;

import com.zl.way.base.AbstractBaseRestController;
import com.zl.way.sp.enums.WayShopLogTypeEnum;
import com.zl.way.sp.model.WayShopLogBo;
import com.zl.way.sp.model.WayShopLogParam;
import com.zl.way.sp.model.WayShopLogRequest;
import com.zl.way.sp.model.WayShopLogResponse;
import com.zl.way.sp.service.WayShopLogService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("spWayShopLogApi")
@RequestMapping("/sp/shop/log")
public class WayShopLogApi extends AbstractBaseRestController {

    @Autowired
    private WayShopLogService shopLogService;

    @PostMapping("reject/get")
    public ResponseResult<WayShopLogResponse> getRejectLog(@RequestBody WayShopLogRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayShopLogParam param = new WayShopLogParam();
        param.setShopId(request.getShopId());
        param.setType(WayShopLogTypeEnum.REJECT.getValue());

        WayShopLogBo shopLogBo = shopLogService.getLog(param);

        WayShopLogResponse response = new WayShopLogResponse();
        response.setShopLogBo(shopLogBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
