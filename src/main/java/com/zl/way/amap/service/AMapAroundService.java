package com.zl.way.amap.service;

import com.zl.way.amap.api.model.AMapAroundRequest;
import com.zl.way.amap.api.model.AMapAroundResponse;
import com.zl.way.amap.exception.AMapException;

/**
 * 周边搜索
 * http://lbs.amap.com/api/webservice/guide/api/search#around
 */
public interface AMapAroundService {

    AMapAroundResponse searchAround(AMapAroundRequest aMapAroundRequest) throws AMapException;
}
