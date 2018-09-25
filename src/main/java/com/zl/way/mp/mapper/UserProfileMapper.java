package com.zl.way.mp.mapper;

import com.zl.way.mp.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository("mpUserProfileMapper")
public interface UserProfileMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserProfile record);

    int insertSelective(UserProfile record);

    UserProfile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserProfile record);

    int updateByPrimaryKey(UserProfile record);
}