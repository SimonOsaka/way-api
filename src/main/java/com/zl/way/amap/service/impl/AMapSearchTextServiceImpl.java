package com.zl.way.amap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.SymbolConstants;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapSearchTextModel;
import com.zl.way.amap.model.AMapSearchTextRequest;
import com.zl.way.amap.model.AMapSearchTextResponse;
import com.zl.way.amap.service.AMapSearchTextService;
import com.zl.way.util.OkHttp3Util;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AMapSearchTextServiceImpl implements AMapSearchTextService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amap.searchTextUrl}")
    private String searchTextUrl;

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapSearchTextResponse searchText(AMapSearchTextRequest request) throws AMapException {

        AMapSearchTextResponse searchTextResponse = new AMapSearchTextResponse();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        if (StringUtils.isNotBlank(request.getKeywords())) {
            params.put("keywords", request.getKeywords());
        }
        if (CollectionUtils.isNotEmpty(request.getTypeList())) {
            params.put("types", String.join(SymbolConstants.VERTICAL_BAR, request.getTypeList()));
        }
        if (null != request.getOffset()) {
            params.put("offset", request.getOffset().toString());
        }
        if (null != request.getPage()) {
            params.put("page", request.getPage().toString());
        }
        if (StringUtils.isNotBlank(request.getCity())) {
            params.put("city", request.getCity());
        }
        params.put("extensions", "all");
        String resp = OkHttp3Util.doGet(searchTextUrl, params);
        if (logger.isDebugEnabled()) {
            logger.debug("请求返回={}", resp);
        }

        if (StringUtils.isNotBlank(resp)) {
            JSONObject resultJsonObject = JSON.parseObject(resp);
            Integer status = resultJsonObject.getInteger("status");
            Integer count = resultJsonObject.getInteger("count");
            if (status == 1 && count > 0) {
                JSONArray poisJsonArray = resultJsonObject.getJSONArray("pois");
                searchTextResponse.setCode(200);
                List<AMapSearchTextModel> searchTextModelList = new ArrayList<>();
                searchTextResponse.setSearchTextModelList(searchTextModelList);
                for (Object obj : poisJsonArray) {
                    JSONObject poiJsonObj = (JSONObject)obj;
                    AMapSearchTextModel searchTextModel = new AMapSearchTextModel();
                    searchTextModel.setName(poiJsonObj.getString("name"));

                    String address = poiJsonObj.getString("address");
                    if (StringUtils.isBlank(address) || address.contains("[]")) {
                        address = "";
                    }
                    searchTextModel.setAddress(address);

                    String cityName = poiJsonObj.getString("cityname");
                    if (StringUtils.isBlank(cityName) || cityName.contains("[]")) {
                        cityName = "";
                    }
                    searchTextModel.setCityName(cityName);

                    String location = poiJsonObj.getString("location");
                    if (StringUtils.isBlank(location) || location.contains("[]")) {
                        location = "";
                    }
                    searchTextModel.setLocation(location);

                    String pName = poiJsonObj.getString("pname");
                    if (StringUtils.isBlank(pName) || pName.contains("[]")) {
                        pName = "";
                    }
                    searchTextModel.setpName(pName);

                    String adName = poiJsonObj.getString("adname");
                    if (StringUtils.isBlank(adName) || adName.contains("[]")) {
                        adName = "";
                    }
                    searchTextModel.setAdName(adName);

                    String cityCode = poiJsonObj.getString("citycode");
                    if (StringUtils.isBlank(cityCode) || cityCode.contains("[]")) {
                        cityCode = "";
                    }
                    searchTextModel.setCityCode(cityCode);

                    String adCode = poiJsonObj.getString("adcode");
                    if (StringUtils.isBlank(adCode) || adCode.contains("[]")) {
                        adCode = "";
                    }
                    searchTextModel.setAdCode(adCode);

                    String tel = poiJsonObj.getString("tel");
                    if (StringUtils.isBlank(tel) || tel.contains("[]")) {
                        tel = StringUtils.EMPTY;
                    }
                    searchTextModel.setTel(tel);

                    searchTextModelList.add(searchTextModel);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("组装后的结构={}", JSON.toJSONString(searchTextResponse, true));
                }
                return searchTextResponse;
            }
        }

        searchTextResponse.setCode(0);
        return searchTextResponse;
    }
}
