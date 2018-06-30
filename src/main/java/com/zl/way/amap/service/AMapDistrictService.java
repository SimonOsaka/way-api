package com.zl.way.amap.service;

import com.zl.way.amap.api.model.AMapDistrictRequest;
import com.zl.way.amap.api.model.AMapDistrictResponse;
import com.zl.way.amap.exception.AMapException;

/**
 * http://lbs.amap.com/api/webservice/guide/api/district
 */
public interface AMapDistrictService {

    AMapDistrictResponse queryDistrict(AMapDistrictRequest aMapDistrictRequest) throws AMapException;
}
