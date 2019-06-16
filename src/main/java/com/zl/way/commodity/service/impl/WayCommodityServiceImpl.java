package com.zl.way.commodity.service.impl;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zl.way.commodity.mapper.WayCommodityAbstractWordRelationshipMapper;
import com.zl.way.commodity.mapper.WayCommodityMapper;
import com.zl.way.commodity.mapper.model.WayCommodityAbstractWordRelationshipCondition;
import com.zl.way.commodity.mapper.model.WayCommodityCondition;
import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityAbstractWordRelationship;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.commodity.service.WayCommodityService;
import com.zl.way.constants.WaySymbolConstants;
import com.zl.way.shop.mapper.WayShopMapper;
import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.model.WayShopBo;
import com.zl.way.util.*;

@Service
public class WayCommodityServiceImpl implements WayCommodityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private WayCommodityMapper wayCommodityMapper;

    private WayCommodityAbstractWordRelationshipMapper abstractWordRelationshipMapper;

    private WayShopMapper shopMapper;

    @Autowired
    public WayCommodityServiceImpl(WayCommodityMapper wayCommodityMapper,
        WayCommodityAbstractWordRelationshipMapper abstractWordRelationshipMapper, WayShopMapper shopMapper) {
        this.wayCommodityMapper = wayCommodityMapper;
        this.abstractWordRelationshipMapper = abstractWordRelationshipMapper;
        this.shopMapper = shopMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodity getCommodityDetail(Long id) {
        return getCommodity(id);
    }

    private WayCommodity getCommodity(Long id) {
        if (NumberUtil.isNotLongKey(id)) {
            logger.info("商品详情id={}", id);
            return null;
        }

        WayCommodity wayCommodity = wayCommodityMapper.selectByPrimaryKey(id);
        if (logger.isDebugEnabled()) {
            logger.debug("商品详情结果={}", JSON.toJSONString(wayCommodity, true));
        }
        return wayCommodity;
    }

    @Override
    public List<WayCommodityBo> pageCommodityByCondition(WayCommodityParam wayCommodityParam, PageParam pageParam) {
        if (null == wayCommodityParam) {
            logger.info("商品列表查询参数={}", wayCommodityParam);
            return Collections.emptyList();
        }

        WayCommodityCondition condition = BeanMapper.map(wayCommodityParam, WayCommodityCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);
        logger.info("商品列表sql条件condition={},pageable={}", condition, pageable);

        List<WayCommodity> wayCommodityList = wayCommodityMapper.selectByCondition(condition, pageable);
        if (logger.isDebugEnabled()) {
            logger.debug("商品列表sql结果={}", JSON.toJSONString(wayCommodityList, true));
        }
        return BeanMapper.mapAsList(wayCommodityList, WayCommodityBo.class);
    }

    @Override
    public List<WayCommodityBo> queryRelationCommodity(WayCommodityParam wayCommodityParam) {
        Long commodityId = wayCommodityParam.getId();
        WayCommodity commodity = getCommodity(commodityId);
        if (null == commodity) {
            logger.info("商品不存在，commodityId={}", commodityId);
            return Collections.emptyList();
        }
        Long shopId = commodity.getShopId();
        String abstractWordIds = commodity.getAbstractWordIds();
        List<WayCommodity> commodityList;
        if (StringUtils.equalsIgnoreCase(WaySymbolConstants.BRACKETS, abstractWordIds)) {
            // 查询相同商品分类的商品数据
            commodityList = querySameShopCateCommodity(shopId, commodityId);
        } else {
            // 查询抽象关联词的商品数据
            List<Integer> abstractWordIdList = JSONArray.parseArray(abstractWordIds, Integer.class);
            commodityList = queryRelationCommodity(shopId, commodityId, abstractWordIdList);
        }
        if (CollectionUtils.isNotEmpty(commodityList)) {
            return toCommodityBoList(commodityList, wayCommodityParam.getClientLng(), wayCommodityParam.getClientLat());
        }
        return Collections.emptyList();
    }

    /**
     * 根据商家id，查询cityCode、adCode和shopCateLeafId相关的商品集合
     * 
     * @param shopId
     * @return 最多20个商品集合
     */
    private List<WayCommodity> querySameShopCateCommodity(Long shopId, Long commodityId) {
        WayShop shop = shopMapper.selectByPrimaryKey(shopId);
        if (null == shop) {
            return Collections.emptyList();
        }
        // 查询商家有相同cityCode、adCode和shopCateLeafId的商品集合
        String cityCode = shop.getCityCode();
        String adCode = shop.getAdCode();
        Integer shopCateLeafId = shop.getShopCateLeafId();
        WayCommodityCondition commodityCondition = new WayCommodityCondition();
        commodityCondition.setShopAdCode(adCode);
        commodityCondition.setShopCityCode(cityCode);
        commodityCondition.setShopCateLeafId(shopCateLeafId);
        commodityCondition.setIdExclude(commodityId);
        List<WayCommodity> allCommodityList = new ArrayList<>(WayPageRequest.TWENTY.getPageSize());
        List<WayCommodity> commodityList =
            wayCommodityMapper.selectByCondition(commodityCondition, WayPageRequest.TWENTY);
        int currentCommodityNum = CollectionUtils.size(commodityList);
        if (currentCommodityNum != 0) {
            allCommodityList.addAll(commodityList);
        }
        if (currentCommodityNum < WayPageRequest.TWENTY.getPageSize()) {
            // 查询商家有相同cityCode、shopCateLeafId和不包含相同adCode的商品集合
            WayCommodityCondition moreCommodityCondition = new WayCommodityCondition();
            moreCommodityCondition.setShopAdCodeExclude(adCode);
            moreCommodityCondition.setShopCityCode(cityCode);
            moreCommodityCondition.setShopCateLeafId(shopCateLeafId);
            moreCommodityCondition.setIdExclude(commodityId);
            int moreNum = WayPageRequest.TWENTY.getPageSize() - currentCommodityNum;
            List<WayCommodity> moreCommodityList =
                wayCommodityMapper.selectByCondition(moreCommodityCondition, WayPageRequest.of(1, moreNum));
            currentCommodityNum = CollectionUtils.size(moreCommodityList);
            if (currentCommodityNum != 0) {
                allCommodityList.addAll(moreCommodityList);
            }
        }

        return allCommodityList;
    }

    /**
     * 根据抽象词id集合，查询抽象关联词的商品集合
     * 
     * @param abstractWordIdList
     * @return
     */
    private List<WayCommodity> queryRelationCommodity(Long shopId, Long commodityIdExclude,
        List<Integer> abstractWordIdList) {
        // 查询关联词
        WayCommodityAbstractWordRelationshipCondition relationshipCondition =
            new WayCommodityAbstractWordRelationshipCondition();
        relationshipCondition.setAbstractWordIdList(abstractWordIdList);

        List<WayCommodityAbstractWordRelationship> abstractWordRelationshipList =
            abstractWordRelationshipMapper.selectByCondition(relationshipCondition, null);
        if (CollectionUtils.isNotEmpty(abstractWordRelationshipList)) {
            // 取出所有关联词
            Set<Integer> allRelationAbstractWordIdList = new HashSet<>();
            for (WayCommodityAbstractWordRelationship relationship : abstractWordRelationshipList) {
                String abstractWordIds = relationship.getAbstractWordIds();
                List<Integer> eachAbstractWordIdList = JSONArray.parseArray(abstractWordIds, Integer.class);
                allRelationAbstractWordIdList.addAll(eachAbstractWordIdList);
            }
            // 根据关联词id集合，查询符合条件的商品集合
            WayShop shop = shopMapper.selectByPrimaryKey(shopId);
            if (null == shop) {
                return Collections.emptyList();
            }
            // 查询商家有相同cityCode的商品集合
            String cityCode = shop.getCityCode();
            WayCommodityCondition commodityQueryCondition = new WayCommodityCondition();
            commodityQueryCondition.setShopCityCode(cityCode);
            commodityQueryCondition.setAbstractWordIdList(new ArrayList<>(allRelationAbstractWordIdList));
            commodityQueryCondition.setIdExclude(commodityIdExclude);
            List<WayCommodity> commodityList = wayCommodityMapper.selectByCondition(commodityQueryCondition, null);
            if (CollectionUtils.isNotEmpty(commodityList)) {
                return commodityList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 商品集合转换并计算经纬度距离
     * 
     * @param commodityList
     * @param clientLat
     * @param clientLng
     * @return
     */
    private List<WayCommodityBo> toCommodityBoList(List<WayCommodity> commodityList, BigDecimal clientLat,
        BigDecimal clientLng) {
        List<WayCommodityBo> commodityBoList = new ArrayList<>(commodityList.size());
        for (WayCommodity commodity : commodityList) {
            WayCommodityBo commodityBo = BeanMapper.map(commodity, WayCommodityBo.class);
            commodityBo.setWayShop(BeanMapper.map(commodityBo.getShop(), WayShopBo.class));
            WayShopBo shopBo = commodityBo.getWayShop();
            BigDecimal distance = GeoUtil.getDistance(clientLng, clientLat, shopBo.getShopLng(), shopBo.getShopLat());
            if (null != distance) {
                String distanceFullString = GeoUtil.getDistanceDesc(distance.doubleValue());
                shopBo.setShopDistance(distanceFullString);
            }
            commodityBoList.add(commodityBo);
        }
        return commodityBoList;
    }

}
