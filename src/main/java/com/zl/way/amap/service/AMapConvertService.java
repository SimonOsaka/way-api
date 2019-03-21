package com.zl.way.amap.service;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapConvertRequest;
import com.zl.way.amap.model.AMapConvertResponse;

public interface AMapConvertService {

    /**
     * 坐标转换
     *
     * @param aMapConvertRequest 接口请求
     * @return 转换后结果
     * @throws AMapException 接口异常
     */
    AMapConvertResponse coordinateConvert(AMapConvertRequest aMapConvertRequest) throws AMapException;
}
