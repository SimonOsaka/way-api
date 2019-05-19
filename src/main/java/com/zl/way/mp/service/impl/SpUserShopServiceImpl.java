package com.zl.way.mp.service.impl;

import com.zl.way.mp.mapper.SpUserShopMapper;
import com.zl.way.mp.model.SpUserShop;
import com.zl.way.mp.model.SpUserShopCondition;
import com.zl.way.mp.service.SpUserShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mpSpUserShopService")
public class SpUserShopServiceImpl implements SpUserShopService {

    @Autowired
    private SpUserShopMapper spUserShopMapper;

    @Override
    public List<SpUserShop> querySpUserShopList(SpUserShop params, PageParam pageParam) {
        SpUserShopCondition condition = BeanMapper.map(params, SpUserShopCondition.class);
        return spUserShopMapper.selectByCondition(condition, WayPageRequest.of(pageParam));
    }

    @Override
    public void updateSpUserShop(SpUserShop params) {
        params.setUpdateTime(DateTime.now().toDate());
        spUserShopMapper.updateByPrimaryKeySelective(params);
    }
}
