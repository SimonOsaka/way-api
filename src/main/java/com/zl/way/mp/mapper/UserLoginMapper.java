package com.zl.way.mp.mapper;

import com.zl.way.mp.model.UserLogin;
import org.springframework.stereotype.Repository;

@Repository("mpUserLoginMapper")
public interface UserLoginMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}