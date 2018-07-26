package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.SpUserShopMapper;
import com.zl.way.sp.model.SpUserShop;
import com.zl.way.sp.model.SpUserShopBo;
import com.zl.way.sp.model.SpUserShopCondition;
import com.zl.way.sp.model.SpUserShopParam;
import com.zl.way.sp.service.SpUserShopService;
import com.zl.way.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("spUserShopServiceImpl")
public class SpUserShopServiceImpl implements SpUserShopService {

    @Autowired
    private SpUserShopMapper spUserShopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public SpUserShopBo getUserShop(SpUserShopParam param) {

        SpUserShopCondition condition = BeanMapper.map(param, SpUserShopCondition.class);
        SpUserShop spUserShop = spUserShopMapper.selectByCondition(condition);
        return BeanMapper.map(spUserShop, SpUserShopBo.class);
    }

}
