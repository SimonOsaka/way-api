package com.zl.way.mp.mapper;

import com.zl.way.mp.model.UserLogin;
import com.zl.way.mp.model.UserLoginCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpUserLoginMapper")
public interface UserLoginMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);

    List<UserLogin> selectByCondition(@Param("condition") UserLoginCondition condition,
            @Param("pageable") Pageable pageable);

    Long countByCondition(@Param("condition") UserLoginCondition condition);
}