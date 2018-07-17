package com.zl.way.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.AMapRegeoRequest;
import com.zl.way.amap.model.AMapRegeoResponse;
import com.zl.way.amap.model.AMapStaticMapRequest;
import com.zl.way.amap.model.AMapStaticMapResponse;
import com.zl.way.amap.service.AMapRegeoService;
import com.zl.way.amap.service.AMapStaticMapService;
import com.zl.way.discount.mapper.WayDiscountMapper;
import com.zl.way.discount.mapper.WayDiscountRealMapper;
import com.zl.way.discount.model.*;
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
import org.springframework.beans.factory.annotation.Value;
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

    //	private static final long ONE_HOUR_MILLS = 60 * 60 * 1000;

    @Autowired
    private WayDiscountMapper wayDiscountMapper;

    @Autowired
    private WayDiscountRealMapper wayDiscountRealMapper;

    @Autowired
    private AMapStaticMapService aMapStaticMapService;

    @Autowired
    private AMapRegeoService aMapRegeoService;

    @Value("${custom.discount.imageUrl}")
    private String discountImageUrl;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayDiscountBo> selectByCondition(WayDiscountParam wayDiscountParam,
            PageParam pageParam) {

        WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
        condition.setClientLat(wayDiscountParam.getClientLat());
        condition.setClientLng(wayDiscountParam.getClientLng());
        condition.setDiscountId(wayDiscountParam.getDiscountId());
        condition.setLimitTimeExpireEnable(wayDiscountParam.getLimitTimeExpireEnable());
        condition.setCityCode(wayDiscountParam.getCityCode());
        condition.setRealUserLoginId(wayDiscountParam.getRealUserLoginId());

        Pageable pageable = WayPageRequest.of(pageParam);
        logger.info("优惠条件查询列表sql参数{},{}", JSON.toJSONString(condition),
                JSON.toJSONString(pageable));

        List<WayDiscount> wayDiscountList = wayDiscountMapper
                .selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(wayDiscountList)) {
            return Collections.emptyList();
        }

        //		Date now = DateTime.now().toDate();
        //		long nowMills = now.getTime();

        List<WayDiscountBo> wayDiscountBoList = BeanMapper
                .mapAsList(wayDiscountList, WayDiscountBo.class);
        for (WayDiscountBo wayDiscountBo : wayDiscountBoList) {
            if (null != wayDiscountParam.getClientLng() && null != wayDiscountParam.getClientLat()
                    && null != wayDiscountBo.getShopLng() && null != wayDiscountBo.getShopLat()) {
                BigDecimal distance = GeoUtil.getDistance(wayDiscountParam.getClientLng(),
                        wayDiscountParam.getClientLat(), wayDiscountBo.getShopLng(),
                        wayDiscountBo.getShopLat());
                wayDiscountBo.setShopDistance(GeoUtil.getDistanceDesc(distance.intValue()));
            }

            wayDiscountBo.setCommodityImageUrl(
                    String.format(discountImageUrl, wayDiscountBo.getCommodityCate()));

            //			if (wayDiscountBo.getLimitTimeExpire() != null && wayDiscountBo.getLimitTimeExpire()
            //					.after(now)) {
            //				long expireMills = wayDiscountBo.getLimitTimeExpire().getTime();
            //				long subMills = expireMills - nowMills;
            //				if (subMills <= ONE_HOUR_MILLS) {
            wayDiscountBo.setLimitTimeExpireMills(wayDiscountBo.getLimitTimeExpire().getTime());
            //				}
            //			}
        }

        if (logger.isDebugEnabled()) {
            logger.debug("优惠条件查询列表组装结果={}", JSON.toJSONString(wayDiscountBoList, true));
        }
        return wayDiscountBoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayDiscountBo selectOne(WayDiscountParam wayDiscountParam) {

        WayDiscountQueryCondition condition = new WayDiscountQueryCondition();
        condition.setDiscountId(wayDiscountParam.getDiscountId());
        condition.setRealUserLoginId(wayDiscountParam.getRealUserLoginId());

        Pageable pageable = WayPageRequest.of(1, 1);
        logger.info("优惠条件查询sql参数{},{}", JSON.toJSONString(condition), JSON.toJSONString(pageable));

        List<WayDiscount> wayDiscountList = wayDiscountMapper
                .selectByCondition(condition, pageable);
        if (logger.isDebugEnabled()) {
            logger.debug("优惠条件sql查询结果={}", JSON.toJSONString(wayDiscountList, true));
        }

        if (CollectionUtils.isNotEmpty(wayDiscountList)) {
            WayDiscount wayDiscount = wayDiscountList.get(0);
            WayDiscountBo wayDiscountBo = BeanMapper.map(wayDiscount, WayDiscountBo.class);
            wayDiscountBo.setCommodityImageUrl(
                    String.format(discountImageUrl, wayDiscountBo.getCommodityCate()));
            wayDiscountBo.setLimitTimeExpireMills(wayDiscountBo.getLimitTimeExpire().getTime());

            if (null != wayDiscount.getWayDiscountReal()) {
                wayDiscountBo.setRealType(wayDiscount.getWayDiscountReal().getRealType());
            }
            try {
                String location = wayDiscountBo.getShopLng() + "," + wayDiscountBo.getShopLat();
                AMapStaticMapRequest aMapStaticMapRequest = new AMapStaticMapRequest();
                aMapStaticMapRequest.setLocation(location);
                aMapStaticMapRequest.setSize("700*300");
                AMapStaticMapResponse aMapStaticMapResponse = aMapStaticMapService
                        .getStaticMap(aMapStaticMapRequest);
                wayDiscountBo.setStaticMapUrl(
                        aMapStaticMapResponse.getaMapStaticMapModel().getStaticMapUrl());
            } catch (AMapException e) {
                logger.warn("请求静态地图异常", e);
            }
            return wayDiscountBo;
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
            Date limitTimeExpire = DateUtils
                    .addDays(DateTime.now().toDate(), wayDiscountParam.getExpireDays());
            wayDiscount.setLimitTimeExpire(limitTimeExpire);
        }

        AMapRegeoRequest aMapRegeoRequest = new AMapRegeoRequest();
        aMapRegeoRequest.setLocation(
                wayDiscountParam.getClientLng() + "," + wayDiscountParam.getClientLat());
        try {
            AMapRegeoResponse aMapRegeoResponse = aMapRegeoService.getRegeo(aMapRegeoRequest);
            if (aMapRegeoResponse.getCode() == 200) {
                String cityCode = aMapRegeoResponse.getaMapRegeoModel().getCityCode();
                wayDiscount.setCityCode(cityCode);
            }
        } catch (AMapException e) {
            logger.warn("获取逆地理发生异常，入参={}", aMapRegeoRequest, e);
        }

        wayDiscount.setIsDeleted((byte) 0);
        wayDiscount.setCommodityApprove((byte) 0);

        if (logger.isDebugEnabled()) {
            logger.debug("创建优惠信息sql条件={}", JSON.toJSONString(wayDiscount, true));
        }

        wayDiscountMapper.insertSelective(wayDiscount);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayDiscountRealBo increaseReal(WayDiscountParam wayDiscountParam) {

        Long discountId = wayDiscountParam.getDiscountId();
        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
        if (null == wayDiscount) {
            logger.info("赞优惠信息discountId={}", discountId);
            throw new RuntimeException("优惠信息不存在");
        }

        Long realUserLoginId = wayDiscountParam.getRealUserLoginId();
        WayDiscountRealQueryCondition condition = new WayDiscountRealQueryCondition();
        condition.setDiscountId(discountId);
        condition.setRealUserLoginId(realUserLoginId);

        WayDiscountReal selectReal = wayDiscountRealMapper
                .selectByDiscountIdAndRealUserLoginId(condition);

        int commodityReal = wayDiscount.getCommodityReal();
        commodityReal = commodityReal + 1;
        WayDiscount upWayDiscount = new WayDiscount();
        upWayDiscount.setId(discountId);
        upWayDiscount.setCommodityReal(commodityReal);

        if (null == selectReal) {
            WayDiscountReal record = new WayDiscountReal();
            record.setUserLoginId(wayDiscountParam.getRealUserLoginId());
            record.setDiscountId(discountId);
            record.setRealType((byte) 0);
            wayDiscountRealMapper.insertSelective(record);

            if (logger.isDebugEnabled()) {
                logger.debug("优惠real信息sql条件={}", JSON.toJSONString(record));
            }

        } else {

            logger.info("优惠real已经存在={}", JSON.toJSONString(selectReal));

            int commodityUnReal = wayDiscount.getCommodityUnreal();
            commodityUnReal = commodityUnReal - 1;
            upWayDiscount.setCommodityUnreal(commodityUnReal);
        }

        wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);
        if (logger.isDebugEnabled()) {
            logger.debug("赞优惠信息sql条件={}", JSON.toJSONString(upWayDiscount));
        }

        return getRealAndUnRealCount(discountId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayDiscountRealBo decreaseReal(WayDiscountParam wayDiscountParam) {

        Long discountId = wayDiscountParam.getDiscountId();
        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
        if (null == wayDiscount) {
            logger.info("优惠信息discountId={}", discountId);
            throw new RuntimeException("优惠信息不存在");
        }

        int commodityReal = wayDiscount.getCommodityReal();
        commodityReal = commodityReal - 1;
        commodityReal = commodityReal < 0 ? 0 : commodityReal;
        WayDiscount upWayDiscount = new WayDiscount();
        upWayDiscount.setId(discountId);
        upWayDiscount.setCommodityReal(commodityReal);

        if (logger.isDebugEnabled()) {
            logger.debug("优惠信息sql条件={}", JSON.toJSONString(upWayDiscount));
        }

        wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);

        Long realUserLoginId = wayDiscountParam.getRealUserLoginId();

        WayDiscountRealQueryCondition condition = new WayDiscountRealQueryCondition();
        condition.setDiscountId(discountId);
        condition.setRealUserLoginId(realUserLoginId);
        WayDiscountReal selectReal = wayDiscountRealMapper
                .selectByDiscountIdAndRealUserLoginId(condition);
        if (null == selectReal) {
            logger.info("优惠real不存在={}", JSON.toJSONString(condition));
            throw new RuntimeException("还没有投过");
        }

        WayDiscountReal updateRecord = new WayDiscountReal();
        updateRecord.setId(selectReal.getId());
        updateRecord.setIsDeleted((byte) 1);
        wayDiscountRealMapper.updateByPrimaryKeySelective(updateRecord);

        if (logger.isDebugEnabled()) {
            logger.debug("优惠real信息sql条件={}", JSON.toJSONString(updateRecord));
        }

        return getRealAndUnRealCount(discountId);
    }

    @Override
    public WayDiscountRealBo increaseUnReal(WayDiscountParam wayDiscountParam) {

        Long discountId = wayDiscountParam.getDiscountId();
        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
        if (null == wayDiscount) {
            logger.info("赞优惠信息discountId={}", discountId);
            throw new RuntimeException("优惠信息不存在");
        }

        Long realUserLoginId = wayDiscountParam.getRealUserLoginId();
        WayDiscountRealQueryCondition condition = new WayDiscountRealQueryCondition();
        condition.setDiscountId(discountId);
        condition.setRealUserLoginId(realUserLoginId);

        WayDiscountReal selectReal = wayDiscountRealMapper
                .selectByDiscountIdAndRealUserLoginId(condition);

        int commodityUnreal = wayDiscount.getCommodityUnreal();
        commodityUnreal = commodityUnreal + 1;
        WayDiscount upWayDiscount = new WayDiscount();
        upWayDiscount.setId(discountId);
        upWayDiscount.setCommodityUnreal(commodityUnreal);

        if (null == selectReal) {
            WayDiscountReal record = new WayDiscountReal();
            record.setUserLoginId(wayDiscountParam.getRealUserLoginId());
            record.setDiscountId(discountId);
            record.setRealType((byte) 1);
            wayDiscountRealMapper.insertSelective(record);

            if (logger.isDebugEnabled()) {
                logger.debug("优惠real信息sql条件={}", JSON.toJSONString(record));
            }

        } else {

            logger.info("优惠real已经存在={}", JSON.toJSONString(selectReal));

            int commodityReal = wayDiscount.getCommodityReal();
            commodityReal = commodityReal - 1;
            upWayDiscount.setCommodityReal(commodityReal);
        }

        wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);
        if (logger.isDebugEnabled()) {
            logger.debug("赞优惠信息sql条件={}", JSON.toJSONString(upWayDiscount));
        }

        return getRealAndUnRealCount(discountId);
    }

    @Override
    public WayDiscountRealBo decreaseUnReal(WayDiscountParam wayDiscountParam) {

        Long discountId = wayDiscountParam.getDiscountId();
        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);
        if (null == wayDiscount) {
            logger.info("优惠信息discountId={}", discountId);
            throw new RuntimeException("优惠信息不存在");
        }

        int commodityUnreal = wayDiscount.getCommodityUnreal();
        commodityUnreal = commodityUnreal - 1;
        commodityUnreal = commodityUnreal < 0 ? 0 : commodityUnreal;
        WayDiscount upWayDiscount = new WayDiscount();
        upWayDiscount.setId(discountId);
        upWayDiscount.setCommodityUnreal(commodityUnreal);

        if (logger.isDebugEnabled()) {
            logger.debug("优惠信息sql条件={}", JSON.toJSONString(upWayDiscount));
        }

        wayDiscountMapper.updateByPrimaryKeySelective(upWayDiscount);

        Long realUserLoginId = wayDiscountParam.getRealUserLoginId();

        WayDiscountRealQueryCondition condition = new WayDiscountRealQueryCondition();
        condition.setDiscountId(discountId);
        condition.setRealUserLoginId(realUserLoginId);
        WayDiscountReal selectReal = wayDiscountRealMapper
                .selectByDiscountIdAndRealUserLoginId(condition);
        if (null == selectReal) {
            logger.info("优惠real不存在={}", JSON.toJSONString(condition));
            throw new RuntimeException("还没有投过");
        }

        WayDiscountReal updateRecord = new WayDiscountReal();
        updateRecord.setId(selectReal.getId());
        updateRecord.setIsDeleted((byte) 1);
        wayDiscountRealMapper.updateByPrimaryKeySelective(updateRecord);

        if (logger.isDebugEnabled()) {
            logger.debug("优惠real信息sql条件={}", JSON.toJSONString(updateRecord));
        }

        return getRealAndUnRealCount(discountId);
    }

    private WayDiscountRealBo getRealAndUnRealCount(Long discountId) {

        WayDiscount wayDiscount = wayDiscountMapper.selectByPrimaryKey(discountId);

        WayDiscountRealBo wayDiscountRealBo = new WayDiscountRealBo();
        wayDiscountRealBo.setDiscountReal(wayDiscount.getCommodityReal());
        wayDiscountRealBo.setDiscountUnReal(wayDiscount.getCommodityUnreal());

        return wayDiscountRealBo;
    }
}
