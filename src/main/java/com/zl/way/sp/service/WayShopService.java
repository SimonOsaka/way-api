package com.zl.way.sp.service;

import com.zl.way.sp.model.WayShopBo;
import com.zl.way.sp.model.WayShopParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayShopService {

    List<WayShopBo> queryShopList(WayShopParam shopParam, PageParam pageParam);

    WayShopBo getShop(WayShopParam shopParam);

    WayShopBo createShop(WayShopParam shopParam);

    WayShopBo updateShop(WayShopParam shopParam);

    WayShopBo deleteShop(WayShopParam shopParam);
}
