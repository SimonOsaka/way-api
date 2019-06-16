package com.zl.way.push.job;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zl.way.push.service.WayDiscountJpushService;

@Component
public class PushDiscountShopFollowJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private WayDiscountJpushService discountJpushService;

    @Autowired
    public PushDiscountShopFollowJob(WayDiscountJpushService discountJpushService) {
        this.discountJpushService = discountJpushService;
    }

    @Scheduled(cron = "0 30 10,16,19 * * ?")
    public void doJob() {
        logger.info("开始做推送任务，时间：{}", DateTime.now().toDate());
        discountJpushService.discountPush();
        logger.info("结束做推送任务，时间：{}", DateTime.now().toDate());
    }
}
