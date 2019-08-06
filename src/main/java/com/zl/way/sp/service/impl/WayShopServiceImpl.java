package com.zl.way.sp.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.amap.exception.AMapException;
import com.zl.way.amap.model.*;
import com.zl.way.amap.service.AMapGeocodeService;
import com.zl.way.sp.enums.*;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.*;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service("spWayShopService")
public class WayShopServiceImpl implements WayShopService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WayShopMapper shopMapper;

    @Autowired
    private SpUserShopMapper spUserShopMapper;

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Autowired
    private WayShopLogMapper shopLogMapper;

    @Autowired
    private WayShopQualificationMapper shopQualificationMapper;

    @Autowired
    private WayShopExtraMapper shopExtraMapper;

    @Autowired
    private AMapGeocodeService aMapRegeoService;

    @Autowired
    private AMapGeocodeService aMapGeocodeService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayShopBo> queryShopList(WayShopParam shopParam, PageParam pageParam) {

        Pageable pageable = WayPageRequest.of(pageParam);
        WayShopCondition condition = BeanMapper.map(shopParam, WayShopCondition.class);
        List<WayShop> shopList = shopMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return Collections.emptyList();
        }

        return BeanMapper.mapAsList(shopList, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayShopBo getShop(WayShopParam shopParam) {

        WayShopCondition condition = BeanMapper.map(shopParam, WayShopCondition.class);
        List<WayShop> shopList = shopMapper.selectByCondition(condition, WayPageRequest.ONE);
        if (CollectionUtils.isEmpty(shopList)) {
            return null;
        }
        WayShopBo wayShopBo = BeanMapper.map(shopList.get(0), WayShopBo.class);
        wayShopBo.setShopStatusName(EnumUtil.getDescByValue(wayShopBo.getIsDeleted(), WayShopStatusEnum.class));
        // String cityCode = wayShopBo.getCityCode();
        // String adCode = wayShopBo.getAdCode();
        // WayCityCondition wayCityCondition = new WayCityCondition();
        // wayCityCondition.setAdcode(adCode);
        // wayCityCondition.setCitycode(cityCode);
        // List<WayCity> wayCityList = cityMapper
        // .selectByCondition(wayCityCondition, WayPageRequest.ONE);
        // if (CollectionUtils.isNotEmpty(wayCityList)) {
        // WayCity wayCity = wayCityList.get(0);
        // wayShopBo.setCityName(wayCity.getName());
        // }
        return wayShopBo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo createShop(WayShopParam shopParam) throws BusinessException {
        // 商家用户关联
        SpUserShopCondition spUserShopCondition = new SpUserShopCondition();
        spUserShopCondition.setUserLoginId(shopParam.getSpUserLoginId());
        SpUserShop existSpUserShop = spUserShopMapper.selectByCondition(spUserShopCondition);
        // 已经创建过
        if (null != existSpUserShop) {
            Long shopId = existSpUserShop.getShopId();
            WayShopCondition getShopCondition = new WayShopCondition();
            getShopCondition.setId(shopId);
            List<WayShop> wayShopList = shopMapper.selectByCondition(getShopCondition, WayPageRequest.of(1, 1));
            if (CollectionUtils.isNotEmpty(wayShopList)) {
                // 幂等
                return BeanMapper.map(wayShopList.get(0), WayShopBo.class);
            }

        }

        // 商家信息写入
        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted(WayShopStatusEnum.AUDITTING.getValue());
        wayShopRecord.setUpdateTime(DateTime.now().toDate());

        AMapRegeoRequest aMapRegeoRequest = new AMapRegeoRequest();
        aMapRegeoRequest.setLocation(wayShopRecord.getShopLng() + "," + wayShopRecord.getShopLat());
        try {
            AMapRegeoResponse aMapRegeoResponse = aMapRegeoService.getRegeo(aMapRegeoRequest);
            if (200 == aMapRegeoResponse.getCode()) {
                wayShopRecord.setCityCode(aMapRegeoResponse.getaMapRegeoModel().getCityCode());
                wayShopRecord.setAdCode(aMapRegeoResponse.getaMapRegeoModel().getAdCode());
            } else {
                logger.warn("调用aMapRegeoService.getRegeo返回非200，原因：{}", aMapRegeoResponse.getMsg());
            }
        } catch (AMapException e) {
            logger.warn("调用aMapRegeoService.getRegeo发生异常：{}", e);
        }

        if (!this.validSameCityCode(wayShopRecord.getCityCode(), wayShopRecord.getShopAddress())) {
            throw new BusinessException("商家地址和地图内的地址不在同一个城市");
        }
        shopMapper.insertSelective(wayShopRecord);

        // 商家额外信息写入，默认商家自行创建。审核后，根据资质照片内容修改为管理人员创建。
        WayShopExtra shopExtraRecord = new WayShopExtra();
        shopExtraRecord.setShopId(wayShopRecord.getId());
        shopExtraRecord.setOwnerType(WayShopExtraOwnerTypeEnum.DEFAULT.getValue());
        shopExtraRecord.setCreateTime(DateTime.now().toDate());
        shopExtraRecord.setUpdateTime(DateTime.now().toDate());
        shopExtraMapper.insertSelective(shopExtraRecord);

        // 商家资质写入
        if (null != shopParam.getShopQualificationParam()) {
            WayShopQualification wayShopQualificationRecord =
                BeanMapper.map(shopParam.getShopQualificationParam(), WayShopQualification.class);
            wayShopQualificationRecord.setShopId(wayShopRecord.getId());
            shopQualificationMapper.insertSelective(wayShopQualificationRecord);
        }

        SpUserShop spUserShopRecord = new SpUserShop();

        // 已经创建过
        if (null != existSpUserShop) {
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setId(existSpUserShop.getId());
            spUserShopMapper.updateByPrimaryKeySelective(spUserShopRecord);
        } else {
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setUserLoginId(shopParam.getSpUserLoginId());
            spUserShopMapper.insertSelective(spUserShopRecord);

            WayShopLog shopLogRecord = new WayShopLog();
            shopLogRecord.setContent(String.format("商家信息被创建，当前状态[%s]",
                EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(), WayShopStatusEnum.class)));
            shopLogRecord.setShopId(wayShopRecord.getId());
            shopLogRecord.setType(WayShopLogTypeEnum.INFO.getValue());
            shopLogRecord.setSource(WayShopLogSourceEnum.SP.getValue());
            shopLogMapper.insertSelective(shopLogRecord);
        }
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo updateShop(WayShopParam shopParam) throws BusinessException {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        AMapRegeoRequest aMapRegeoRequest = new AMapRegeoRequest();
        aMapRegeoRequest.setLocation(wayShopRecord.getShopLng() + "," + wayShopRecord.getShopLat());
        try {
            AMapRegeoResponse aMapRegeoResponse = aMapRegeoService.getRegeo(aMapRegeoRequest);
            if (200 == aMapRegeoResponse.getCode()) {
                wayShopRecord.setCityCode(aMapRegeoResponse.getaMapRegeoModel().getCityCode());
                wayShopRecord.setAdCode(aMapRegeoResponse.getaMapRegeoModel().getAdCode());
            } else {
                logger.warn("调用aMapRegeoService.getRegeo返回非200，原因：{}", aMapRegeoResponse.getMsg());
            }
        } catch (AMapException e) {
            logger.warn("调用aMapRegeoService.getRegeo发生异常：{}", e);
        }

        if (!this.validSameCityCode(wayShopRecord.getCityCode(), wayShopRecord.getShopAddress())) {
            throw new BusinessException("商家地址和地图内的地址不在同一个城市");
        }

        if ("submit".equalsIgnoreCase(shopParam.getUpdateType())) {// 提交更新操作
            wayShopRecord.setIsDeleted(WayShopStatusEnum.AUDITTING.getValue());
        } else if ("save".equalsIgnoreCase(shopParam.getUpdateType())) {// 保存更新操作
            wayShopRecord.setIsDeleted(WayShopStatusEnum.DRAFT.getValue());
        }
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        // 修改资质
        WayShopQualificationCondition shopQualificationCondition = new WayShopQualificationCondition();
        shopQualificationCondition.setShopId(shopParam.getId());
        List<WayShopQualification> existShopQualificationList =
            shopQualificationMapper.selectByCondition(shopQualificationCondition, WayPageRequest.ONE);
        if (CollectionUtils.isNotEmpty(existShopQualificationList)) {
            WayShopQualification existShopQualification = existShopQualificationList.get(0);
            WayShopQualification wayShopQualificationRecord =
                BeanMapper.map(shopParam.getShopQualificationParam(), WayShopQualification.class);
            wayShopQualificationRecord.setId(existShopQualification.getId());
            wayShopQualificationRecord.setShopId(wayShopRecord.getId());
            shopQualificationMapper.updateByPrimaryKeySelective(wayShopQualificationRecord);
        } else {
            WayShopQualification wayShopQualificationRecord =
                BeanMapper.map(shopParam.getShopQualificationParam(), WayShopQualification.class);
            wayShopQualificationRecord.setShopId(wayShopRecord.getId());
            shopQualificationMapper.insertSelective(wayShopQualificationRecord);
        }

        WayShopLog shopLogRecord = new WayShopLog();
        if ("submit".equalsIgnoreCase(shopParam.getUpdateType())) {// 提交更新操作
            shopLogRecord.setContent(String.format("商家信息被修改并提交，当前状态[%s]",
                EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(), WayShopStatusEnum.class)));
        } else if ("save".equalsIgnoreCase(shopParam.getUpdateType())) {
            shopLogRecord.setContent(String.format("商家信息被修改并保存，当前状态[%s]",
                EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(), WayShopStatusEnum.class)));
        }
        shopLogRecord.setShopId(wayShopRecord.getId());
        shopLogRecord.setType(WayShopLogTypeEnum.INFO.getValue());
        shopLogRecord.setSource(WayShopLogSourceEnum.SP.getValue());
        shopLogMapper.insertSelective(shopLogRecord);

        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo deleteShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.DELETED.getValue()));
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo onlineShop(WayShopParam shopParam) throws BusinessException {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.NORMAL.getValue()));
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);

        Long shopId = shopParam.getId();
        WayCommodityCondition wayCommodityCondition = new WayCommodityCondition();
        wayCommodityCondition.setShopId(shopId);
        List<WayCommodity> commodityList = commodityMapper.selectByCondition(wayCommodityCondition, WayPageRequest.ONE);
        if (CollectionUtils.isEmpty(commodityList)) {
            throw new BusinessException("商品未创建，无法执行上线操作");
        }

        if (!WayCommodityStatusEnum.NORMAL.getValue().equals(commodityList.get(0).getIsDeleted())) {
            throw new BusinessException("商品未上架，无法执行上线操作");
        }

        WayShopLog shopLogRecord = new WayShopLog();
        shopLogRecord.setContent(String.format("商家状态修改，当前状态[%s]",
            EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(), WayShopStatusEnum.class)));
        shopLogRecord.setShopId(wayShopRecord.getId());
        shopLogRecord.setType(WayShopLogTypeEnum.STATUS.getValue());
        shopLogRecord.setSource(WayShopLogSourceEnum.SP.getValue());
        shopLogMapper.insertSelective(shopLogRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo offlineShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.PENDING.getValue()));
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);

        WayShopLog shopLogRecord = new WayShopLog();
        shopLogRecord.setContent(String.format("商家状态修改，当前状态[%s]",
            EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(), WayShopStatusEnum.class)));
        shopLogRecord.setShopId(wayShopRecord.getId());
        shopLogRecord.setType(WayShopLogTypeEnum.STATUS.getValue());
        shopLogRecord.setSource(WayShopLogSourceEnum.SP.getValue());
        shopLogMapper.insertSelective(shopLogRecord);

        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    /**
     * 验证经纬度的citycode和地址的citycode是否一致
     * 
     * @param cityCode
     * @param address
     * @return true：一致，false：不一致
     */
    private boolean validSameCityCode(String cityCode, String address) {
        AMapGeoRequest aMapGeoRequest = new AMapGeoRequest();
        aMapGeoRequest.setAddress(address);
        try {
            AMapGeoResponse aMapGeoResponse = aMapGeocodeService.getGeo(aMapGeoRequest);
            if (200 == aMapGeoResponse.getCode()) {
                if (CollectionUtils.isNotEmpty(aMapGeoResponse.getaMapGeoModelList())) {
                    List<AMapGeoModel> aMapGeoModelList = aMapGeoResponse.getaMapGeoModelList();
                    AMapGeoModel aMapGeoModel = aMapGeoModelList.get(0);
                    String addressCityCode = aMapGeoModel.getCityCode();
                    if (logger.isDebugEnabled()) {
                        logger.debug("经纬度citycode={}，地址citycode={}", cityCode, addressCityCode);
                    }
                    return StringUtils.equalsIgnoreCase(addressCityCode, cityCode);
                }
            }
        } catch (AMapException e) {
        }
        return false;
    }
}
