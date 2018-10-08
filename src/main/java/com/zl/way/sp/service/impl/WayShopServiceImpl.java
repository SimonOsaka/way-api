package com.zl.way.sp.service.impl;

import com.zl.way.sp.enums.WayCommodityStatusEnum;
import com.zl.way.sp.enums.WayShopLogSourceEnum;
import com.zl.way.sp.enums.WayShopLogTypeEnum;
import com.zl.way.sp.enums.WayShopStatusEnum;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.*;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("spWayShopService")
public class WayShopServiceImpl implements WayShopService {

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
        wayShopBo.setShopStatusName(
                EnumUtil.getDescByValue(wayShopBo.getIsDeleted(), WayShopStatusEnum.class));
        //        String cityCode = wayShopBo.getCityCode();
        //        String adCode = wayShopBo.getAdCode();
        //        WayCityCondition wayCityCondition = new WayCityCondition();
        //        wayCityCondition.setAdcode(adCode);
        //        wayCityCondition.setCitycode(cityCode);
        //        List<WayCity> wayCityList = cityMapper
        //                .selectByCondition(wayCityCondition, WayPageRequest.ONE);
        //        if (CollectionUtils.isNotEmpty(wayCityList)) {
        //            WayCity wayCity = wayCityList.get(0);
        //            wayShopBo.setCityName(wayCity.getName());
        //        }
        return wayShopBo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo createShop(WayShopParam shopParam) {

        SpUserShopCondition spUserShopCondition = new SpUserShopCondition();
        spUserShopCondition.setUserLoginId(shopParam.getSpUserLoginId());
        SpUserShop existSpUserShop = spUserShopMapper.selectByCondition(spUserShopCondition);
        if (null != existSpUserShop) {//已经创建过
            Long shopId = existSpUserShop.getShopId();
            WayShopCondition getShopCondition = new WayShopCondition();
            getShopCondition.setId(shopId);
            List<WayShop> wayShopList = shopMapper
                    .selectByCondition(getShopCondition, WayPageRequest.of(1, 1));
            if (CollectionUtils.isNotEmpty(wayShopList)) {
                return BeanMapper.map(wayShopList.get(0), WayShopBo.class);//幂等
            }

        }

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted(WayShopStatusEnum.AUDITTING.getValue());
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.insertSelective(wayShopRecord);

        WayShopQualification wayShopQualificationRecord = BeanMapper
                .map(shopParam.getShopQualificationParam(), WayShopQualification.class);
        wayShopQualificationRecord.setShopId(wayShopRecord.getId());
        shopQualificationMapper.insertSelective(wayShopQualificationRecord);

        SpUserShop spUserShopRecord = new SpUserShop();

        if (null != existSpUserShop) {//已经创建过
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setId(existSpUserShop.getId());
            spUserShopMapper.updateByPrimaryKeySelective(spUserShopRecord);
        } else {
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setUserLoginId(shopParam.getSpUserLoginId());
            spUserShopMapper.insertSelective(spUserShopRecord);

            WayShopLog shopLogRecord = new WayShopLog();
            shopLogRecord.setContent(String.format("商家信息被创建，当前状态[%s]",
                    EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(),
                            WayShopStatusEnum.class)));
            shopLogRecord.setShopId(wayShopRecord.getId());
            shopLogRecord.setType(WayShopLogTypeEnum.INFO.getValue());
            shopLogRecord.setSource(WayShopLogSourceEnum.SP.getValue());
            shopLogMapper.insertSelective(shopLogRecord);
        }
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo updateShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        if ("submit".equalsIgnoreCase(shopParam.getUpdateType())) {//提交更新操作
            wayShopRecord.setIsDeleted(WayShopStatusEnum.AUDITTING.getValue());
        } else if ("save".equalsIgnoreCase(shopParam.getUpdateType())) {//保存更新操作
            wayShopRecord.setIsDeleted(WayShopStatusEnum.DRAFT.getValue());
        }
        wayShopRecord.setUpdateTime(DateTime.now().toDate());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        //修改资质
        WayShopQualificationCondition shopQualificationCondition = new WayShopQualificationCondition();
        shopQualificationCondition.setShopId(shopParam.getId());
        List<WayShopQualification> existShopQualificationList = shopQualificationMapper
                .selectByCondition(shopQualificationCondition, WayPageRequest.ONE);
        if (CollectionUtils.isNotEmpty(existShopQualificationList)) {
            WayShopQualification existShopQualification = existShopQualificationList.get(0);
            WayShopQualification wayShopQualificationRecord = BeanMapper
                    .map(shopParam.getShopQualificationParam(), WayShopQualification.class);
            wayShopQualificationRecord.setId(existShopQualification.getId());
            wayShopQualificationRecord.setShopId(wayShopRecord.getId());
            shopQualificationMapper.updateByPrimaryKeySelective(wayShopQualificationRecord);
        } else {
            WayShopQualification wayShopQualificationRecord = BeanMapper
                    .map(shopParam.getShopQualificationParam(), WayShopQualification.class);
            wayShopQualificationRecord.setShopId(wayShopRecord.getId());
            shopQualificationMapper.insertSelective(wayShopQualificationRecord);
        }

        WayShopLog shopLogRecord = new WayShopLog();
        if ("submit".equalsIgnoreCase(shopParam.getUpdateType())) {//提交更新操作
            shopLogRecord.setContent(String.format("商家信息被修改并提交，当前状态[%s]",
                    EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(),
                            WayShopStatusEnum.class)));
        } else if ("save".equalsIgnoreCase(shopParam.getUpdateType())) {
            shopLogRecord.setContent(String.format("商家信息被修改并保存，当前状态[%s]",
                    EnumUtil.getDescByValue(wayShopRecord.getIsDeleted(),
                            WayShopStatusEnum.class)));
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
        List<WayCommodity> commodityList = commodityMapper
                .selectByCondition(wayCommodityCondition, WayPageRequest.ONE);
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
}
