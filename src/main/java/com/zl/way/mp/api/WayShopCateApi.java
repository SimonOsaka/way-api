package com.zl.way.mp.api;

import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayShopCateService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("mpWayShopCateApi") @RequestMapping("/mp/shop/cate") public class WayShopCateApi {

    @Autowired private WayShopCateService cateService;

    @PostMapping("/root") public ResponseResult<WayShopCateResponse> queryCateRoot() {

        List<WayShopCateRootBo> cateRootBoList = cateService.queryCateRoot();
        WayShopCateResponse response = new WayShopCateResponse();
        response.setCateRootBoList(cateRootBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/leaf")
    public ResponseResult<WayShopCateResponse> queryCateLeaf(@RequestBody WayShopCateRequest request) {

        WayShopCateLeafParam leafParam = BeanMapper.map(request, WayShopCateLeafParam.class);
        List<WayShopCateLeafBo> cateLeafBoList = cateService.queryCateLeaf(leafParam);
        WayShopCateResponse response = new WayShopCateResponse();
        response.setCateLeafBoList(cateLeafBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/all") public ResponseResult<WayShopCateResponse> queryCateAll() {

        List<WayShopCateRootBo> cateRootBoList = cateService.queryCateRoot();
        WayShopCateResponse response = new WayShopCateResponse();
        response.setCateRootBoList(cateRootBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }
}
