package com.zl.way.mp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.model.WayCommodityAbstractWordBo;
import com.zl.way.mp.model.WayCommodityAbstractWordParam;
import com.zl.way.mp.model.WayCommodityAbstractWordRequest;
import com.zl.way.mp.model.WayCommodityAbstractWordResponse;
import com.zl.way.mp.service.WayCommodityAbstractWordService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

/**
 * mp抽象词api
 *
 * @author xuzhongliang
 */
@RestController("mpWayCommodityAbstractWordApi")
@RequestMapping("/mp/commodity/abstractword")
public class WayCommodityAbstractWordApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private WayCommodityAbstractWordService commodityAbstractWordService;

    @Autowired
    public WayCommodityAbstractWordApi(WayCommodityAbstractWordService commodityAbstractWordService) {
        this.commodityAbstractWordService = commodityAbstractWordService;
    }

    @PostMapping(value = "/list")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        queryCommodityAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = new WayCommodityAbstractWordParam();
        param.setShopCateLeafId(request.getShopCateLeafId());
        param.setPid(request.getPid());
        param.setPathPid(request.getPathPid());
        param.setLeaf(request.getLeaf());
        param.setName(request.getName());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        WayCommodityAbstractWordBo commodityAbstractWordBo =
            commodityAbstractWordService.queryAbstractWord(param, pageParam);

        WayCommodityAbstractWordResponse response = new WayCommodityAbstractWordResponse();
        response.setCommodityAbstractWordBo(commodityAbstractWordBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/query")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        queryAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = new WayCommodityAbstractWordParam();
        param.setShopCateLeafId(request.getShopCateLeafId());
        param.setLeaf(request.getLeaf());
        param.setName(request.getName());

        WayCommodityAbstractWordBo commodityAbstractWordBo = commodityAbstractWordService.queryAbstractWord(param);

        WayCommodityAbstractWordResponse response = new WayCommodityAbstractWordResponse();
        response.setCommodityAbstractWordBo(commodityAbstractWordBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/create")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        createCommodityAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = BeanMapper.map(request, WayCommodityAbstractWordParam.class);

        WayCommodityAbstractWordBo wordBo = commodityAbstractWordService.createAbstractWord(param);

        WayCommodityAbstractWordResponse response = new WayCommodityAbstractWordResponse();
        response.setCommodityAbstractWordBo(wordBo);

        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/update")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        updateCommodityAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = BeanMapper.map(request, WayCommodityAbstractWordParam.class);

        commodityAbstractWordService.updateAbstractWord(param);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/delete")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        deleteCommodityAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = BeanMapper.map(request, WayCommodityAbstractWordParam.class);

        try {
            commodityAbstractWordService.deleteAbstractWord(param);
        } catch (Exception e) {
            return ResponseResultUtil.wrapException(e);
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(value = "/move")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordResponse>
        moveCommodityAbstractWord(@RequestBody WayCommodityAbstractWordRequest request) {

        WayCommodityAbstractWordParam param = BeanMapper.map(request, WayCommodityAbstractWordParam.class);

        try {
            commodityAbstractWordService.moveAbstractWord(param);
        } catch (Exception e) {
            return ResponseResultUtil.wrapException(e);
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}