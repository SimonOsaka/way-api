package com.zl.way.sp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.zl.way.sp.enums.WayCommodityLogSourceEnum;
import com.zl.way.sp.enums.WayCommodityLogTypeEnum;
import com.zl.way.sp.enums.WayCommodityStatusEnum;
import com.zl.way.sp.mapper.WayCommodityAbstractWordMapper;
import com.zl.way.sp.mapper.WayCommodityLogMapper;
import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.mapper.WayDiscountMapper;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.EnumUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("spWayCommodityService")
public class WayCommodityServiceImpl implements WayCommodityService {

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Autowired
    private WayCommodityLogMapper commodityLogMapper;

    @Autowired
    private WayDiscountMapper discountMapper;

    @Autowired
    private WayCommodityAbstractWordMapper commodityAbstractWordMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayCommodityBo getCommodity(WayCommodityParam commodityParam) {

        Pageable pageable = WayPageRequest.of(1, 1);
        WayCommodityCondition condition = BeanMapper.map(commodityParam, WayCommodityCondition.class);
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

        if (!StringUtils.equalsIgnoreCase("[]", wayCommodityBo.getAbstractWordIds())) {
            String abstractWordIds = shopList.get(0).getAbstractWordIds();
            WayCommodityAbstractWordCondition abstractWordCondition = new WayCommodityAbstractWordCondition();

            JSONArray jsonArray = JSONArray.parseArray(abstractWordIds);
            List<Integer> ids = new ArrayList<>(jsonArray.size());
            for (Object obj : jsonArray) {
                ids.add((Integer)obj);
            }
            abstractWordCondition.setIds(ids);
            List<WayCommodityAbstractWord> fiveWordList =
                commodityAbstractWordMapper.selectByCondition(abstractWordCondition, WayPageRequest.of(1, 5));
            abstractWordCondition = new WayCommodityAbstractWordCondition();
            List<WayCommodityAbstractWord> allAbstractWordList =
                commodityAbstractWordMapper.selectByCondition(abstractWordCondition, null);
            if (CollectionUtils.isNotEmpty(fiveWordList)) {
                String[] wordStr = new String[fiveWordList.size()];
                for (int i = 0; i < fiveWordList.size(); i++) {
                    JSONObject jsonObject = JSON.parseObject(fiveWordList.get(i).getJsonData());
                    if (jsonObject.containsKey("path")) {
                        JSONArray jsonPathArray = jsonObject.getJSONArray("path");
                        List<Integer> wordPathIdList = jsonPathArray.toJavaList(Integer.class);
                        wordStr[i] =
                            getWordPathName(wordPathIdList, allAbstractWordList) + fiveWordList.get(i).getName();
                    }
                }
                wayCommodityBo.setAbstractWordNames(StringUtils.join(wordStr, ","));
            }
            wayCommodityBo.setAbstractWordIdList(ids);
        }
        return wayCommodityBo;
    }

