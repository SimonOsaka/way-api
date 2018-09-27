package com.zl.way.user.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean userTelLogin(UserLoginParam userLoginParam) {

        String userLoginTel = userLoginParam.getLoginTel();
        String userValidCode = userLoginParam.getValidCode();
        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginTel(userLoginTel);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        if (logger.isDebugEnabled()) {
            logger.debug("用户={}", userLogin);
        }
        if (null == userLogin) {
            logger.warn("用户未注册,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户未注册");
        }

        if (DateTime.now().toDate().after(userLogin.getValidCodeExpire())) {
            logger.warn("验证码过期，请重新获取,{}", JSON.toJSONString(condition));
            throw new RuntimeException("验证码过期，请重新获取");
        }

        if (StringUtils.compare(userLogin.getValidCode(), userValidCode) != 0) {
            logger.warn("验证码不正确,{}", JSON.toJSONString(condition));
            throw new RuntimeException("验证码不正确");
        }

        if (userLogin.getIsUsed() == 1) {
            logger.warn("用户被禁用,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            logger.warn("用户已被删除,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户已被删除");
        }

        Date now = new DateTime().toDate();
        UserLogin record = new UserLogin();
        record.setUpdateTime(now);
        record.setLoginTime(now);
        record.setId(userLogin.getId());
        userLoginMapper.updateByPrimaryKeySelective(record);
        logger.info("用户登录成功,{},{}", condition, record);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean userNameLogin(UserLoginParam userLoginParam) {

        String userLoginName = userLoginParam.getLoginName();
        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginName(userLoginName);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        if (logger.isDebugEnabled()) {
            logger.debug("用户={}", userLogin);
        }

        Date now = DateTime.now().toDate();

        if (null == userLogin) {
            if (StringUtils.length(userLoginName) < 4 || StringUtils.length(userLoginName) > 16) {
                throw new RuntimeException("用户名长度在4-16个字符内");
            }

            Pattern pattern = Pattern.compile("^[0-9A-Za-z]+$");
            Matcher matcher = pattern.matcher(userLoginName);
            if (!matcher.matches()) {
                throw new RuntimeException("用户名只能包含字母和数字");
            }

            if (StringUtils.length(userLoginParam.getLoginPassword()) < 6) {
                throw new RuntimeException("用户密码最少6个字符");
            }

            UserLogin userLoginRecord = new UserLogin();
            userLoginRecord.setLoginName(userLoginName);
            userLoginRecord
                    .setLoginPassword(md5UserLoginPassword(userLoginParam.getLoginPassword()));
            userLoginRecord.setLoginTime(now);
            userLoginMapper.insertSelective(userLoginRecord);

            UserProfile userProfileRecord = new UserProfile();
            userProfileRecord.setUserLoginId(userLoginRecord.getId());
            userProfileRecord.setUserNickName("w_" + genNickName(userLoginRecord.getId() + ""));
            userProfileMapper.insertSelective(userProfileRecord);
            if (logger.isDebugEnabled()) {
                logger.debug("用户创建成功{}", JSON.toJSONString(userProfileRecord));
            }
        } else {

            final String userLoginPassword = userLoginParam.getLoginPassword();
            if (!md5UserLoginPassword(userLoginPassword)
                    .equalsIgnoreCase(userLogin.getLoginPassword())) {
                logger.warn("用户密码不正确,{}", JSON.toJSONString(condition));
                throw new RuntimeException("用户密码不正确");
            }

            if (userLogin.getIsUsed() == 1) {
                logger.warn("用户被禁用,{}", JSON.toJSONString(condition));
                throw new RuntimeException("用户被禁用");
            }

            if (userLogin.getIsDeleted() == 1) {
                logger.warn("用户已被删除,{}", JSON.toJSONString(condition));
                throw new RuntimeException("用户已被删除");
            }

            UserLogin record = new UserLogin();
            record.setUpdateTime(now);
            record.setLoginTime(now);
            record.setId(userLogin.getId());
            userLoginMapper.updateByPrimaryKeySelective(record);
            logger.info("用户登录成功,{},{}", condition, record);
        }

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean userLogout(UserLoginParam userlogoutParam) {

        Long userLoginId = userlogoutParam.getId();

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setId(userLoginId);
        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);
        if (logger.isDebugEnabled()) {
            logger.debug("用户={}", userLogin);
        }
        if (null == userLogin) {
            logger.warn("用户未注册,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户未注册");
        }

        if (userLogin.getIsUsed() == 1) {
            logger.warn("用户被禁用,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            logger.warn("用户已被删除,{}", JSON.toJSONString(condition));
            throw new RuntimeException("用户已被删除");
        }

        Date now = DateTime.now().toDate();
        UserLogin record = new UserLogin();
        record.setUpdateTime(now);
        record.setLogoutTime(now);
        record.setId(userLoginId);
        userLoginMapper.updateByPrimaryKeySelective(record);
        logger.info("用户登出成功,{},{}", condition, record);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public UserProfileBo getUserById(Long userLoginId) {

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setId(userLoginId);

        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);

        return getUser(userLogin);
    }

    @Override
    public UserProfileBo getUserByTel(String userTel) {

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginTel(userTel);

        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);

        return getUser(userLogin);
    }

    @Override
    public UserProfileBo getUserByName(String userLoginName) {

        UserLoginQueryCondition condition = new UserLoginQueryCondition();
        condition.setLoginName(userLoginName);

        UserLogin userLogin = userLoginMapper.selectByPrimaryKey(condition);

        return getUser(userLogin);
    }

    private UserProfileBo getUser(UserLogin userLogin) {

        if (null == userLogin) {
            logger.warn("用户未注册,{}", JSON.toJSONString(userLogin));
            throw new RuntimeException("用户未注册");
        }

        if (userLogin.getIsUsed() == 1) {
            logger.warn("用户被禁用,{}", JSON.toJSONString(userLogin));
            throw new RuntimeException("用户被禁用");
        }

        if (userLogin.getIsDeleted() == 1) {
            logger.warn("用户已被删除,{}", JSON.toJSONString(userLogin));
            throw new RuntimeException("用户已被删除");
        }

        UserProfileQueryCondition profileQueryCondition = new UserProfileQueryCondition();
        profileQueryCondition.setUserLoginId(userLogin.getId());
        UserProfile userProfile = userProfileMapper.selectByPrimaryKey(profileQueryCondition);
        if (null == userProfile) {
            logger.warn("用户信息不存在,{}", JSON.toJSONString(profileQueryCondition));
            throw new RuntimeException("用户信息不存在");
        }

        UserProfileBo userProfileBo = BeanMapper.map(userProfile, UserProfileBo.class);
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户信息={}", userProfileBo);
        }

        return userProfileBo;
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
            if (logger.isDebugEnabled()) {
                logger.debug("登录用户创建成功{}", JSON.toJSONString(userLoginRecord));
            }

            UserProfile userProfileRecord = new UserProfile();
            userProfileRecord.setUserLoginId(userLoginRecord.getId());
            userProfileRecord.setUserNickName("w_" + genNickName(userTel));
            userProfileMapper.insertSelective(userProfileRecord);
            if (logger.isDebugEnabled()) {
                logger.debug("用户信息创建成功{}", JSON.toJSONString(userProfileRecord));
            }
        } else {
            if (userLogin.getIsUsed() == 1) {
                logger.info("用户被禁用,{}", JSON.toJSONString(condition));
                throw new RuntimeException("用户被禁用");
            }

            if (userLogin.getIsDeleted() == 1) {
                logger.info("用户已被删除,{}", JSON.toJSONString(condition));
                throw new RuntimeException("用户已被删除");
            }

            if (null == userLogin.getValidCodeExpire() || now
                    .after(userLogin.getValidCodeExpire())) {//当前时间大于过期时间，需要重新生成验证码
                validCode = String.valueOf(RandomUtils.nextInt(113494, 984920));
                UserLogin updateUserLogin = new UserLogin();
                updateUserLogin.setId(userLogin.getId());
                updateUserLogin.setValidCode(validCode);
                updateUserLogin.setValidCodeExpire(DateUtils.addMinutes(now, 5));
                updateUserLogin.setUpdateTime(now);
                userLoginMapper.updateByPrimaryKeySelective(updateUserLogin);
                if (logger.isDebugEnabled()) {
                    logger.debug("验证码生成成功={}", JSON.toJSONString(updateUserLogin));
                }
            } else {
                validCode = userLogin.getValidCode();
                if (logger.isDebugEnabled()) {
                    logger.debug("验证码未过期从DB获取={}", JSON.toJSONString(userLogin));
                }
            }

        }

        if (StringUtils.isBlank(validCode)) {
            logger.error("验证码生成失败{}", userTel);
            throw new RuntimeException("验证码生成失败");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("手机号={}，验证码={}", userTel, validCode);
        }
        return validCode;
    }

    private String genNickName(String str) {

        int[] offset = {8, 5, 2, 1, 6, 8, 7, 4, 0, 7, 5};
        char[] ca = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ca.length; i++) {
            sb.append((char) (ca[i] + 49 + offset[i]));
        }
        return sb.toString();
    }

    private String md5UserLoginPassword(String str) {

        byte[] strBytes = (str + "*jw#Ofos3O*#fh").getBytes();
        return DigestUtils.md5DigestAsHex(strBytes);
    }

}
