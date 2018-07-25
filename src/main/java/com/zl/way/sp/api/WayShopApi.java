package com.zl.way.sp.api;

import com.zl.way.sp.model.WayShopBo;
import com.zl.way.sp.model.WayShopParam;
import com.zl.way.sp.model.WayShopRequest;
import com.zl.way.sp.model.WayShopResponse;
import com.zl.way.sp.service.WayShopService;
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

@RestController("spWayShopApi")
@RequestMapping("/sp/shop")
public class WayShopApi {

    @Autowired
    private WayShopService shopService;

    @PostMapping(value = "/list")
    public ResponseResult<WayShopResponse> queryShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = new WayShopParam();
        shopParam.setShopName(request.getShopName());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<WayShopBo> shopBoList = shopService.queryShopList(shopParam, pageParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBoList(shopBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/get")
    public ResponseResult<WayShopResponse> getShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        WayShopBo shopBo = shopService.getShop(shopParam);
        WayShopResponse response = new WayShopResponse();
        response.setShopBo(shopBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    public ResponseResult<WayShopResponse> createShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.createShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/update")
    public ResponseResult<WayShopResponse> updateShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.updateShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    public ResponseResult<WayShopResponse> deleteShop(@RequestBody WayShopRequest request) {

        WayShopParam shopParam = BeanMapper.map(request, WayShopParam.class);

        shopService.deleteShop(shopParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
