package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapDistrictRequest;
import com.zl.way.amap.model.AMapDistrictResponse;

/**
 * http://lbs.amap.com/api/webservice/guide/api/district
 */
public interface AMapDistrictService {

    AMapDistrictResponse queryDistrict(AMapDistrictRequest aMapDistrictRequest) throws AMapException;
}
