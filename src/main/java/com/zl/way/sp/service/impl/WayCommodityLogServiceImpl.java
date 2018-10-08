package com.zl.way.sp.service.impl;

import com.zl.way.sp.mapper.WayCommodityLogMapper;
import com.zl.way.sp.model.WayCommodityLog;
import com.zl.way.sp.model.WayCommodityLogBo;
import com.zl.way.sp.model.WayCommodityLogCondition;
import com.zl.way.sp.model.WayCommodityLogParam;
import com.zl.way.sp.service.WayCommodityLogService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spWayCommodityLogService")
public class WayCommodityLogServiceImpl implements WayCommodityLogService {

    @Autowired
    private WayCommodityLogMapper commodityLogMapper;

    @Override
    public WayCommodityLogBo getLog(WayCommodityLogParam param) {

        WayCommodityLogCondition condition = BeanMapper.map(param, WayCommodityLogCondition.class);
        List<WayCommodityLog> commodityLogList = commodityLogMapper
                .selectByCondition(condition, WayPageRequest.ONE);
        if (CollectionUtils.isEmpty(commodityLogList)) {
            return null;
        }
        return BeanMapper.map(commodityLogList.get(0), WayCommodityLogBo.class);
    }
}
