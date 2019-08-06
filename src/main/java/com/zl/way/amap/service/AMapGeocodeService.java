package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapGeoRequest;
import com.zl.way.amap.model.AMapGeoResponse;
import com.zl.way.amap.model.AMapRegeoRequest;
import com.zl.way.amap.model.AMapRegeoResponse;

public interface AMapGeocodeService {

    /**
     * 逆地址编码查询
     *
     * @param aMapRegeoRequest
     * @return
     * @throws AMapException
     */
    AMapRegeoResponse getRegeo(AMapRegeoRequest aMapRegeoRequest) throws AMapException;

    /**
     * 地址查询
     *
     * @param aMapGeoRequest
     * @return
     * @throws AMapException
     */
    AMapGeoResponse getGeo(AMapGeoRequest aMapGeoRequest) throws AMapException;
}
