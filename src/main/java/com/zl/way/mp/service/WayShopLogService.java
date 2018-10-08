package com.zl.way.mp.service;

import com.zl.way.mp.model.WayShopLogBo;
import com.zl.way.mp.model.WayShopLogParam;

import java.util.List;

public interface WayShopLogService {

    List<WayShopLogBo> queryShopLogList(WayShopLogParam param);
}
