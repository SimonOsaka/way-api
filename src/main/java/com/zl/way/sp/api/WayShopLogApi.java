package com.zl.way.sp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.base.AbstractBaseRestController;
import com.zl.way.sp.enums.WayShopLogTypeEnum;
import com.zl.way.sp.model.WayShopLogBo;
import com.zl.way.sp.model.WayShopLogParam;
import com.zl.way.sp.model.WayShopLogRequest;
import com.zl.way.sp.model.WayShopLogResponse;
import com.zl.way.sp.service.WayShopLogService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("spWayShopLogApi")
@RequestMapping("/sp/shop/log")
public class WayShopLogApi extends AbstractBaseRestController {

    @Autowired
    private WayShopLogService shopLogService;

    @PostMapping("reject/get")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayShopLogResponse> getRejectLog(@RequestBody WayShopLogRequest request) {

        WayShopLogParam param = new WayShopLogParam();
        param.setShopId(request.getShopId());
        param.setType(WayShopLogTypeEnum.REJECT.getValue());

        WayShopLogBo shopLogBo = shopLogService.getLog(param);

        WayShopLogResponse response = new WayShopLogResponse();
        response.setShopLogBo(shopLogBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
