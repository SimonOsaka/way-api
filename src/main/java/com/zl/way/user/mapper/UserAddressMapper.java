package com.zl.way.user.mapper;

import com.zl.way.user.model.UserAddress;
import com.zl.way.user.model.UserAddressQueryCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository public interface UserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddress record);

    List<UserAddress> selectByCondition(@Param("condition") UserAddressQueryCondition condition);
}