package com.zl.way.city.service.impl;

import com.zl.way.city.mapper.WayCityMapper;
import com.zl.way.city.model.WayCity;
import com.zl.way.city.service.WayCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WayCityServiceImpl implements WayCityService {

    @Autowired
    private WayCityMapper wayCityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayCity> getAllCities(byte isUsed) {
        return wayCityMapper.selectAllByCondition(isUsed);
    }
}
