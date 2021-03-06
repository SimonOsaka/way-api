package com.zl.way.amap.api;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zl.way.amap.api.model.*;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.*;
import com.zl.way.amap.service.*;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController
@RequestMapping("/amap")
public class WayAMapApi {

    private final AMapInputTipsService aMapInputTipsService;

    private final AMapDistrictService aMapDistrictService;

    private final AMapAroundService aMapAroundService;

    private final AMapStaticMapService aMapStaticMapService;

    private final AMapGeocodeService aMapGeocodeService;

    private final AMapSearchTextService aMapSearchTextService;

    private final AMapConvertService aMapConvertService;

    @Autowired
    public WayAMapApi(AMapInputTipsService aMapInputTipsService, AMapDistrictService aMapDistrictService,
        AMapAroundService aMapAroundService, AMapStaticMapService aMapStaticMapService,
        AMapGeocodeService aMapGeocodeService, AMapSearchTextService aMapSearchTextService,
        AMapConvertService aMapConvertService) {
        this.aMapInputTipsService = aMapInputTipsService;
        this.aMapDistrictService = aMapDistrictService;
        this.aMapAroundService = aMapAroundService;
        this.aMapStaticMapService = aMapStaticMapService;
        this.aMapGeocodeService = aMapGeocodeService;
        this.aMapSearchTextService = aMapSearchTextService;
        this.aMapConvertService = aMapConvertService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/inputtips")
    public ResponseResult<List<WayAMapInputTipsResponse>>
        requestInputTips(@RequestBody WayAMapInputTipsRequest request) {

        if (StringUtils.isBlank(request.getKeywords())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapInputTipsRequest aMapInputTipsRequest = new AMapInputTipsRequest();
        aMapInputTipsRequest.setKeywords(request.getKeywords());// 关键字
        aMapInputTipsRequest.setCity(request.getCity());// 当前城市
        try {
            AMapInputTipsResponse aMapInputTipsResponse = aMapInputTipsService.queryInputTips(aMapInputTipsRequest);
            if (aMapInputTipsResponse.getCode() == 200) {
                List<AMapInputTipsModel> aMapInputTipsModelList = aMapInputTipsResponse.getaMapInputTipsModelList();
                if (aMapInputTipsModelList.size() > 5) {
                    aMapInputTipsModelList = aMapInputTipsModelList.subList(0, 5);
                }
                List<WayAMapInputTipsResponse> wayAMapInputTipsResponseList =
                    BeanMapper.mapAsList(aMapInputTipsModelList, WayAMapInputTipsResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapInputTipsResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/district")
    public ResponseResult<List<WayAMapDistrictResponse>> requestDistrict(@RequestBody WayAMapDistrictRequest request) {

        if (StringUtils.isBlank(request.getKeywords())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapDistrictRequest aMapDistrictRequest = new AMapDistrictRequest();
        aMapDistrictRequest.setKeywords(request.getKeywords());// 关键字
        try {
            AMapDistrictResponse aMapDistrictResponse = aMapDistrictService.queryDistrict(aMapDistrictRequest);
            if (aMapDistrictResponse.getCode() == 200) {
                List<AMapDistrictModel> aMapDistrictModelList = aMapDistrictResponse.getaMapDistrictModelList();
                List<WayAMapDistrictResponse> wayAMapDistrictResponseList =
                    BeanMapper.mapAsList(aMapDistrictModelList, WayAMapDistrictResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapDistrictResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/around")
    public ResponseResult<List<WayAMapAroundResponse>> requestAround(@RequestBody WayAMapAroundRequest request) {

        if (StringUtils.isBlank(request.getLocation())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapAroundRequest aMapAroundRequest = new AMapAroundRequest();
        aMapAroundRequest.setLocation(request.getLocation());
        aMapAroundRequest.setTypes(request.getTypes());
        aMapAroundRequest.setKeywords(request.getKeywords());// 关键字
        try {
            AMapAroundResponse aMapAroundResponse = aMapAroundService.searchAround(aMapAroundRequest);
            if (aMapAroundResponse.getCode() == 200) {
                List<AMapAroundModel> aMapAroundModelList = aMapAroundResponse.getaMapAroundModelList();
                List<WayAMapAroundResponse> wayAMapAroundResponseList =
                    BeanMapper.mapAsList(aMapAroundModelList, WayAMapAroundResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapAroundResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/staticmap")
    public ResponseResult<WayAMapStaticMapResponse>
        requestStaticMap(@RequestBody WayAMapStaticMapRequest wayAMapStaticMapRequest) {

        AMapStaticMapRequest aMapStaticMapRequest = BeanMapper.map(wayAMapStaticMapRequest, AMapStaticMapRequest.class);

        WayAMapStaticMapResponse wayAMapStaticMapResponse = null;
        try {
            AMapStaticMapResponse aMapStaticMapResponse = aMapStaticMapService.getStaticMap(aMapStaticMapRequest);

            wayAMapStaticMapResponse = new WayAMapStaticMapResponse();
            wayAMapStaticMapResponse.setStaticMapUrl(aMapStaticMapResponse.getaMapStaticMapModel().getStaticMapUrl());

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapStaticMapResponse);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/regeo")
    public ResponseResult<WayAMapRegeoResponse> requestRegeo(@RequestBody WayAMapRegeoRequest wayAMapRegeoRequest) {

        AMapRegeoRequest aMapRegeoRequest = BeanMapper.map(wayAMapRegeoRequest, AMapRegeoRequest.class);

        WayAMapRegeoResponse wayAMapRegeoResponse = null;
        try {
            AMapRegeoResponse aMapRegeoResponse = aMapGeocodeService.getRegeo(aMapRegeoRequest);

            wayAMapRegeoResponse = BeanMapper.map(aMapRegeoResponse.getaMapRegeoModel(), WayAMapRegeoResponse.class);

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapRegeoResponse);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping(path = "/searchText")
    public ResponseResult<List<WayAMapSearchTextResponse>>
        requestSearchText(@RequestBody WayAMapSearchTextRequest request) {

        if (StringUtils.isBlank(request.getKeywords())) {
            return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
        }

        AMapSearchTextRequest aMapSearchTextRequest = new AMapSearchTextRequest();
        aMapSearchTextRequest.setKeywords(request.getKeywords());// 关键字
        aMapSearchTextRequest.setCity(request.getCity());// 当前城市
        try {
            AMapSearchTextResponse aMapSearchTextResponse = aMapSearchTextService.searchText(aMapSearchTextRequest);
            if (aMapSearchTextResponse.getCode() == 200) {
                List<AMapSearchTextModel> searchTextModelList = aMapSearchTextResponse.getSearchTextModelList();
                List<WayAMapSearchTextResponse> wayAMapSearchTextResponseList =
                    BeanMapper.mapAsList(searchTextModelList, WayAMapSearchTextResponse.class);
                for (WayAMapSearchTextResponse response : wayAMapSearchTextResponseList) {
                    response.setFullAddress(
                        response.getpName() + response.getCityName() + response.getAdName() + response.getAddress());
                }
                return ResponseResultUtil.wrapSuccessResponseResult(wayAMapSearchTextResponseList);
            }
        } catch (Exception e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(Collections.emptyList());
    }

    @PostMapping(path = "/convert")
    public ResponseResult<List<WayAMapConvertResponse>>
        requestConvert(@RequestBody WayAMapConvertRequest wayAMapConvertRequest) {

        AMapConvertRequest aMapConvertRequest = BeanMapper.map(wayAMapConvertRequest, AMapConvertRequest.class);

        try {
            AMapConvertResponse aMapConvertResponse = aMapConvertService.coordinateConvert(aMapConvertRequest);

            List<WayAMapConvertResponse> wayAMapConvertResponseList =
                BeanMapper.mapAsList(aMapConvertResponse.getConvertModelList(), WayAMapConvertResponse.class);

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapConvertResponseList);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/geo")
    public ResponseResult<List<WayAMapGeoResponse>> requestGeo(@RequestBody WayAMapGeoRequest wayAMapGeoRequest) {

        AMapGeoRequest aMapGeoRequest = BeanMapper.map(wayAMapGeoRequest, AMapGeoRequest.class);

        List<WayAMapGeoResponse> wayAMapGeoResponse = null;
        try {
            AMapGeoResponse aMapGeoResponse = aMapGeocodeService.getGeo(aMapGeoRequest);

            wayAMapGeoResponse = BeanMapper.mapAsList(aMapGeoResponse.getaMapGeoModelList(), WayAMapGeoResponse.class);

            return ResponseResultUtil.wrapSuccessResponseResult(wayAMapGeoResponse);
        } catch (AMapException e) {
        }

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}