    private String getWordPathName(List<Integer> wordPathIdList,
        List<WayCommodityAbstractWord> commodityAbstractWordList) {
        StringBuilder pathNamesSb = new StringBuilder();
        for (WayCommodityAbstractWord word : commodityAbstractWordList) {
            for (Integer pathId : wordPathIdList) {
                if (pathId.equals(word.getId())) {
                    pathNamesSb.append(word.getName()).append("/");
                }
            }
        }

        return pathNamesSb.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo createCommodity(WayCommodityParam commodityParam) {
        // 当前只能增加一个商品，如果已存在商品，返回当前的商品，幂等操作
        WayCommodityCondition getCommodityCondition = new WayCommodityCondition();
        getCommodityCondition.setShopId(commodityParam.getShopId());
        List<WayCommodity> existCommodityList =
            commodityMapper.selectByCondition(getCommodityCondition, WayPageRequest.of(1, 1));
        if (CollectionUtils.isNotEmpty(existCommodityList)) {
            return BeanMapper.map(existCommodityList.get(0), WayCommodityBo.class);
        }

        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayCommodityRecord
            .setAbstractWordIds("[" + StringUtils.join(commodityParam.getAbstractWordIdList(), ",") + "]");
        try {
            wayCommodityRecord.setNamePinyin(PinyinHelper.convertToPinyinString(wayCommodityRecord.getName(),
                StringUtils.EMPTY, PinyinFormat.WITHOUT_TONE));
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
        wayCommodityRecord.setIsDeleted(WayCommodityStatusEnum.AUDITTING.getValue());
        commodityMapper.insertSelective(wayCommodityRecord);

        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = String.format("商品被创建，状态为[%s]",
            EnumUtil.getDescByValue(wayCommodityRecord.getIsDeleted(), WayCommodityStatusEnum.class));
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayCommodityRecord.getId());
        commodityLogRecord.setType(WayCommodityLogTypeEnum.STATUS.getValue());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.SP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        return BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo updateCommodity(WayCommodityParam commodityParam) {

        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayCommodityRecord
            .setAbstractWordIds("[" + StringUtils.join(commodityParam.getAbstractWordIdList(), ",") + "]");
        try {
            wayCommodityRecord.setNamePinyin(PinyinHelper.convertToPinyinString(wayCommodityRecord.getName(),
                StringUtils.EMPTY, PinyinFormat.WITHOUT_TONE));
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
                wayCommodityRecord.setImgUrl1(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 2) {
                wayCommodityRecord.setImgUrl2(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 3) {
                wayCommodityRecord.setImgUrl3(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            } else if (i == 4) {
                wayCommodityRecord.setImgUrl4(StringUtils.defaultIfBlank(imgUrl, StringUtils.EMPTY));
            }
        }

        wayCommodityRecord.setIsDeleted(WayCommodityStatusEnum.AUDITTING.getValue());
        commodityMapper.updateByPrimaryKeySelective(wayCommodityRecord);

        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = String.format("商品被修改，状态为[%s]",
            EnumUtil.getDescByValue(wayCommodityRecord.getIsDeleted(), WayCommodityStatusEnum.class));
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayCommodityRecord.getId());
        commodityLogRecord.setType(WayCommodityLogTypeEnum.INFO.getValue());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.SP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        return BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo deleteCommodity(WayCommodityParam commodityParam) {

        WayCommodity existCommodity = commodityMapper.selectByPrimaryKey(commodityParam.getId());

        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayCommodityRecord.setIsDeleted((byte)1);
        commodityMapper.updateByPrimaryKeySelective(wayCommodityRecord);

        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = String.format("商品修改状态，状态从[%s]修改为[%s]",
            EnumUtil.getDescByValue(existCommodity.getIsDeleted(), WayCommodityStatusEnum.class),
            EnumUtil.getDescByValue(wayCommodityRecord.getIsDeleted(), WayCommodityStatusEnum.class));
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayCommodityRecord.getId());
        commodityLogRecord.setType(WayCommodityLogTypeEnum.STATUS.getValue());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.SP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        return BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo updateStatus(WayCommodityParam commodityParam) {

        WayCommodity existCommodity = commodityMapper.selectByPrimaryKey(commodityParam.getId());

        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayCommodityRecord.setIsDeleted(commodityParam.getIsDeleted());
        commodityMapper.updateByPrimaryKeySelective(wayCommodityRecord);

        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = String.format("商品修改状态，状态从[%s]修改为[%s]",
            EnumUtil.getDescByValue(existCommodity.getIsDeleted(), WayCommodityStatusEnum.class),
            EnumUtil.getDescByValue(wayCommodityRecord.getIsDeleted(), WayCommodityStatusEnum.class));
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayCommodityRecord.getId());
        commodityLogRecord.setType(WayCommodityLogTypeEnum.STATUS.getValue());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.SP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        WayCommodityBo wayCommodityBo = BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
        wayCommodityBo
            .setStatusName(EnumUtil.getDescByValue(commodityParam.getIsDeleted(), WayCommodityStatusEnum.class));
        return wayCommodityBo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public WayCommodityBo offlineCommodity(WayCommodityParam commodityParam) {

        WayCommodity existCommodity = commodityMapper.selectByPrimaryKey(commodityParam.getId());

        WayCommodity wayCommodityRecord = BeanMapper.map(commodityParam, WayCommodity.class);
        wayCommodityRecord.setIsDeleted(WayCommodityStatusEnum.PENDING.getValue());
        commodityMapper.updateByPrimaryKeySelective(wayCommodityRecord);

        // 商品下架：将关联的优惠下架
        WayDiscountCondition wayDiscountCondition = new WayDiscountCondition();
        wayDiscountCondition.setLimitTimeExpireEnable(true);
        wayDiscountCondition.setCommodityId(commodityParam.getId());
        List<WayDiscount> discountList = discountMapper.selectByCondition(wayDiscountCondition, WayPageRequest.ONE);
        StringBuilder logFormat = new StringBuilder("商品下架操作，状态从[%s]修改为[%s]");
        if (CollectionUtils.isNotEmpty(discountList)) {
            WayDiscount wayDiscount = new WayDiscount();
            wayDiscount.setId(discountList.get(0).getId());
            wayDiscount.setIsDeleted((byte)1);// 删除
            discountMapper.updateByPrimaryKeySelective(wayDiscount);
            logFormat.append("，对应优惠信息同时删除");
        }

        WayCommodityLog commodityLogRecord = new WayCommodityLog();
        String logContent = String.format(logFormat.append("。").toString(),
            EnumUtil.getDescByValue(existCommodity.getIsDeleted(), WayCommodityStatusEnum.class),
            EnumUtil.getDescByValue(wayCommodityRecord.getIsDeleted(), WayCommodityStatusEnum.class));
        commodityLogRecord.setContent(logContent);
        commodityLogRecord.setCommodityId(wayCommodityRecord.getId());
        commodityLogRecord.setType(WayCommodityLogTypeEnum.STATUS.getValue());
        commodityLogRecord.setSource(WayCommodityLogSourceEnum.SP.getValue());
        commodityLogMapper.insertSelective(commodityLogRecord);

        WayCommodityBo wayCommodityBo = BeanMapper.map(wayCommodityRecord, WayCommodityBo.class);
        wayCommodityBo.setStatusName(WayCommodityStatusEnum.PENDING.getDesc());
        return wayCommodityBo;
    }
}
