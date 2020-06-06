package com.zl.way.amap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapInputTipsModel;
import com.zl.way.amap.model.AMapInputTipsRequest;
import com.zl.way.amap.model.AMapInputTipsResponse;
import com.zl.way.amap.service.AMapInputTipsService;
import com.zl.way.amap.util.OkHttp3Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AMapInputTipsServiceImpl implements AMapInputTipsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${amap.inputtipsUrl}")
    private String inputTipsUrl;

    @Value("${amap.key}")
    private String key;

    @Override
    public AMapInputTipsResponse queryInputTips(AMapInputTipsRequest request) throws AMapException {
        AMapInputTipsResponse inputTipsResponse = new AMapInputTipsResponse();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("keywords", request.getKeywords());
        params.put("city", request.getCity());
        String resp = OkHttp3Util.doGet(inputTipsUrl, params);
        if (logger.isDebugEnabled()) {
            logger.debug("请求返回={}", resp);
        }

        if (StringUtils.isNotBlank(resp)) {
            JSONObject resultJsonObject = JSON.parseObject(resp);
            Integer status = resultJsonObject.getInteger("status");
            Integer count = resultJsonObject.getInteger("count");
            if (status == 1 && count > 0) {
                JSONArray tipsJsonArray = resultJsonObject.getJSONArray("tips");
                inputTipsResponse.setCode(200);
                List<AMapInputTipsModel> aMapInputTipsModelList = new ArrayList<>();
                inputTipsResponse.setaMapInputTipsModelList(aMapInputTipsModelList);
                for (Object obj : tipsJsonArray) {
                    JSONObject tipJsonObj = (JSONObject)obj;
                    AMapInputTipsModel inputTipsModel = new AMapInputTipsModel();
                    inputTipsModel.setName(tipJsonObj.getString("name"));

                    String tipAddress = tipJsonObj.getString("address");
                    if (StringUtils.isBlank(tipAddress) || tipAddress.contains("[]")) {
                        tipAddress = "";
                    }
                    inputTipsModel.setAddress(tipAddress);

                    String tipDistrict = tipJsonObj.getString("district");
                    if (StringUtils.isBlank(tipDistrict) || tipDistrict.contains("[]")) {
                        tipDistrict = "";
                    }
                    inputTipsModel.setDistrict(tipDistrict);

                    String tipLocation = tipJsonObj.getString("location");
                    if (StringUtils.isBlank(tipLocation) || tipLocation.contains("[]")) {
                        continue;
                    }
                    inputTipsModel.setLocation(tipLocation);
                    aMapInputTipsModelList.add(inputTipsModel);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("组装后的结构={}", JSON.toJSONString(inputTipsResponse, true));
                }
                return inputTipsResponse;
            }
        }

        inputTipsResponse.setCode(0);
        return inputTipsResponse;
    }
}
