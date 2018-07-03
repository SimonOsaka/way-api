package com.zl.way.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.discount.mapper.WayDiscountMapper;
import com.zl.way.discount.model.WayDiscount;
import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.model.WayDiscountQueryCondition;
import com.zl.way.discount.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.GeoUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class WayDiscountServiceImpl implements WayDiscountService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long ONE_HOUR_MILLS = 60 * 60 * 1000;

	@Autowired
	private WayDiscountMapper wayDiscountMapper;


	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public List<WayDiscountBo> selectByCondition(WayDiscountParam wayDiscountParam, PageParam pageParam) {
		WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
		condition.setClientLat(wayDiscountParam.getClientLat());
		condition.setClientLng(wayDiscountParam.getClientLng());
		condition.setDiscountId(wayDiscountParam.getDiscountId());
		condition.setLimitTimeExpireEnable(wayDiscountParam.getLimitTimeExpireEnable());

		Pageable pageable = WayPageRequest.of(pageParam);
		logger.info("优惠条件查询列表sql参数{},{}", JSON.toJSONString(condition), JSON.toJSONString(pageable));

		List<WayDiscount> wayDiscountList = wayDiscountMapper.selectByCondition(condition, pageable);
		if (CollectionUtils.isEmpty(wayDiscountList)) {
			return Collections.emptyList();
		}

		Date now = DateTime.now().toDate();
		long nowMills = now.getTime();

		List<WayDiscountBo> wayDiscountBoList = BeanMapper.mapAsList(wayDiscountList, WayDiscountBo.class);
		for (WayDiscountBo wayDiscountBo : wayDiscountBoList) {
			if (null != wayDiscountParam.getClientLng() && null != wayDiscountParam.getClientLat()
					&& null != wayDiscountBo.getShopLng() && null != wayDiscountBo.getShopLat()) {
				BigDecimal distance = GeoUtil
						.getDistance(wayDiscountParam.getClientLng(), wayDiscountParam.getClientLat(),
								wayDiscountBo.getShopLng(), wayDiscountBo.getShopLat());
				wayDiscountBo.setShopDistance(GeoUtil.getDistanceDesc(distance.intValue()));
			}

			if (wayDiscountBo.getLimitTimeExpire() != null && wayDiscountBo.getLimitTimeExpire().after(now)) {
				long expireMills = wayDiscountBo.getLimitTimeExpire().getTime();
				long subMills = expireMills - nowMills;
				if (subMills <= ONE_HOUR_MILLS) {
					wayDiscountBo.setLimitTimeExpireMills(wayDiscountBo.getLimitTimeExpire().getTime());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("优惠条件查询列表组装结果={}", JSON.toJSONString(wayDiscountBoList, true));
		}
		return wayDiscountBoList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public WayDiscount selectOne(Long discountId) {
		WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
		condition.setDiscountId(discountId);

		Pageable pageable = WayPageRequest.of(1, 1);
		logger.info("优惠条件查询sql参数{},{}", JSON.toJSONString(condition), JSON.toJSONString(pageable));

		List<WayDiscount> wayDiscountList = wayDiscountMapper.selectByCondition(condition, pageable);
		if (logger.isDebugEnabled()) {
			logger.debug("优惠条件sql查询结果={}", JSON.toJSONString(wayDiscountList, true));
		}

		if (CollectionUtils.isNotEmpty(wayDiscountList)) {
			return wayDiscountList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public void createDiscount(WayDiscountParam wayDiscountParam) {
		WayDiscount wayDiscount = BeanMapper.map(wayDiscountParam, WayDiscount.class);
		wayDiscount.setShopLng(wayDiscountParam.getClientLng());
		wayDiscount.setShopLat(wayDiscountParam.getClientLat());
		if (wayDiscountParam.getExpireDays() != null) {
			Date limitTimeExpire = DateUtils.addDays(DateTime.now().toDate(), wayDiscountParam.getExpireDays());
			wayDiscount.setLimitTimeExpire(limitTimeExpire);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("创建优惠信息sql条件={}", JSON.toJSONString(wayDiscount));
		}

		wayDiscountMapper.insertSelective(wayDiscount);

	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public void increateReal(Long discountId) {
		WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
		if (null == wayDiscount) {
			logger.info("赞优惠信息discountId={}", discountId);
			return;
		}

		int commodityReal = wayDiscount.getCommodityReal();
		commodityReal = commodityReal + 1;
		WayDiscount upWayDiscount = new WayDiscount();
		upWayDiscount.setId(discountId);
		upWayDiscount.setCommodityReal(commodityReal);

		if (logger.isDebugEnabled()) {
			logger.debug("赞优惠信息sql条件={}", JSON.toJSONString(upWayDiscount));
		}

		wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);
	}
}
