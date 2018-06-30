package com.zl.way.city.api;

import com.zl.way.city.api.model.WayCityResponse;
import com.zl.way.city.model.WayCity;
import com.zl.way.city.service.WayCityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityApi {
    private static final byte USED = 0;

    @Autowired
    private WayCityService wayCityService;

    @RequestMapping(value = "/allUsed", method = RequestMethod.GET)
    public ResponseResult<List<WayCityResponse>> getAllUsedCities() {

        List<WayCity> wayCityList = wayCityService.getAllCities(USED);
        List<WayCityResponse> wayCityResponseList = BeanMapper.mapAsList(wayCityList, WayCityResponse.class);
        return ResponseResultUtil.wrapSuccessResponseResult(wayCityResponseList);
    }
}
