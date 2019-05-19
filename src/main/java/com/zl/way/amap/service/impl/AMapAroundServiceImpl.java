package com.zl.way.amap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapAroundModel;
import com.zl.way.amap.model.AMapAroundRequest;
import com.zl.way.amap.model.AMapAroundResponse;
import com.zl.way.amap.service.AMapAroundService;
import com.zl.way.util.OkHttp3Util;
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
public class AMapAroundServiceImpl implements AMapAroundService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amap.aroundUrl}")
    private String aroundUrl;

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapAroundResponse searchAround(AMapAroundRequest aMapAroundRequest) throws AMapException {
        AMapAroundResponse aroundResponse = new AMapAroundResponse();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("location", aMapAroundRequest.getLocation());
        params.put("keywords", StringUtils.defaultIfBlank(aMapAroundRequest.getKeywords(), StringUtils.EMPTY));
        params.put("types", StringUtils.defaultIfBlank(aMapAroundRequest.getTypes(), StringUtils.EMPTY));
        params.put("city", StringUtils.defaultIfBlank(aMapAroundRequest.getCity(), StringUtils.EMPTY));
        String resp = OkHttp3Util.doGet(aroundUrl, params);
        if (logger.isDebugEnabled()) {
            logger.debug("请求返回={}", resp);
        }

        if (StringUtils.isNotBlank(resp)) {
            JSONObject resultJsonObj = JSON.parseObject(resp);
            Integer status = resultJsonObj.getInteger("status");
            Integer count = resultJsonObj.getInteger("count");
            if (status == 1 && count > 0) {
                JSONArray aroundJsonArray = resultJsonObj.getJSONArray("pois");
                aroundResponse.setCode(200);
                List<AMapAroundModel> aroundModelArrayList = new ArrayList<>();
                aroundResponse.setaMapAroundModelList(aroundModelArrayList);
                for (Object obj : aroundJsonArray) {
                    JSONObject aroundJsonObj = (JSONObject)obj;
                    AMapAroundModel aroundModel = new AMapAroundModel();
                    aroundModel.setName(aroundJsonObj.getString("name"));
                    aroundModel.setCityCode(aroundJsonObj.getString("citycode"));
                    aroundModel.setAdCode(aroundJsonObj.getString("adcode"));
                    aroundModel.setCityName(aroundJsonObj.getString("cityname"));
                    aroundModel.setAdName(aroundJsonObj.getString("adname"));
                    aroundModel.setLocation(aroundJsonObj.getString("location"));
                    aroundModel.setDistance(aroundJsonObj.getString("distance") + "m");
                    aroundModelArrayList.add(aroundModel);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("组装后的结构={}", JSON.toJSONString(aroundResponse, true));
                }

                return aroundResponse;
            }
        }

        aroundResponse.setCode(0);
        return aroundResponse;
    }
}
