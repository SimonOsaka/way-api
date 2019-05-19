package com.zl.way.push.job;

import com.zl.way.push.service.WayDiscountJpushService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PushDiscountShopFollowJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private WayDiscountJpushService discountJpushService;

    @Autowired
    public PushDiscountShopFollowJob(WayDiscountJpushService discountJpushService) {
        this.discountJpushService = discountJpushService;
    }

    @Scheduled(fixedDelay = 3600000 * 6)
    public void doJob() {
        logger.info("开始做推送任务，时间：{}", DateTime.now().toDate());
        discountJpushService.discountPush();
        logger.info("结束做推送任务，时间：{}", DateTime.now().toDate());
    }
}
