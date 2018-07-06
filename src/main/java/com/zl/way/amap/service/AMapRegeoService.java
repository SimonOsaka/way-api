package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapRegeoRequest;
import com.zl.way.amap.model.AMapRegeoResponse;

public interface AMapRegeoService {

	/**
	 * 逆地址编码查询
	 * @param aMapRegeoRequest
	 * @return
	 * @throws AMapException
	 */
	AMapRegeoResponse getRegeo(AMapRegeoRequest aMapRegeoRequest) throws AMapException;
}
