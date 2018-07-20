package com.zl.way.amap.api;

import com.zl.way.amap.api.model.*;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.*;
import com.zl.way.amap.service.*;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/amap")
public class WayAMapApi {

    @Autowired
    private AMapInputTipsService aMapInputTipsService;

    @Autowired
    private AMapDistrictService aMapDistrictService;

    @Autowired
    private AMapAroundService aMapAroundService;

    @Autowired
    private AMapStaticMapService aMapStaticMapService;

    @Autowired
    private AMapRegeoService aMapRegeoService;

    @RequestMapping(method = RequestMethod.POST, path = "/inputtips")
    public ResponseResult<List<WayAMapInputTipsResponse>> requestInputTips(
            @RequestBody WayAMapInputTipsRequest request) {

        if (StringUtils.isBlank(request.getKeywords())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapInputTipsRequest aMapInputTipsRequest = new AMapInputTipsRequest();
        aMapInputTipsRequest.setKeywords(request.getKeywords());//关键字
        aMapInputTipsRequest.setCity(request.getCity());//当前城市
        try {
            AMapInputTipsResponse aMapInputTipsResponse = aMapInputTipsService
                    .queryInputTips(aMapInputTipsRequest);
            if (aMapInputTipsResponse.getCode() == 200) {
                List<AMapInputTipsModel> aMapInputTipsModelList = aMapInputTipsResponse
                        .getaMapInputTipsModelList();
                if (aMapInputTipsModelList.size() > 5) {
                    aMapInputTipsModelList = aMapInputTipsModelList.subList(0, 5);
                }
                List<WayAMapInputTipsResponse> wayAMapInputTipsResponseList = BeanMapper
                        .mapAsList(aMapInputTipsModelList, WayAMapInputTipsResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapInputTipsResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/district")
    public ResponseResult<List<WayAMapDistrictResponse>> requestDistrict(
            @RequestBody WayAMapDistrictRequest request) {

        if (StringUtils.isBlank(request.getKeywords())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapDistrictRequest aMapDistrictRequest = new AMapDistrictRequest();
        aMapDistrictRequest.setKeywords(request.getKeywords());//关键字
        try {
            AMapDistrictResponse aMapDistrictResponse = aMapDistrictService
                    .queryDistrict(aMapDistrictRequest);
            if (aMapDistrictResponse.getCode() == 200) {
                List<AMapDistrictModel> aMapDistrictModelList = aMapDistrictResponse
                        .getaMapDistrictModelList();
                List<WayAMapDistrictResponse> wayAMapDistrictResponseList = BeanMapper
                        .mapAsList(aMapDistrictModelList, WayAMapDistrictResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapDistrictResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/around")
    public ResponseResult<List<WayAMapAroundResponse>> requestAround(
            @RequestBody WayAMapAroundRequest request) {

        if (StringUtils.isBlank(request.getLocation())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapAroundRequest aMapAroundRequest = new AMapAroundRequest();
        aMapAroundRequest.setLocation(request.getLocation());
        aMapAroundRequest.setTypes(request.getTypes());
        aMapAroundRequest.setKeywords(request.getKeywords());//关键字
        try {
            AMapAroundResponse aMapAroundResponse = aMapAroundService
                    .searchAround(aMapAroundRequest);
            if (aMapAroundResponse.getCode() == 200) {
                List<AMapAroundModel> aMapAroundModelList = aMapAroundResponse
                        .getaMapAroundModelList();
                List<WayAMapAroundResponse> wayAMapAroundResponseList = BeanMapper
                        .mapAsList(aMapAroundModelList, WayAMapAroundResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapAroundResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/staticmap")
    public ResponseResult<WayAMapStaticMapResponse> requestStaticMap(
            @RequestBody WayAMapStaticMapRequest wayAMapStaticMapRequest) {

        AMapStaticMapRequest aMapStaticMapRequest = BeanMapper
                .map(wayAMapStaticMapRequest, AMapStaticMapRequest.class);

        WayAMapStaticMapResponse wayAMapStaticMapResponse = null;
        try {
            AMapStaticMapResponse aMapStaticMapResponse = aMapStaticMapService
                    .getStaticMap(aMapStaticMapRequest);

            wayAMapStaticMapResponse = new WayAMapStaticMapResponse();
            wayAMapStaticMapResponse.setStaticMapUrl(
                    aMapStaticMapResponse.getaMapStaticMapModel().getStaticMapUrl());

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapStaticMapResponse);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/regeo")
    public ResponseResult<WayAMapRegeoResponse> requestRegeo(
            @RequestBody WayAMapRegeoRequest wayAMapRegeoRequest) {

        AMapRegeoRequest aMapRegeoRequest = BeanMapper
                .map(wayAMapRegeoRequest, AMapRegeoRequest.class);

        WayAMapRegeoResponse wayAMapRegeoResponse = null;
        try {
            AMapRegeoResponse aMapRegeoResponse = aMapRegeoService.getRegeo(aMapRegeoRequest);

            wayAMapRegeoResponse = BeanMapper
                    .map(aMapRegeoResponse.getaMapRegeoModel(), WayAMapRegeoResponse.class);

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapRegeoResponse);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
