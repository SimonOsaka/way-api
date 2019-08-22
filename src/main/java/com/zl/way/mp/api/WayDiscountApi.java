package com.zl.way.mp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.WayDiscountApiValidation;
import com.zl.way.mp.model.WayDiscountBo;
import com.zl.way.mp.model.WayDiscountParam;
import com.zl.way.mp.model.WayDiscountRequest;
import com.zl.way.mp.model.WayDiscountResponse;
import com.zl.way.mp.service.WayDiscountService;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpWayDiscountApi")
@RequestMapping("/mp/discount")
public class WayDiscountApi {

    private final Logger logger = LoggerFactory.getLogger(WayDiscountApi.class);

    @Autowired
    private WayDiscountService wayDiscountService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @WayTokenValidation
    public ResponseResult<WayDiscountResponse> queryDiscountList(@RequestBody WayDiscountRequest wayDiscountRequest) {

        WayDiscountParam wayDiscountParam = new WayDiscountParam();
        wayDiscountParam.setDiscountId(wayDiscountRequest.getDiscountId());
        wayDiscountParam.setCityCode(wayDiscountRequest.getCityCode());
        wayDiscountParam.setCommodityId(wayDiscountRequest.getCommodityId());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(wayDiscountRequest.getPageNum());
        pageParam.setPageSize(wayDiscountRequest.getPageSize());

        List<WayDiscountBo> wayDiscountBoList = wayDiscountService.selectByCondition(wayDiscountParam, pageParam);

        WayDiscountResponse response = new WayDiscountResponse();
        response.setDiscountBoList(wayDiscountBoList);
        response.setDiscountTotal(wayDiscountService.countByCondition(wayDiscountParam));
        return ResponseResultUtil.wrapSuccessResponseResult(response);

    }

    /*@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public ResponseResult<WayDiscountResponse> getDiscountDetail(
            WayDiscountRequest wayDiscountRequest, @RequestHeader("token") String userToken) {
    
        if (NumberUtil.isLongKey(wayDiscountRequest.getRealUserLoginId()) && !TokenUtil
                .validToken(String.valueOf(wayDiscountRequest.getRealUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", wayDiscountRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
    
        WayDiscountParam wayDiscountParam = new WayDiscountParam();
        wayDiscountParam.setDiscountId(wayDiscountRequest.getDiscountId());
        wayDiscountParam.setRealUserLoginId(wayDiscountRequest.getRealUserLoginId());
    
        WayDiscountBo wayDiscountBo = wayDiscountService.selectOne(wayDiscountParam);
        if (null == wayDiscountBo) {
            return ResponseResultUtil.wrapSuccessResponseResult(null);
        }
    
        WayDiscountResponse wayDiscountResponse = BeanMapper
                .map(wayDiscountBo, WayDiscountResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayDiscountResponse);
    }*/

    @PostMapping("/delete")
    @WayTokenValidation
    public ResponseResult<WayDiscountResponse> getDiscountDetail(@RequestBody WayDiscountRequest request) {

        WayDiscountApiValidation validation = new WayDiscountApiValidation(request).discountId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayDiscountParam discountParam = new WayDiscountParam();
        discountParam.setDiscountId(request.getDiscountId());

        wayDiscountService.deleteByCondition(discountParam);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
