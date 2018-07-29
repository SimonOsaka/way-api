package com.zl.way.sp.service;

import com.zl.way.sp.model.WayShopCateLeafBo;
import com.zl.way.sp.model.WayShopCateLeafParam;
import com.zl.way.sp.model.WayShopCateRootBo;

import java.util.List;

public interface WayShopCateService {

    List<WayShopCateRootBo> queryCateRoot();

    List<WayShopCateLeafBo> queryCateLeaf(WayShopCateLeafParam leafParam);

    List<WayShopCateRootBo> queryCateAll();

}
