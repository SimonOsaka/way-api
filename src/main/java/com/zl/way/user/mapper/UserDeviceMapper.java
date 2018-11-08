package com.zl.way.user.mapper;

import com.zl.way.user.model.UserDevice;

public interface UserDeviceMapper {

    int deleteByPrimaryKey(Long userLoginId);

    int insert(UserDevice record);

    int insertSelective(UserDevice record);

    UserDevice selectByPrimaryKey(Long userLoginId);

    int updateByPrimaryKeySelective(UserDevice record);

    int updateByPrimaryKey(UserDevice record);
}