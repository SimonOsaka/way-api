package com.zl.way.amap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapGeoModel;
import com.zl.way.amap.model.AMapGeoRequest;
import com.zl.way.amap.model.AMapGeoResponse;
import com.zl.way.amap.model.AMapRegeoModel;
import com.zl.way.amap.model.AMapRegeoRequest;
import com.zl.way.amap.model.AMapRegeoResponse;
import com.zl.way.amap.service.AMapGeocodeService;
import com.zl.way.amap.util.OkHttp3Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AMapGeocodeServiceImpl implements AMapGeocodeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amap.regeoUrl}")
    private String regeoUrl;

    @Value("${amap.geoUrl}")
    private String geoUrl;

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapRegeoResponse getRegeo(AMapRegeoRequest aMapRegeoRequest) throws AMapException {
        AMapRegeoResponse regeoResponse = new AMapRegeoResponse();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("location", aMapRegeoRequest.getLocation());
        params.put("extensions", StringUtils.defaultIfBlank(aMapRegeoRequest.getExtensions(), "base"));
        String resp = OkHttp3Util.doGet(regeoUrl, params);
        if (logger.isDebugEnabled()) {
            logger.debug("请求返回={}", resp);
        }

        if (StringUtils.isNotBlank(resp)) {
            JSONObject resultJsonObject = JSON.parseObject(resp);
            Integer status = resultJsonObject.getInteger("status");
            if (status == 1) {
                JSONObject regeoJsonObject = resultJsonObject.getJSONObject("regeocode");
                regeoResponse.setCode(200);
                AMapRegeoModel aMapRegeoModel = new AMapRegeoModel();
                regeoResponse.setaMapRegeoModel(aMapRegeoModel);
                aMapRegeoModel.setFormatAddress(
                    StringUtils.defaultString(regeoJsonObject.getString("formatted_address"), StringUtils.EMPTY));

                JSONObject addressComponentJsonObject = regeoJsonObject.getJSONObject("addressComponent");

                String city = addressComponentJsonObject.getString("city");
                if (StringUtils.isNotBlank(city)) {
                    city = city.replaceFirst("\\[", "").replaceFirst("]", "");
                }
                String province = addressComponentJsonObject.getString("province");
                String cityName = StringUtils.defaultIfBlank(city, province);
                aMapRegeoModel.setCityName(cityName);

                String cityCode = addressComponentJsonObject.getString("citycode");
                aMapRegeoModel.setCityCode(cityCode);

                String adCode = addressComponentJsonObject.getString("adcode");
                aMapRegeoModel.setAdCode(adCode);

                String district = addressComponentJsonObject.getString("district");
                aMapRegeoModel.setDistrict(district);

                JSONArray poisJsonArray = regeoJsonObject.getJSONArray("pois");
                if (null != poisJsonArray && !poisJsonArray.isEmpty()) {
                    JSONObject poiJsonObject = poisJsonArray.getJSONObject(0);
                    String poiName = poiJsonObject.getString("name");
                    aMapRegeoModel.setNeighborhoodName(StringUtils.defaultString(poiName, StringUtils.EMPTY));

                    String poiLocation = poiJsonObject.getString("location");
                    aMapRegeoModel.setNeighborhoodLocation(StringUtils.defaultString(poiLocation, StringUtils.EMPTY));
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("组装后的结构={}", JSON.toJSONString(regeoResponse, true));
                }
                return regeoResponse;
            }
        }

        regeoResponse.setCode(0);
        return regeoResponse;
    }

    @Override
    public AMapGeoResponse getGeo(AMapGeoRequest aMapGeoRequest) throws AMapException {
        AMapGeoResponse geoResponse = new AMapGeoResponse();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("address", aMapGeoRequest.getAddress());
        params.put("city", StringUtils.defaultIfBlank(aMapGeoRequest.getCity(), ""));
        String resp = OkHttp3Util.doGet(geoUrl, params);
        if (logger.isDebugEnabled()) {
            logger.debug("请求返回={}", resp);
        }

        if (StringUtils.isNotBlank(resp)) {
            JSONObject resultJsonObject = JSON.parseObject(resp);
            Integer status = resultJsonObject.getInteger("status");
            Integer count = resultJsonObject.getInteger("count");
            if (status == 1) {
                geoResponse.setCode(200);
                if (count > 0) {
                    JSONArray geoJsonArray = resultJsonObject.getJSONArray("geocodes");
                    List<AMapGeoModel> aMapGeoModelList = new ArrayList<>();
                    geoResponse.setaMapGeoModelList(aMapGeoModelList);
                    for (Object geoObject : geoJsonArray) {
                        JSONObject geoJson = (JSONObject)geoObject;
                        AMapGeoModel aMapGeoModel = new AMapGeoModel();

                        String formattedAddress =
                            StringUtils.defaultString(geoJson.getString("formatted_address"), StringUtils.EMPTY);
                        aMapGeoModel.setFormatAddress(formattedAddress);

                        String city = geoJson.getString("city");
                        if (StringUtils.isNotBlank(city)) {
                            city = city.replaceFirst("\\[", "").replaceFirst("]", "");
                        }
                        String province = geoJson.getString("province");
                        String cityName = StringUtils.defaultIfBlank(city, province);
                        aMapGeoModel.setCityName(cityName);

                        String cityCode = geoJson.getString("citycode");
                        aMapGeoModel.setCityCode(cityCode);

                        String adCode = geoJson.getString("adcode");
                        aMapGeoModel.setAdCode(adCode);

                        String district = geoJson.getString("district");
                        aMapGeoModel.setDistrict(district);

                        aMapGeoModelList.add(aMapGeoModel);
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("组装后的结构={}", JSON.toJSONString(geoResponse, true));
                }
                return geoResponse;
            }
        }

        geoResponse.setCode(0);
        return geoResponse;
    }
}
