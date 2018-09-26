package com.zl.way.mp.service.impl;

import com.alibaba.fastjson.JSON;
import com.zl.way.mp.mapper.WayDiscountMapper;
import com.zl.way.mp.model.WayDiscount;
import com.zl.way.mp.model.WayDiscountBo;
import com.zl.way.mp.model.WayDiscountParam;
import com.zl.way.mp.model.WayDiscountQueryCondition;
import com.zl.way.mp.service.WayDiscountService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.GeoUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service("mpWayDiscountServiceImpl")
public class WayDiscountServiceImpl implements WayDiscountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayDiscountMapper wayDiscountMapper;

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

            wayDiscountBo.setLimitTimeExpireMills(wayDiscountBo.getLimitTimeExpire().getTime());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("优惠条件查询列表组装结果={}", JSON.toJSONString(wayDiscountBoList, true));
        }
        return wayDiscountBoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayDiscountBo deleteByCondition(WayDiscountParam wayDiscountParam) {

        WayDiscount record = new WayDiscount();
        record.setId(wayDiscountParam.getDiscountId());
        record.setIsDeleted((byte) 1);
        wayDiscountMapper.updateByPrimaryKeySelective(record);
        return BeanMapper.map(record, WayDiscountBo.class);
    }

    @Override
    public Long countByCondition(WayDiscountParam wayDiscountParam) {

        WayDiscountQueryCondition condition = BeanMapper
                .map(wayDiscountParam, WayDiscountQueryCondition.class);
        return wayDiscountMapper.countByCondition(condition);
    }
}
