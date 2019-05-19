package com.zl.way.mp.api;

import com.zl.way.mp.model.WayCommodityLogBo;
import com.zl.way.mp.model.WayCommodityLogParam;
import com.zl.way.mp.model.WayCommodityLogRequest;
import com.zl.way.mp.model.WayCommodityLogResponse;
import com.zl.way.mp.service.WayCommodityLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("mpWayCommodityLogApi")
@RequestMapping("/mp/commodity/log")
public class WayCommodityLogApi extends BaseRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityLogService commodityLogService;

    @PostMapping("list")
    public ResponseResult<WayCommodityLogResponse> commodityLogList(@RequestBody WayCommodityLogRequest request,
        @RequestHeader("X-Token") String userToken, @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityLogParam commodityLogParam = BeanMapper.map(request, WayCommodityLogParam.class);
        List<WayCommodityLogBo> wayCommodityLogBoList = commodityLogService.queryCommodityLogList(commodityLogParam);

        WayCommodityLogResponse response = new WayCommodityLogResponse();
        response.setCommodityLogBoList(wayCommodityLogBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
