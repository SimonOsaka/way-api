package com.zl.way.mp.service;

import com.zl.way.mp.model.WayShopExtraBo;
import com.zl.way.mp.model.WayShopExtraParam;

public interface WayShopExtraService {
    /**
     * 修改为管理人员创建
     * 
     * @param param
     * @return
     */
    WayShopExtraBo changeToManager(WayShopExtraParam param);

    /**
     * 修改为商家自行创建
     * 
     * @param param
     * @return
     */
    WayShopExtraBo changeToSelf(WayShopExtraParam param);
}
