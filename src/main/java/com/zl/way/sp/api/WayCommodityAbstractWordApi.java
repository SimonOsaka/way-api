package com.zl.way.sp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.sp.model.WayCommodityAbstractWordBo;
import com.zl.way.sp.model.WayCommodityAbstractWordParam;
import com.zl.way.sp.model.WayCommodityAbstractWordRequest;
import com.zl.way.sp.model.WayCommodityAbstractWordResponse;
import com.zl.way.sp.service.WayCommodityAbstractWordService;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;

/**
 * sp抽象词api
 *
 * @author xuzhongliang
 */
@RestController("spWayCommodityAbstractWordApi")
@RequestMapping("/sp/commodity/abstractword")
public class WayCommodityAbstractWordApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private WayCommodityAbstractWordService commodityAbstractWordService;

    @Autowired
    public WayCommodityAbstractWordApi(WayCommodityAbstractWordService commodityAbstractWordService) {
        this.commodityAbstractWordService = commodityAbstractWordService;
    }

    @PostMapping(value = "/list")
    public ResponseResult<WayCommodityAbstractWordResponse> queryCommodityAbstractWord(
        @RequestBody WayCommodityAbstractWordRequest request, @RequestHeader("X-Token") String userToken,
        @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        WayCommodityAbstractWordParam param = new WayCommodityAbstractWordParam();
        param.setShopCateLeafId(request.getShopCateLeafId());
        param.setPid(request.getPid());
        param.setPathPid(request.getPathPid());
        param.setLeaf(request.getLeaf());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        WayCommodityAbstractWordBo commodityAbstractWordBo =
            commodityAbstractWordService.queryAbstractWord(param, pageParam);

        WayCommodityAbstractWordResponse response = new WayCommodityAbstractWordResponse();
        response.setCommodityAbstractWordBo(commodityAbstractWordBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

}