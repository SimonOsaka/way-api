package com.zl.way.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.user.model.UserFeedback;
import com.zl.way.user.model.UserFeedbackCondition;

@Repository
public interface UserFeedbackMapper {

    int insertSelective(UserFeedback record);

    UserFeedback selectByPrimaryKey(@Param("id") Long id, @Param("userLoginId") Long userLoginId);

    List<UserFeedback> selectByCondition(@Param("condition") UserFeedbackCondition condition,
        @Param("pageable") Pageable pageable);
}