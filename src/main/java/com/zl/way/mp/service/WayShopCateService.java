package com.zl.way.mp.service;

import com.zl.way.mp.model.WayShopCateLeafBo;
import com.zl.way.mp.model.WayShopCateLeafParam;
import com.zl.way.mp.model.WayShopCateRootBo;

import java.util.List;

public interface WayShopCateService {

    List<WayShopCateRootBo> queryCateRoot();

    List<WayShopCateLeafBo> queryCateLeaf(WayShopCateLeafParam leafParam);

    List<WayShopCateRootBo> queryCateAll();

}
