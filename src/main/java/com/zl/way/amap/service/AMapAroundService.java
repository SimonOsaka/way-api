package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapAroundRequest;
import com.zl.way.amap.model.AMapAroundResponse;

/**
 * 周边搜索
 * http://lbs.amap.com/api/webservice/guide/api/search#around
 */
public interface AMapAroundService {

	AMapAroundResponse searchAround(AMapAroundRequest aMapAroundRequest) throws AMapException;
}
