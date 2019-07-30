package com.zl.way.mp.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.mp.mapper.WayShopExtraMapper;
import com.zl.way.mp.model.WayShopExtraBo;
import com.zl.way.mp.model.WayShopExtraCondition;
import com.zl.way.mp.model.WayShopExtraParam;
import com.zl.way.mp.service.WayShopExtraService;

@Service("mpWayShopExtraServiceImpl")
public class WayShopExtraServiceImpl implements WayShopExtraService {

    private WayShopExtraMapper shopExtraMapper;

    @Autowired
    public WayShopExtraServiceImpl(WayShopExtraMapper shopExtraMapper) {
        this.shopExtraMapper = shopExtraMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WayShopExtraBo changeToManager(WayShopExtraParam param) {
        WayShopExtraCondition condition = new WayShopExtraCondition();
        condition.setId(param.getId());
        condition.setOwnerType((byte)2);
        condition.setUpdateTime(DateTime.now().toDate());

        shopExtraMapper.updateByPrimaryKeySelective(condition);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WayShopExtraBo changeToSelf(WayShopExtraParam param) {
        WayShopExtraCondition condition = new WayShopExtraCondition();
        condition.setId(param.getId());
        condition.setOwnerType((byte)1);
        condition.setUpdateTime(DateTime.now().toDate());

        shopExtraMapper.updateByPrimaryKeySelective(condition);
        return null;
    }
}
