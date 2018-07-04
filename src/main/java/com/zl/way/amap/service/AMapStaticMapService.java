package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapStaticMapRequest;
import com.zl.way.amap.model.AMapStaticMapResponse;

public interface AMapStaticMapService {

	AMapStaticMapResponse getStaticMap(AMapStaticMapRequest aMapStaticMapRequest) throws AMapException;
}
