package com.zl.way.sp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.sp.api.validation.WayDiscountApiValidation;
import com.zl.way.sp.constants.ApiConstants;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayDiscountBo;
import com.zl.way.sp.model.WayDiscountParam;
import com.zl.way.sp.model.WayDiscountRequest;
import com.zl.way.sp.model.WayDiscountResponse;
import com.zl.way.sp.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("spWayDiscountApi")
@RequestMapping("/sp/discount")
public class WayDiscountApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayDiscountService discountService;

    @PostMapping("list")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayDiscountResponse> queryDiscountList(@RequestBody WayDiscountRequest request) {

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        PageParam pageParam = BeanMapper.map(request, PageParam.class);

        List<WayDiscountBo> discountBoList = discountService.queryDiscountList(param, pageParam);

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBoList(discountBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("get")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayDiscountResponse> getDiscount(@RequestBody WayDiscountRequest request) {

        WayDiscountApiValidation validation = new WayDiscountApiValidation(request).commodityId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getFirstError());
        }

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        WayDiscountBo discountBo = discountService.getDiscount(param);

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBo(discountBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("create")
    @WayTokenValidation(project = "sp")
    public ResponseResult<WayDiscountResponse> createDiscount(@RequestBody WayDiscountRequest request,
        @RequestHeader(ApiConstants.X_USERLOGINID) Long userLoginId) {

        WayDiscountApiValidation validation = new WayDiscountApiValidation(request).commodityId()
            .discountCommodityCate().discountCommodityPrice().discountLimitTimeExpire();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getFirstError());
        }

        WayDiscountParam param = BeanMapper.map(request, WayDiscountParam.class);
        param.setUserLoginId(userLoginId);
        WayDiscountBo discountBo = null;
        try {
            discountBo = discountService.createDiscount(param);
        } catch (BusinessException e) {
            return ResponseResultUtil.wrapException(e);
        }

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBo(discountBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

}
