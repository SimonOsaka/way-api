package com.zl.way.sp.api;

import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.sp.model.WayCommodityRequest;
import com.zl.way.sp.model.WayCommodityResponse;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("spWayCommodityApi")
@RequestMapping("/sp/commodity")
public class WayCommodityApi {

    @Autowired
    private WayCommodityService commodityService;

    @PostMapping(value = "/list")
    public ResponseResult<WayCommodityResponse> queryCommodity(
            @RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayCommodityBo> commodityBoList = commodityService
                .queryCommodityList(commodityParam, pageParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBoList(commodityBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    public ResponseResult<WayCommodityResponse> getCommodity(
            @RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        WayCommodityBo commodityBo = commodityService.getCommodity(commodityParam);
        WayCommodityResponse response = new WayCommodityResponse();
        response.setCommodityBo(commodityBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    public ResponseResult<WayCommodityResponse> createCommodity(
            @RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.createCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/update")
    public ResponseResult<WayCommodityResponse> updateCommodity(
            @RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.updateCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    public ResponseResult<WayCommodityResponse> deleteCommodity(
            @RequestBody WayCommodityRequest request) {

        WayCommodityParam commodityParam = BeanMapper.map(request, WayCommodityParam.class);

        commodityService.deleteCommodity(commodityParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
