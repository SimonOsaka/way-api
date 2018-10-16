package com.zl.way.mp.service.impl;

import com.zl.way.mp.mapper.WayShopQualificationMapper;
import com.zl.way.mp.model.WayShopQualification;
import com.zl.way.mp.model.WayShopQualificationBo;
import com.zl.way.mp.model.WayShopQualificationCondition;
import com.zl.way.mp.model.WayShopQualificationParam;
import com.zl.way.mp.service.WayShopQualificationService;
import com.zl.way.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mpWayShopQualificationService")
public class WayShopQualificationServiceImpl implements WayShopQualificationService {

    @Autowired
    private WayShopQualificationMapper shopQualificationMapper;

    @Override
    public WayShopQualificationBo getShopQualification(WayShopQualificationParam param) {

        WayShopQualificationCondition condition = BeanMapper
                .map(param, WayShopQualificationCondition.class);

        WayShopQualification shopQualification = shopQualificationMapper
                .selectByPrimaryKey(condition.getId());
        return BeanMapper.map(shopQualification, WayShopQualificationBo.class);
    }
}
