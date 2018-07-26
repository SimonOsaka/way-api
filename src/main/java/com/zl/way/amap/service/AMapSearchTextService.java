package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapSearchTextRequest;
import com.zl.way.amap.model.AMapSearchTextResponse;

/**
 * https://lbs.amap.com/api/webservice/guide/api/search#text
 */
public interface AMapSearchTextService {

    AMapSearchTextResponse searchText(AMapSearchTextRequest request) throws AMapException;
}
