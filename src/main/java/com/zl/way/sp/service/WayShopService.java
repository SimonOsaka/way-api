package com.zl.way.sp.service;

import java.util.List;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayShopBo;
import com.zl.way.sp.model.WayShopParam;
import com.zl.way.util.PageParam;

public interface WayShopService {

    List<WayShopBo> queryShopList(WayShopParam shopParam, PageParam pageParam);

    WayShopBo getShop(WayShopParam shopParam);

    WayShopBo createShop(WayShopParam shopParam) throws BusinessException;

    WayShopBo updateShop(WayShopParam shopParam) throws BusinessException;

    WayShopBo deleteShop(WayShopParam shopParam);

    WayShopBo onlineShop(WayShopParam shopParam) throws BusinessException;

    WayShopBo offlineShop(WayShopParam shopParam);
}
