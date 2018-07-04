package com.zl.way.amap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapDistrictModel;
import com.zl.way.amap.model.AMapDistrictRequest;
import com.zl.way.amap.model.AMapDistrictResponse;
import com.zl.way.amap.service.AMapDistrictService;
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
public class AMapDistrictServiceImpl implements AMapDistrictService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${amap.districtUrl}")
	private String districtUrl;

	@Value("${amap.key}")
	private String key;

	@Override
	public AMapDistrictResponse queryDistrict(AMapDistrictRequest aMapDistrictRequest) throws AMapException {
		AMapDistrictResponse districtResponse = new AMapDistrictResponse();
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("keywords", aMapDistrictRequest.getKeywords());
		params.put("subdistrict", StringUtils.defaultIfBlank(aMapDistrictRequest.getSubDistrict(), "0"));
		params.put("extensions", StringUtils.defaultIfBlank(aMapDistrictRequest.getExtensions(), "base"));
		String resp = OkHttp3Util.doGet(districtUrl, params);
		if (logger.isDebugEnabled()) {
			logger.debug("请求返回={}", resp);
		}

		if (StringUtils.isNotBlank(resp)) {
			JSONObject resultJsonObj = JSON.parseObject(resp);
			Integer status = resultJsonObj.getInteger("status");
			Integer count = resultJsonObj.getInteger("count");
			if (status == 1 && count > 0) {
				JSONArray tipsJsonArray = resultJsonObj.getJSONArray("districts");
				districtResponse.setCode(200);
				List<AMapDistrictModel> aMapDistrictModelList = new ArrayList<>();
				districtResponse.setaMapDistrictModelList(aMapDistrictModelList);
				for (Object obj : tipsJsonArray) {
					JSONObject districtJsonObj = (JSONObject) obj;
					AMapDistrictModel districtModel = new AMapDistrictModel();
					districtModel.setName(districtJsonObj.getString("name"));
					districtModel.setCityCode(districtJsonObj.getString("citycode"));
					districtModel.setAdCode(districtJsonObj.getString("adcode"));
					districtModel.setCenter(districtJsonObj.getString("center"));
					districtModel.setLevel(districtJsonObj.getString("level"));
					aMapDistrictModelList.add(districtModel);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("组装后的结构={}", JSON.toJSONString(districtResponse, true));
				}
				return districtResponse;
			}
		}

		districtResponse.setCode(0);
		return districtResponse;
	}
}
