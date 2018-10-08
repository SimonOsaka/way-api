package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.WayShopLogMapper;
import com.zl.way.sp.model.WayShopLog;
import com.zl.way.sp.model.WayShopLogBo;
import com.zl.way.sp.model.WayShopLogCondition;
import com.zl.way.sp.model.WayShopLogParam;
import com.zl.way.sp.service.WayShopLogService;
import com.zl.way.util.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spWayShopLogService")
public class WayShopLogServiceImpl implements WayShopLogService {

    @Autowired
    private WayShopLogMapper shopLogMapper;

    @Override
    public WayShopLogBo getLog(WayShopLogParam param) {

        WayShopLogCondition condition = BeanMapper.map(param, WayShopLogCondition.class);
        List<WayShopLog> shopLogList = shopLogMapper.selectByCondition(condition, null);
        if (CollectionUtils.isEmpty(shopLogList)) {
            return null;
        }

        return BeanMapper.map(shopLogList.get(0), WayShopLogBo.class);
    }
}
