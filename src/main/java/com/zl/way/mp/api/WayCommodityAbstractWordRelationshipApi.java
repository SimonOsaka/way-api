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
import com.zl.way.mp.api.model.WayCommodityAbstractWordRelationshipRequest;
import com.zl.way.mp.api.model.WayCommodityAbstractWordRelationshipResponse;
import com.zl.way.mp.api.validation.WayCommodityAbstractWordRelationshipApiValidation;
import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipBo;
import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipParam;
import com.zl.way.mp.service.WayCommodityAbstractWordRelationshipService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

/**
 * mp抽象网络图api
 *
 * @author xuzhongliang
 */
@RestController("mpWayCommodityAbstractWordRelationshipApi")
@RequestMapping("/mp/commodity/abstractword/relationship")
public class WayCommodityAbstractWordRelationshipApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private WayCommodityAbstractWordRelationshipService abstractWordRelationshipService;

    @Autowired
    public WayCommodityAbstractWordRelationshipApi(
        WayCommodityAbstractWordRelationshipService abstractWordRelationshipService) {
        this.abstractWordRelationshipService = abstractWordRelationshipService;
    }

    @PostMapping(value = "/list")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordRelationshipResponse>
        queryCommodityAbstractWordRelationship(@RequestBody WayCommodityAbstractWordRelationshipRequest request) {

        WayCommodityAbstractWordRelationshipApiValidation validation =
            new WayCommodityAbstractWordRelationshipApiValidation(request).abstractWordId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayCommodityAbstractWordRelationshipParam params = new WayCommodityAbstractWordRelationshipParam();
        params.setAbstractWordId(request.getAbstractWordId());

        PageParam pageParam = BeanMapper.map(request, PageParam.class);
        List<WayCommodityAbstractWordRelationshipBo> abstractWordRelationshipBoList =
            abstractWordRelationshipService.queryAbstractRelationShip(params, pageParam);

        WayCommodityAbstractWordRelationshipResponse response = new WayCommodityAbstractWordRelationshipResponse();
        response.setAbstractWordRelationshipBoList(abstractWordRelationshipBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping(value = "/save")
    @WayTokenValidation
    public ResponseResult<WayCommodityAbstractWordRelationshipResponse>
        createCommodityAbstractWordRelationship(@RequestBody WayCommodityAbstractWordRelationshipRequest request) {

        WayCommodityAbstractWordRelationshipApiValidation validation =
            new WayCommodityAbstractWordRelationshipApiValidation(request).abstractWordId();
        if (validation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(validation.getErrors().get(0));
        }

        WayCommodityAbstractWordRelationshipParam params = new WayCommodityAbstractWordRelationshipParam();
        params.setAbstractWordId(request.getAbstractWordId());
        params.setAbstractWordIdList(request.getAbstractWordIdList());
        abstractWordRelationshipService.saveAbstractWordRelationship(params);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

}