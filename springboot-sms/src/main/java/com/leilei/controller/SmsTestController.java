package com.leilei.controller;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.leilei.config.SmsBean;
import com.leilei.support.SendSms;
import com.leilei.support.VerCodeGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Author: lei-p
 * Date: 2020/4/6 17:43
 * desc: 短信发送测试控制器
 */
@RestController
@RequestMapping("/sms")
public class SmsTestController {
    @Autowired
    private SendSms sms;

    @RequestMapping("/send")
    public HashMap<String,Object> sendSms(String phones){
        String code = VerCodeGenerateUtil.generateVerCode();
        SendSmsResponse commonResponse = sms.sendSms(phones, code);
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", commonResponse.getMessage());
        map.put("code", commonResponse.getCode());
        map.put("bizId", commonResponse.getBizId());
        return map;
    }
}
