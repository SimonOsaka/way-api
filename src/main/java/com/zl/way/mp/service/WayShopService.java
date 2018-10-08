package com.zl.way.mp.service;

import com.zl.way.mp.exception.BusinessException;
import com.zl.way.mp.model.WayShopBo;
import com.zl.way.mp.model.WayShopParam;
import com.zl.way.util.PageParam;

import java.util.List;
import java.util.Map;

public interface WayShopService {

    List<WayShopBo> queryShopList(WayShopParam shopParam, PageParam pageParam);

    WayShopBo getShop(WayShopParam shopParam);

    //    WayShopBo createShop(WayShopParam shopParam);

    WayShopBo updateShop(WayShopParam shopParam);

    WayShopBo deleteShop(WayShopParam shopParam);

    //    WayShopBo onlineShop(WayShopParam shopParam) throws BusinessException;

    //    WayShopBo offlineShop(WayShopParam shopParam);

    Map<String, String> getAllShopStatus();

    Long queryShopCount(WayShopParam shopParam);

    WayShopBo updateShopStatus(WayShopParam shopParam) throws BusinessException;
}
