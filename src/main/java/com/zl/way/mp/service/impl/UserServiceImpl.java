package com.zl.way.mp.service.impl;

import com.zl.way.mp.mapper.UserLoginMapper;
import com.zl.way.mp.model.UserLogin;
import com.zl.way.mp.model.UserLoginBo;
import com.zl.way.mp.model.UserLoginCondition;
import com.zl.way.mp.model.UserLoginParam;
import com.zl.way.mp.service.UserService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("mpUserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public List<UserLoginBo> queryUserLoginList(UserLoginParam param, PageParam pageParam) {

        UserLoginCondition condition = BeanMapper.map(param, UserLoginCondition.class);
        Pageable pageable = WayPageRequest.of(pageParam);

        List<UserLogin> userLoginList = userLoginMapper.selectByCondition(condition, pageable);
        if (CollectionUtils.isEmpty(userLoginList)) {
            return Collections.emptyList();
        }

        return BeanMapper.mapAsList(userLoginList, UserLoginBo.class);
    }

    @Override
    public Long queryUserLoginCount(UserLoginParam param) {

        UserLoginCondition condition = BeanMapper.map(param, UserLoginCondition.class);
        return userLoginMapper.countByCondition(condition);
    }

    @Override
    public UserLoginBo disableUserLogin(UserLoginParam param) {

        UserLogin record = new UserLogin();
        record.setId(param.getId());
        record.setIsUsed((byte) 1);
        userLoginMapper.updateByPrimaryKeySelective(record);
        return BeanMapper.map(record, UserLoginBo.class);
    }
}
