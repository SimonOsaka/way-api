package com.zl.way.mp.service;

import com.zl.way.mp.model.SpUserShop;
import com.zl.way.util.PageParam;

import java.util.List;

public interface SpUserShopService {
    List<SpUserShop> querySpUserShopList(SpUserShop params, PageParam pageParam);

    void updateSpUserShop(SpUserShop params);
}
