package com.zl.way.mp.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.mp.enums.WayCommodityLogSourceEnum;
import com.zl.way.mp.enums.WayCommodityLogTypeEnum;
import com.zl.way.mp.enums.WayCommodityStatusEnum;
import com.zl.way.mp.exception.BusinessException;
import com.zl.way.mp.mapper.WayCommodityLogMapper;
import com.zl.way.mp.mapper.WayCommodityMapper;
import com.zl.way.mp.model.*;
import com.zl.way.mp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service("mpWayCommodityService")
public class WayCommodityServiceImpl implements WayCommodityService {

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Autowired
    private WayCommodityLogMapper commodityLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<WayCommodityBo> queryCommodityList(WayCommodityParam shopParam, PageParam pageParam) {

        Pageable pageable = WayPageRequest.of(pageParam);
        WayCommodityCondition condition = BeanMapper.map(shopParam, WayCommodityCondition.class);
        List<WayCommodity> commodityList = commodityMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(commodityList)) {
            return Collections.emptyList();
        }
        return BeanMapper.mapAsList(commodityList, WayCommodityBo.class);
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodityBo getCommodity(WayCommodityParam commodityParam) {
    
        Pageable pageable = WayPageRequest.of(1, 1);
        WayCommodityCondition condition = BeanMapper
                .map(commodityParam, WayCommodityCondition.class);
        List<WayCommodity> shopList = commodityMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(shopList)) {
            return null;
        }
        WayCommodityBo wayCommodityBo = BeanMapper.map(shopList.get(0), WayCommodityBo.class);
        List<String> imgUrlList = new ArrayList<>(5);
        if (StringUtils.isNotBlank(wayCommodityBo.getImgUrl())) {
            imgUrlList.add(wayCommodityBo.getImgUrl());
        }
        if (StringUtils.isNotBlank(wayCommodityBo.getImgUrl1())) {
            imgUrlList.add(wayCommodityBo.getImgUrl1());
        }
        if (StringUtils.isNotBlank(wayCommodityBo.getImgUrl2())) {
            imgUrlList.add(wayCommodityBo.getImgUrl2());
        }
        if (StringUtils.isNotBlank(wayCommodityBo.getImgUrl3())) {
            imgUrlList.add(wayCommodityBo.getImgUrl3());
        }
        if (StringUtils.isNotBlank(wayCommodityBo.getImgUrl4())) {
            imgUrlList.add(wayCommodityBo.getImgUrl4());
        }
        wayCommodityBo.setImgUrlList(imgUrlList);
        return wayCommodityBo;
    }*/

    /*@Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo createCommodity(WayCommodityParam commodityParam) {
        //当前只能增加一个商品，如果已存在商品，返回当前的商品，幂等操作
        WayCommodityCondition getCommodityCondition = new WayCommodityCondition();
        getCommodityCondition.setShopId(commodityParam.getShopId());
        List<WayCommodity> existCommodityList = commodityMapper
                .selectByCondition(getCommodityCondition, WayPageRequest.of(1, 1));
        if (CollectionUtils.isNotEmpty(existCommodityList)) {
            return BeanMapper.map(existCommodityList.get(0), WayCommodityBo.class);
        }
    
        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        try {
            wayCommodityRecord.setNamePinyin(PinyinHelper
                    .convertToPinyinString(wayCommodityRecord.getName(), StringUtils.EMPTY,
                            PinyinFormat.WITHOUT_TONE));
            wayCommodityRecord.setNamePy(PinyinHelper.getShortPinyin(wayCommodityRecord.getName()));
        } catch (PinyinException e) {
        }
    
        for (int i = 0; i < commodityParam.getImgUrlList().size(); i++) {
            String imgUrl = commodityParam.getImgUrlList().get(i);
            if (i == 0) {
                wayCommodityRecord.setImgUrl(imgUrl);
            } else if (i == 1) {
                wayCommodityRecord.setImgUrl1(imgUrl);
            } else if (i == 2) {
                wayCommodityRecord.setImgUrl2(imgUrl);
            } else if (i == 3) {
                wayCommodityRecord.setImgUrl3(imgUrl);
            } else if (i == 4) {
                wayCommodityRecord.setImgUrl4(imgUrl);
            }
        }
        wayCommodityRecord.setIsDeleted(WayCommodityStatusEnum.AUDITTING.getStatus());
        commodityMapper.insertSelective(wayCommodityRecord);
        return BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
    }*/

    /*@Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo updateCommodity(WayCommodityParam commodityParam) {
    
        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        try {
            wayCommodityRecord.setNamePinyin(PinyinHelper
                    .convertToPinyinString(wayCommodityRecord.getName(), StringUtils.EMPTY,
                            PinyinFormat.WITHOUT_TONE));
            wayCommodityRecord.setNamePy(PinyinHelper.getShortPinyin(wayCommodityRecord.getName()));
        } catch (PinyinException e) {
        }
    
        wayCommodityRecord.setImgUrl1(StringUtils.EMPTY);
        wayCommodityRecord.setImgUrl2(StringUtils.EMPTY);
        wayCommodityRecord.setImgUrl3(StringUtils.EMPTY);
        wayCommodityRecord.setImgUrl4(StringUtils.EMPTY);
    
        for (int i = 0; i < commodityParam.getImgUrlList().size(); i++) {
            String imgUrl = commodityParam.getImgUrlList().get(i);
            if (i == 0) {
                wayCommodityRecord.setImgUrl(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 1) {
                wayCommodityRecord
                        .setImgUrl1(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 2) {
                wayCommodityRecord
                        .setImgUrl2(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 3) {
                wayCommodityRecord
                        .setImgUrl3(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 4) {
                wayCommodityRecord
                        .setImgUrl4(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            }
        }
        commodityMapper.updateByPrimaryKeySelective(wayCommodityRecord);
        return BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
    }*/

    /*@Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo deleteCommodity(WayCommodityParam commodityParam) {
    
        WayCommodity wayShopRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayShopRecord.setIsDeleted((byte) 1);
        commodityMapper.updateByPrimaryKeySelective(wayShopRecord);
        return BeanMapper.map(wayShopRecord, WayCommodityBo.class);
    }*/

    @Override
    public Map<String, String> getAllCommodityStatus() {

        WayCommodityStatusEnum[] commodityStatusEnums = WayCommodityStatusEnum.values();
        if (commodityStatusEnums.length < 1) {
            return MapUtils.EMPTY_SORTED_MAP;
        }

        Map<String, String> commodityStatusMap = new HashMap<>(commodityStatusEnums.length);
        for (WayCommodityStatusEnum commodityStatusEnum : commodityStatusEnums) {
            byte status = commodityStatusEnum.getValue();
            String desc = commodityStatusEnum.getDesc();
            commodityStatusMap.put(String.valueOf(status), desc);
        }

        return commodityStatusMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WayCommodityBo updateCommodityStatus(WayCommodityParam commodityParam) throws BusinessException {

        // 获取商品对象
        WayCommodity existCommodity = commodityMapper.selectByPrimaryKey(commodityParam.getId());
        if (null == existCommodity) {
            throw new BusinessException("商品不存在");
        }

        // 修改状态
        WayCommodity wayShopRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayShopRecord.setIsDeleted(commodityParam.getStatus());
        commodityMapper.updateByPrimaryKeySelective(wayShopRecord);

        // 记录日志
        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = null;
        if (WayCommodityStatusEnum.DRAFT.getValue().equals(commodityParam.getStatus())) {
            logContent = commodityParam.getRejectContent();
            commodityLogRecord.setType(WayCommodityLogTypeEnum.REJECT.getValue());
        } else {
            logContent = String.format("商品状态从[%s]修改为[%s]",
                EnumUtil.getDescByValue(existCommodity.getIsDeleted(), WayCommodityStatusEnum.class),
                EnumUtil.getDescByValue(commodityParam.getStatus(), WayCommodityStatusEnum.class));
            commodityLogRecord.setType(WayCommodityLogTypeEnum.STATUS.getValue());
        }
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayShopRecord.getId());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.MP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        return BeanMapper.map(wayShopRecord, WayCommodityBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Long queryCommodityCount(WayCommodityParam commodityParam) {

        WayCommodityCondition condition = BeanMapper.map(commodityParam, WayCommodityCondition.class);
        return commodityMapper.countByCondition(condition);
    }

    @Override
    public Long queryOnlineCount() {
        return commodityMapper.countAllOnline();
    }
}
