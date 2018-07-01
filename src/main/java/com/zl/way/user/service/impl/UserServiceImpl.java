package com.zl.way.user.service.impl;

import com.zl.way.user.mapper.UserLoginMapper;
import com.zl.way.user.mapper.UserProfileMapper;
import com.zl.way.user.model.*;
import com.zl.way.user.service.UserService;
import com.zl.way.util.BeanMapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean userLogin(UserLoginParam userLoginParam) {

        String userLoginTel = userLoginParam.getLoginTel();
        String userValidCode = userLoginParam.getValidCode();
        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginTel(userLoginTel);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        if (null == userLogin) {
            throw new RuntimeException("用户未注册");
        }

        if (DateTime.now().toDate().after(userLogin.getValidCodeExpire())) {
            throw new RuntimeException("验证码过期，请重新获取");
        }

        if (StringUtils.compare(userLogin.getValidCode(), userValidCode) != 0) {
            throw new RuntimeException("验证码不正确");
        }


        if (userLogin.getIsUsed() == 1) {
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除");
        }

        Date now = new DateTime().toDate();
        UserLogin record = new UserLogin();
        record.setUpdateTime(now);
        record.setLoginTime(now);
        record.setId(userLogin.getId());
        userLoginMapper.updateByPrimaryKeySelective(record);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean userLogout(UserLoginParam userlogoutParam) {

        Long userLoginId = userlogoutParam.getId();

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setId(userLoginId);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        if (null == userLogin) {
            throw new RuntimeException("用户未注册");
        }

        if (userLogin.getIsUsed() == 1) {
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除");
        }

        Date now = new DateTime().toDate();
        UserLogin record = new UserLogin();
        record.setUpdateTime(now);
        record.setLogoutTime(now);
        record.setId(userLoginId);
        userLoginMapper.updateByPrimaryKeySelective(record);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public UserProfileBo getUser(Long userLoginId) {

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setId(userLoginId);

        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);

        return getUser(userLogin);
    }

    @Override
    public UserProfileBo getUser(String userTel) {

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginTel(userTel);

        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);

        return getUser(userLogin);
    }

    private UserProfileBo getUser(UserLogin userLogin) {
        if (null == userLogin) {
            throw new RuntimeException("用户未注册");
        }

        if (userLogin.getIsUsed() == 1) {
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除");
        }

        UserProfileQueryCondition profileQueryCondition = new UserProfileQueryCondition();
        profileQueryCondition.setUserLoginId(userLogin.getId());
        UserProfile userProfile = userProfileMapper.selectByPrimaryKey(profileQueryCondition);
        if (null == userProfile) {
            throw new RuntimeException("用户信息不存在");
        }

        return BeanMapper.map(userProfile, UserProfileBo.class);
    }

    @Override
    public String getUserValidCode(String userTel) {
        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginTel(userTel);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        Date now = new DateTime().toDate();

        String validCode;
        if (null == userLogin) {
            UserLogin userLoginRecord = new UserLogin();
            userLoginRecord.setLoginTel(userTel);
            validCode = String.valueOf(RandomUtils.nextInt(113494, 984920));
            userLoginRecord.setValidCode(validCode);
            userLoginRecord.setValidCodeExpire(DateUtils.addMinutes(now, 5));
            userLoginRecord.setUpdateTime(now);
            userLoginMapper.insertSelective(userLoginRecord);

            UserProfile userProfileRecord = new UserProfile();
            userProfileRecord.setUserLoginId(userLoginRecord.getId());
            userProfileRecord.setUserNickName("w_" + genNickName(userTel));
            userProfileMapper.insertSelective(userProfileRecord);
        } else {
            if (userLogin.getIsUsed() == 1) {
                throw new RuntimeException("用户被禁用");
            }

            if (userLogin.getIsDeleted() == 1) {
                throw new RuntimeException("用户已被删除");
            }

            if (null == userLogin.getValidCodeExpire() || now.after(userLogin.getValidCodeExpire())) {//当前时间大于过期时间，需要重新生成验证码
                validCode = String.valueOf(RandomUtils.nextInt(113494, 984920));
                UserLogin updateUserLogin = new UserLogin();
                updateUserLogin.setId(userLogin.getId());
                updateUserLogin.setValidCode(validCode);
                updateUserLogin.setValidCodeExpire(DateUtils.addMinutes(now, 5));
                updateUserLogin.setUpdateTime(now);
                userLoginMapper.updateByPrimaryKeySelective(updateUserLogin);
            } else {
                validCode = userLogin.getValidCode();
            }

        }

        if (StringUtils.isBlank(validCode)) {
            throw new RuntimeException("验证码生成失败");
        }

        LOGGER.debug("生成的验证码{}", validCode);
        return validCode;
    }

    private String genNickName(String userTel) {
        int[] offset = {8, 5, 2, 1, 6, 8, 7, 4, 0, 7, 5};
        char[] ca = userTel.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ca.length; i++) {
            sb.append((char) (ca[i] + 49 + offset[i]));
        }
        return sb.toString();
    }

}
