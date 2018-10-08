package com.zl.way.mp.service.impl;

import com.zl.way.mp.enums.WayCommodityLogSourceEnum;
import com.zl.way.mp.enums.WayCommodityLogTypeEnum;
import com.zl.way.mp.mapper.WayCommodityLogMapper;
import com.zl.way.mp.model.WayCommodityLog;
import com.zl.way.mp.model.WayCommodityLogBo;
import com.zl.way.mp.model.WayCommodityLogCondition;
import com.zl.way.mp.model.WayCommodityLogParam;
import com.zl.way.mp.service.WayCommodityLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("mpWayCommodityLogService")
public class WayCommodityLogServiceImpl implements WayCommodityLogService {

    @Autowired
    private WayCommodityLogMapper commodityLogMapper;

    @Override
    public List<WayCommodityLogBo> queryCommodityLogList(WayCommodityLogParam param) {

        WayCommodityLogCondition condition = BeanMapper.map(param, WayCommodityLogCondition.class);
        List<WayCommodityLog> commodityLogList = commodityLogMapper
                .selectByCondition(condition, null);
        if (CollectionUtils.isEmpty(commodityLogList)) {
            return Collections.emptyList();
        }

        List<WayCommodityLogBo> wayCommodityLogBoList = BeanMapper
                .mapAsList(commodityLogList, WayCommodityLogBo.class);
        for (WayCommodityLogBo commodityLogBo : wayCommodityLogBoList) {
            commodityLogBo.setTypeDesc(EnumUtil.getDescByValue(commodityLogBo.getType(),
                    WayCommodityLogTypeEnum.class));
            commodityLogBo.setSourceDesc(EnumUtil.getDescByValue(commodityLogBo.getSource(),
                    WayCommodityLogSourceEnum.class));
        }

        return wayCommodityLogBoList;
    }
}
