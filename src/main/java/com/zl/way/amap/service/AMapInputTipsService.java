package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapInputTipsRequest;
import com.zl.way.amap.model.AMapInputTipsResponse;

/**
 * http://lbs.amap.com/api/webservice/guide/api/inputtips
 */
public interface AMapInputTipsService {

    AMapInputTipsResponse queryInputTips(AMapInputTipsRequest request) throws AMapException;
}
