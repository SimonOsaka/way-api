package com.zl.way.sp.service.impl;

import com.zl.way.sp.enums.WayShopStatusEnum;
import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.SpUserShopMapper;
import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.mapper.WayShopMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
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

        Pageable pageable = WayPageRequest.of(1, 1);
        WayShopCondition condition = BeanMapper.map(shopParam, WayShopCondition.class);
        List<WayShop> shopList = shopMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return null;
        }
        WayShopBo wayShopBo = BeanMapper.map(shopList.get(0), WayShopBo.class);
        wayShopBo
                .setShopStatusName(WayShopStatusEnum.getStatus(wayShopBo.getIsDeleted()).getDesc());
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
        wayShopRecord.setIsDeleted(WayShopStatusEnum.PENDING.getStatus());
        shopMapper.insertSelective(wayShopRecord);

        SpUserShop spUserShopRecord = new SpUserShop();

        if (null != existSpUserShop) {//已经创建过
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setId(existSpUserShop.getId());
            spUserShopMapper.updateByPrimaryKeySelective(spUserShopRecord);
        } else {
            spUserShopRecord.setShopId(wayShopRecord.getId());
            spUserShopRecord.setUserLoginId(shopParam.getSpUserLoginId());
            spUserShopMapper.insertSelective(spUserShopRecord);
        }
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo updateShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted(WayShopStatusEnum.PENDING.getStatus());
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo deleteShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.DELETED.getStatus()));
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo onlineShop(WayShopParam shopParam) throws BusinessException {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.NORMAL.getStatus()));
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);

        Long shopId = shopParam.getId();
        WayCommodityCondition wayCommodityCondition = new WayCommodityCondition();
        wayCommodityCondition.setShopId(shopId);
        List<WayCommodity> commodityList = commodityMapper
                .selectByCondition(wayCommodityCondition, WayPageRequest.ONE);
        if (CollectionUtils.isEmpty(commodityList)) {
            throw new BusinessException("商品未创建，无法执行上线操作");
        }
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayShopBo offlineShop(WayShopParam shopParam) {

        WayShop wayShopRecord = BeanMapper.map(shopParam, WayShop.class);
        wayShopRecord.setIsDeleted((WayShopStatusEnum.PENDING.getStatus()));
        shopMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayShopBo.class);
    }
}
