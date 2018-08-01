package com.zl.way.sp.service;

import com.zl.way.sp.exception.BusinessException;

public interface ApiValidationService {

    void validateUserShop(Long userLoginId, Long shopId) throws BusinessException;

    void validateUserCommodity(Long userLoginId, Long commodityId) throws BusinessException;
}
