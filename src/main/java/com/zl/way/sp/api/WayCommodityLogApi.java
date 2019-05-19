package com.zl.way.sp.api;

import com.zl.way.base.AbstractBaseRestController;
import com.zl.way.sp.enums.WayCommodityLogTypeEnum;
import com.zl.way.sp.model.WayCommodityLogBo;
import com.zl.way.sp.model.WayCommodityLogParam;
import com.zl.way.sp.model.WayCommodityLogRequest;
import com.zl.way.sp.model.WayCommodityLogResponse;
import com.zl.way.sp.service.WayCommodityLogService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("spWayCommodityLogApi")
@RequestMapping("/sp/commodity/log")
public class WayCommodityLogApi extends AbstractBaseRestController {

    @Autowired
    private WayCommodityLogService commodityLogService;

    @PostMapping("reject/get")
    public ResponseResult<WayCommodityLogResponse> getRejectLog(@RequestBody WayCommodityLogRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityLogParam param = new WayCommodityLogParam();
        param.setCommodityId(request.getCommodityId());
        param.setType(WayCommodityLogTypeEnum.REJECT.getValue());

        WayCommodityLogBo commodityLogBo = commodityLogService.getLog(param);

        WayCommodityLogResponse response = new WayCommodityLogResponse();
        response.setCommodityLogBo(commodityLogBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
