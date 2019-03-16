package com.zl.way.www.service.impl;

import com.zl.way.www.mapper.WayLandMapper;
import com.zl.way.www.model.WayLand;
import com.zl.way.www.service.MainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service public class MainServiceImpl implements MainService {

    private final WayLandMapper landMapper;

    @Autowired public MainServiceImpl(WayLandMapper landMapper) {
        this.landMapper = landMapper;
    }

    @Override @Transactional(readOnly = true, rollbackFor = Exception.class) public String getAndroidApkLink() {
        WayLand land = landMapper.selectByPropKey("android_apk_link");
        if (null == land) {
            return StringUtils.EMPTY;
        }
        return land.getPropVal();
    }
}
