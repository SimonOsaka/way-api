package com.zl.way.commodity.service.impl;

import com.zl.way.commodity.mapper.WayCommodityMapper;
import com.zl.way.commodity.mapper.model.WayCommodityQueryCondition;
import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.commodity.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.NumberUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class WayCommodityServiceImpl implements WayCommodityService {

    @Autowired
    private WayCommodityMapper wayCommodityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodity getCommodityDetail(Long id) {
        if (NumberUtil.isNotLongKey(id)) {
            return null;
        }
        return wayCommodityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WayCommodityBo> pageCommodityByCondition(WayCommodityParam wayCommodityParam, PageParam pageParam) {
        if (null == wayCommodityParam) {
            return Collections.emptyList();
        }

        WayCommodityQueryCondition condition = BeanMapper.map(wayCommodityParam, WayCommodityQueryCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayCommodity> wayCommodityList = wayCommodityMapper.selectByCondition(condition, pageable);
        return BeanMapper.mapAsList(wayCommodityList, WayCommodityBo.class);
    }

}
