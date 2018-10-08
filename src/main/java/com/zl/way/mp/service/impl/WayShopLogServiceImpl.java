package com.zl.way.mp.service.impl;

import com.zl.way.mp.enums.WayShopLogSourceEnum;
import com.zl.way.mp.enums.WayShopLogTypeEnum;
import com.zl.way.mp.mapper.WayShopLogMapper;
import com.zl.way.mp.model.WayShopLog;
import com.zl.way.mp.model.WayShopLogBo;
import com.zl.way.mp.model.WayShopLogCondition;
import com.zl.way.mp.model.WayShopLogParam;
import com.zl.way.mp.service.WayShopLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("mpWayShopLogService")
public class WayShopLogServiceImpl implements WayShopLogService {

    @Autowired
    private WayShopLogMapper shopLogMapper;

    @Override
    public List<WayShopLogBo> queryShopLogList(WayShopLogParam param) {

        WayShopLogCondition condition = BeanMapper.map(param, WayShopLogCondition.class);
        List<WayShopLog> shopLogList = shopLogMapper.selectByCondition(condition, null);
        if (CollectionUtils.isEmpty(shopLogList)) {
            return Collections.emptyList();
        }

        List<WayShopLogBo> wayShopLogBoList = BeanMapper.mapAsList(shopLogList, WayShopLogBo.class);
        for (WayShopLogBo shopLogBo : wayShopLogBoList) {
            shopLogBo.setTypeDesc(
                    EnumUtil.getDescByValue(shopLogBo.getType(), WayShopLogTypeEnum.class));
            shopLogBo.setSourceDesc(
                    EnumUtil.getDescByValue(shopLogBo.getSource(), WayShopLogSourceEnum.class));
        }

        return wayShopLogBoList;
    }
}
