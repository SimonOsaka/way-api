package com.zl.way.push.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.zl.way.push.mapper.WayDiscountJpushMapper;
import com.zl.way.push.model.WayDiscountJpush;
import com.zl.way.push.model.WayDiscountJpushBo;
import com.zl.way.push.model.WayDiscountJpushCondition;
import com.zl.way.push.service.WayDiscountJpushService;
import com.zl.way.util.WayPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzhongliang
 */
@Service public class WayDiscountJpushServiceImpl implements WayDiscountJpushService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jpush.jicu.appKey}") private String appKey;
    @Value("${jpush.jicu.masterSecret}") private String masterSecret;
    /**
     * 0.016612≈1500m
     */
    private static final BigDecimal RADIUS = BigDecimal.valueOf(0.016612);
    private static final int PAGE_SIZE_DISCOUNT = 20;
    private static final int PAGE_SIZE_DEVICE = 200;
    private final WayDiscountJpushMapper discountJpushMapper;

    @Autowired public WayDiscountJpushServiceImpl(WayDiscountJpushMapper wayDiscountJpushMapper) {
        this.discountJpushMapper = wayDiscountJpushMapper;
    }

    @Override @Transactional(rollbackFor = Exception.class) public void discountPush() {

        List<WayDiscountJpushBo> discountJpushList = null;
        WayDiscountJpushCondition condition = new WayDiscountJpushCondition();
        condition.setStartTime(DateUtils.addHours(DateTime.now().toDate(), -6));
        condition.setEndTime(DateTime.now().toDate());
        condition.setRadius(RADIUS);
        do {
            discountJpushList =
                discountJpushMapper.selectByCondition(condition, WayPageRequest.of(1, PAGE_SIZE_DISCOUNT));
            if (CollectionUtils.isEmpty(discountJpushList)) {
                break;
            }

            for (WayDiscountJpushBo discountJpushBo : discountJpushList) {
                push(discountJpushBo);
            }

        } while (!discountJpushList.isEmpty());
    }

    private void push(WayDiscountJpushBo discountJpushBo) {
        //根据shopLng、shopLat查出直径三公里内的用户设备经纬度
        Long jpushId = discountJpushBo.getId();
        String commodityName = discountJpushBo.getCommodityName();
        BigDecimal commodityPrice = discountJpushBo.getCommodityPrice();
        String deviceJpushRegId = discountJpushBo.getJpushRegId();

        // 推送符合地理范围的用户
        String alert = String.format("%s ¥%s", commodityName, new DecimalFormat("#.##").format(commodityPrice));
        jpush(deviceJpushRegId, alert, jpushId);
    }

    private void jpush(String registrationId, String alert, Long jpushId) {
        List<String> registrationIdList = new ArrayList<>();
        registrationIdList.add(registrationId);
        jpush(registrationIdList, alert, jpushId);
    }

    private void jpush(List<String> registrationIdList, String alert, Long jpushId) {
        int retry = 0;
        do {
            logger.info("执行第{}次jpush推送，id列表：{}，推送内容：{}，jpushId: {}", retry + 1, registrationIdList, alert, jpushId);
            ClientConfig clientConfig = ClientConfig.getInstance();
            final JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
            final PushPayload payload = buildPushObjectAndroid(registrationIdList, alert);
            try {
                PushResult result = jpushClient.sendPush(payload);
                logger.info("Got result - {}", result);
                updateHasPushed(jpushId);
                Thread.sleep(1000);
                break;
            } catch (APIConnectionException e) {
                logger.error("Connection error. Should retry later. ", e);
                logger.error("Sendno: {}", payload.getSendno());
                retry++;
            } catch (APIRequestException e) {
                logger.error("Error response from JPush server. Should review and fix it. ", e);
                logger.info("HTTP Status: {}", e.getStatus());
                logger.info("Error Code: {}", e.getErrorCode());
                logger.info("Error Message: {}", e.getErrorMessage());
                logger.info("Msg ID: {}", e.getMsgId());
                logger.error("Sendno: {}", payload.getSendno());
                retry++;
            } catch (InterruptedException e) {
                logger.error("Thread sleep error. ", e);
                retry++;
                Thread.currentThread().interrupt();
            }
        } while (retry < 3);

        if (retry == 3) {
            logger.warn("重试3次，失败，jpushId：{}", jpushId);
            updateHasPushed(jpushId);
        }
    }

    private PushPayload buildPushObjectAndroid(List<String> jpushRegIdList, String alertContent) {
        return PushPayload.newBuilder().setPlatform(Platform.android())
            .setAudience(Audience.registrationId(jpushRegIdList)).setNotification(
                Notification.newBuilder().setAlert(alertContent)
                    .addPlatformNotification(AndroidNotification.newBuilder().setTitle("有新优惠商品出现").build()).build())
            .build();
    }

    private void updateHasPushed(Long jpushId) {
        WayDiscountJpush discountJpush = new WayDiscountJpush();
        discountJpush.setId(jpushId);
        discountJpush.setHasPushed((byte)1);
        discountJpush.setPushedTime(DateTime.now().toDate());
        discountJpushMapper.updateByPrimaryKeySelective(discountJpush);
    }

}
