package com.zl.way.amap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapConvertModel;
import com.zl.way.amap.model.AMapConvertRequest;
import com.zl.way.amap.model.AMapConvertResponse;
import com.zl.way.amap.service.AMapConvertService;
import com.zl.way.amap.util.OkHttp3Util;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AMapConvertServiceImpl implements AMapConvertService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amap.coordinateConvertUrl}")
    private String coordinateConvertUrl;

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapConvertResponse coordinateConvert(AMapConvertRequest aMapConvertRequest) throws AMapException {
        AMapConvertResponse response = new AMapConvertResponse();
        if (CollectionUtils.isNotEmpty(aMapConvertRequest.getLocationList())) {
            Map<String, String> params = new HashMap<>(3);
            params.put("key", key);
            params.put("coordsys", "gps");
            params.put("locations", StringUtils.join(aMapConvertRequest.getLocationList(), "|"));
            String resp = OkHttp3Util.doGet(coordinateConvertUrl, params);
            if (logger.isDebugEnabled()) {
                logger.debug("请求返回={}", resp);
            }

            if (StringUtils.isNotBlank(resp)) {
                JSONObject resultJsonObject = JSON.parseObject(resp);
                Integer status = resultJsonObject.getInteger("status");
                if (status == 1) {
                    // 112,39;112,38
                    String locationsString =
                        StringUtils.defaultIfBlank(resultJsonObject.getString("locations"), StringUtils.EMPTY);
                    String[] locationsArr = StringUtils.split(locationsString, ";");
                    if (locationsArr.length > 0) {
                        List<AMapConvertModel> aMapConvertModelList = new ArrayList<>(locationsArr.length);
                        for (String location : Arrays.asList(locationsArr)) {
                            String[] locationArr = StringUtils.split(location, ",");
                            if (locationArr.length == 2) {
                                AMapConvertModel aMapConvertModel = new AMapConvertModel();
                                aMapConvertModel.setLongitude(locationArr[0]);
                                aMapConvertModel.setLatitude(locationArr[1]);
                                aMapConvertModelList.add(aMapConvertModel);
                            }
                        }
                        response.setConvertModelList(aMapConvertModelList);
                        return response;
                    }
                }
            }
        }
        response.setConvertModelList(Collections.emptyList());
        return response;
    }
}