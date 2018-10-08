package com.zl.way.mp.service;

import com.zl.way.mp.model.WayCommodityLogBo;
import com.zl.way.mp.model.WayCommodityLogParam;

import java.util.List;

public interface WayCommodityLogService {

    List<WayCommodityLogBo> queryCommodityLogList(WayCommodityLogParam param);
}
