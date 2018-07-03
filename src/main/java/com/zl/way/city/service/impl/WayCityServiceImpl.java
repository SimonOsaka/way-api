package com.zl.way.city.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.city.mapper.WayCityMapper;
import com.zl.way.city.model.WayCity;
import com.zl.way.city.service.WayCityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WayCityServiceImpl implements WayCityService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WayCityMapper wayCityMapper;

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public List<WayCity> getAllCities(byte isUsed) {

		List<WayCity> wayCityList = wayCityMapper.selectAllByCondition(isUsed);
		if (logger.isDebugEnabled()) {
			logger.debug("所有城市={}", JSON.toJSONString(wayCityList, true));
			logger.debug("所有城市数量={}", wayCityList.size());
		}
		return wayCityList;
	}

	@Override
	public void updatePinyin() throws PinyinException {
		List<WayCity> wayCityList = wayCityMapper.selectAllByCondition(null);
		int wayCityListSize = wayCityList.size();
		for (int i = 0; i < wayCityListSize; i++) {
			WayCity wayCity = wayCityList.get(i);

			WayCity updateWayCity = new WayCity();
			updateWayCity.setId(wayCity.getId());
			updateWayCity.setPinyinAll(PinyinHelper
					.convertToPinyinString(wayCity.getName(), StringUtils.EMPTY, PinyinFormat.WITHOUT_TONE));
			updateWayCity.setPinyinShort(PinyinHelper.getShortPinyin(wayCity.getName()));
			wayCityMapper.updateByPrimaryKeySelective(updateWayCity);
		}
	}
}
