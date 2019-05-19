package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.WayShopCateLeafMapper;
import com.zl.way.sp.mapper.WayShopCateRootMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayShopCateService;
import com.zl.way.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("spWayShopCateServiceImpl")
public class WayShopCateServiceImpl implements WayShopCateService {

    @Autowired
    private WayShopCateRootMapper cateRootMapper;

    @Autowired
    private WayShopCateLeafMapper cateLeafMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateRootBo> queryCateRoot() {

        List<WayShopCateRoot> cateRootList = cateRootMapper.selectByCondition(null);
        return BeanMapper.mapAsList(cateRootList, WayShopCateRootBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateLeafBo> queryCateLeaf(WayShopCateLeafParam leafParam) {

        WayShopCateLeafCondition condition = BeanMapper.map(leafParam, WayShopCateLeafCondition.class);
        List<WayShopCateLeaf> cateLeafList = cateLeafMapper.selectByCondition(condition);
        return BeanMapper.mapAsList(cateLeafList, WayShopCateLeafBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopCateRootBo> queryCateAll() {

        List<WayShopCateRoot> cateRootList = cateRootMapper.selectByCondition(null);
        return BeanMapper.mapAsList(cateRootList, WayShopCateRootBo.class);
    }
}
