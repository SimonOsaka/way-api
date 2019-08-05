package com.zl.way.sp.service;

import java.util.List;

import com.zl.way.sp.model.WayShopCateLeafBo;
import com.zl.way.sp.model.WayShopCateLeafParam;
import com.zl.way.sp.model.WayShopCateRootBo;

public interface WayShopCateService {

    List<WayShopCateRootBo> queryCateRoot();

    List<WayShopCateLeafBo> queryCateLeaf(WayShopCateLeafParam leafParam);

}
