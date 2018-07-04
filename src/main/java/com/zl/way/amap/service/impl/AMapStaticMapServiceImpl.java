package com.zl.way.amap.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapStaticMapModel;
import com.zl.way.amap.model.AMapStaticMapRequest;
import com.zl.way.amap.model.AMapStaticMapResponse;
import com.zl.way.amap.service.AMapStaticMapService;
import com.zl.way.util.OkHttp3Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AMapStaticMapServiceImpl implements AMapStaticMapService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${amap.staticMapUrl}")
	private String staticMapUrl;

	@Value("${amap.key}")
	private String key;

	@Override
	public AMapStaticMapResponse getStaticMap(AMapStaticMapRequest aMapStaticMapRequest)
			throws AMapException {

		Map<String, String> urlParamsMap = new HashMap<>();
		urlParamsMap.put("key", key);
		urlParamsMap.put("location", aMapStaticMapRequest.getLocation());
		urlParamsMap
				.put("size", StringUtils.defaultIfBlank(aMapStaticMapRequest.getSize(), "750*300"));
		urlParamsMap.put("zoom", StringUtils.defaultIfBlank(aMapStaticMapRequest.getZoom(), "17"));
		urlParamsMap.put("scale", StringUtils.defaultIfBlank(aMapStaticMapRequest.getScale(), "1"));
		urlParamsMap.put("markers", "large,,这:" + aMapStaticMapRequest.getLocation());

		if (logger.isDebugEnabled()) {
			logger.debug("静态地图请求参数{}", JSON.toJSONString(urlParamsMap));
		}
		AMapStaticMapModel aMapStaticMapModel = new AMapStaticMapModel();
		aMapStaticMapModel.setStaticMapUrl(OkHttp3Util.getUrlComplete(staticMapUrl, urlParamsMap));

		AMapStaticMapResponse aMapStaticMapResponse = new AMapStaticMapResponse();
		aMapStaticMapResponse.setaMapStaticMapModel(aMapStaticMapModel);

		if (logger.isDebugEnabled()) {
			logger.debug("静态地图地址={}", JSON.toJSONString(aMapStaticMapResponse));
		}
		return aMapStaticMapResponse;
	}
}
