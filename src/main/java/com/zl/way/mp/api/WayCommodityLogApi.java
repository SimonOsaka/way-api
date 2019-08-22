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
import com.zl.way.mp.model.WayCommodityLogBo;
import com.zl.way.mp.model.WayCommodityLogParam;
import com.zl.way.mp.model.WayCommodityLogRequest;
import com.zl.way.mp.model.WayCommodityLogResponse;
import com.zl.way.mp.service.WayCommodityLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayCommodityLogApi")
@RequestMapping("/mp/commodity/log")
public class WayCommodityLogApi extends BaseRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayCommodityLogService commodityLogService;

    @PostMapping("list")
    @WayTokenValidation
    public ResponseResult<WayCommodityLogResponse> commodityLogList(@RequestBody WayCommodityLogRequest request) {

        WayCommodityLogParam commodityLogParam = BeanMapper.map(request, WayCommodityLogParam.class);
        List<WayCommodityLogBo> wayCommodityLogBoList = commodityLogService.queryCommodityLogList(commodityLogParam);

        WayCommodityLogResponse response = new WayCommodityLogResponse();
        response.setCommodityLogBoList(wayCommodityLogBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
