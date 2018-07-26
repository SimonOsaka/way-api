package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.SpUserShopMapper;
import com.zl.way.sp.mapper.WayShopMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("spWayShopService")
public class WayShopServiceImpl implements WayShopService {

    @Autowired
    private WayShopMapper shopMapper;

    @Autowired
    private SpUserShopMapper spUserShopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopBo> queryShopList(WayShopParam shopParam, PageParam pageParam) {

        Pageable pageable = WayPageRequest.of(pageParam);
        WayShopCondition condition = BeanMapper.map(shopParam, WayShopCondition.class);
        List<WayShop> shopList = shopMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return Collections.emptyList();
        }
        return BeanMapper.mapAsList(shopList, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayShopBo getShop(WayShopParam shopParam) {

        Pageable pageable = WayPageRequest.of(1, 1);
        WayShopCondition condition = BeanMapper.map(shopParam, WayShopCondition.class);
        List<WayShop> shopList = shopMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return null;
        }
        return BeanMapper.map(shopList.get(0), WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo createShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        shopMapper.insertSelective(wayShopRecord);

        SpUserShop spUserShopRecord = new SpUserShop();
        spUserShopRecord.setShopId(wayShopRecord.getId());
        spUserShopRecord.setUserLoginId(shopParam.getSpUserLoginId());
        spUserShopMapper.insertSelective(spUserShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo updateShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo deleteShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((byte) 1);
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }
}
