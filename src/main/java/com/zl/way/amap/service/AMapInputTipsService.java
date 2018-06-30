package com.zl.way.amap.service;

import com.zl.way.amap.api.model.AMapInputTipsRequest;
import com.zl.way.amap.api.model.AMapInputTipsResponse;
import com.zl.way.amap.exception.AMapException;

/**
 * http://lbs.amap.com/api/webservice/guide/api/inputtips
 */
public interface AMapInputTipsService {

    AMapInputTipsResponse queryInputTips(AMapInputTipsRequest request) throws AMapException;
}
