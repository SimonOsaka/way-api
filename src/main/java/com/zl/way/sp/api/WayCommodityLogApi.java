package com.zl.way.sp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.base.AbstractBaseRestController;
import com.zl.way.sp.enums.WayCommodityLogTypeEnum;
import com.zl.way.sp.model.WayCommodityLogBo;
import com.zl.way.sp.model.WayCommodityLogParam;
import com.zl.way.sp.model.WayCommodityLogRequest;
import com.zl.way.sp.model.WayCommodityLogResponse;
import com.zl.way.sp.service.WayCommodityLogService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("spWayCommodityLogApi")
@RequestMapping("/sp/commodity/log")
public class WayCommodityLogApi extends AbstractBaseRestController {

    @Autowired
    private WayCommodityLogService commodityLogService;

    @PostMapping("reject/get")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayCommodityLogResponse> getRejectLog(@RequestBody WayCommodityLogRequest request) {

        WayCommodityLogParam param = new WayCommodityLogParam();
        param.setCommodityId(request.getCommodityId());
        param.setType(WayCommodityLogTypeEnum.REJECT.getValue());

        WayCommodityLogBo commodityLogBo = commodityLogService.getLog(param);

        WayCommodityLogResponse response = new WayCommodityLogResponse();
        response.setCommodityLogBo(commodityLogBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
