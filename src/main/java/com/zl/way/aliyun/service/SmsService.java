package com.zl.way.aliyun.service;

import com.zl.way.aliyun.model.SmsSendReq;
import com.zl.way.aliyun.model.SmsSendResp;

public interface SmsService {

    /**
     * 短信发送，运营商阿里云
     * TODO 短信签名必须要有公司才能申请成功，所以短信发送功能暂时无法启用
     * @param smsSendReq
     * @return
     */
    SmsSendResp sendMessage(SmsSendReq smsSendReq);
}
