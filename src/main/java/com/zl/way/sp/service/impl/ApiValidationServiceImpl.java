package com.zl.way.sp.service.impl;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.mapper.SpUserShopMapper;
import com.zl.way.sp.mapper.WayCommodityMapper;
import com.zl.way.sp.mapper.WayShopMapper;
import com.zl.way.sp.model.SpUserShop;
import com.zl.way.sp.model.SpUserShopCondition;
import com.zl.way.sp.model.WayCommodity;
import com.zl.way.sp.model.WayCommodityCondition;
import com.zl.way.sp.service.ApiValidationService;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spApiValidationServiceImpl")
public class ApiValidationServiceImpl implements ApiValidationService {

    @Autowired
    private WayShopMapper shopMapper;

    @Autowired
    private WayCommodityMapper commodityMapper;

    @Autowired
    private SpUserShopMapper spUserShopMapper;

    @Override
    public void validateUserShop(Long userLoginId, Long shopId) throws BusinessException {

        SpUserShopCondition condition = new SpUserShopCondition();
        condition.setUserLoginId(userLoginId);
        SpUserShop spUserShop = spUserShopMapper.selectByCondition(condition);
        if (null == spUserShop) {
            throw new BusinessException("商户信息不存在");
        }

        if (!shopId.equals(spUserShop.getShopId())) {
            throw new BusinessException("商户信息不存在");
        }
    }

    @Override
    public void validateUserCommodity(Long userLoginId, Long commodityId) throws BusinessException {

        WayCommodityCondition commodityCondition = new WayCommodityCondition();
        commodityCondition.setId(commodityId);
        List<WayCommodity> commodityList = commodityMapper.selectByCondition(commodityCondition, WayPageRequest.ONE);

        if (CollectionUtils.isEmpty(commodityList)) {
            throw new BusinessException("商品信息不存在");
        }

        SpUserShopCondition spUserShopCondition = new SpUserShopCondition();
        spUserShopCondition.setUserLoginId(userLoginId);
        SpUserShop spUserShop = spUserShopMapper.selectByCondition(spUserShopCondition);
        if (null == spUserShop || null == spUserShop.getShopId()) {
            throw new BusinessException("商户信息不存在");
        }

        WayCommodity wayCommodity = commodityList.get(0);
        Long commodityShopId = wayCommodity.getShopId();
        Long spShopId = spUserShop.getShopId();
        if (!spShopId.equals(commodityShopId)) {
            throw new BusinessException("商户信息不匹配");
        }

    }
}
