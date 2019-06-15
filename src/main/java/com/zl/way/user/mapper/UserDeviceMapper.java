package com.zl.way.user.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.user.model.UserDevice;

@Repository
public interface UserDeviceMapper {

    int deleteByPrimaryKey(Long userLoginId);

    int insert(UserDevice record);

    int insertSelective(UserDevice record);

    UserDevice selectByPrimaryKey(Long userLoginId);

    int updateByPrimaryKeySelective(UserDevice record);

    int updateByPrimaryKey(UserDevice record);
}