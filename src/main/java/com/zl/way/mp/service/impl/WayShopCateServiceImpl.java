package com.zl.way.mp.service.impl;

import com.zl.way.mp.mapper.WayShopCateLeafMapper;
import com.zl.way.mp.mapper.WayShopCateRootMapper;
import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayShopCateService;
import com.zl.way.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("mpWayShopCateServiceImpl") public class WayShopCateServiceImpl implements WayShopCateService {

    @Autowired private WayShopCateRootMapper cateRootMapper;

    @Autowired private WayShopCateLeafMapper cateLeafMapper;

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateRootBo> queryCateRoot() {

        List<WayShopCateRoot> cateRootList = cateRootMapper.selectByCondition(null);
        return BeanMapper.mapAsList(cateRootList, WayShopCateRootBo.class);
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateLeafBo> queryCateLeaf(WayShopCateLeafParam leafParam) {

        WayShopCateLeafCondition condition = BeanMapper.map(leafParam, WayShopCateLeafCondition.class);
        List<WayShopCateLeaf> cateLeafList = cateLeafMapper.selectByCondition(condition);
        return BeanMapper.mapAsList(cateLeafList, WayShopCateLeafBo.class);
    }

    @Override @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateRootBo> queryCateAll() {

        List<WayShopCateRoot> cateRootList = cateRootMapper.selectByCondition(null);
        return BeanMapper.mapAsList(cateRootList, WayShopCateRootBo.class);
    }
}
