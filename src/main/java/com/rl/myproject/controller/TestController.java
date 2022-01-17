package com.rl.myproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.rl.myproject.util.http.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rl.spring_security.enity.sms.SmsContent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author renlei
 * @date 2022/1/11 21:45
 * @description: 专门测试各种工具运用
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/sendSmsCode")
    public String sendSmsCode() {
        String serviceUrl = "http://localhost:8085/security/getSmsCode";
        Map<String, Object> message = new HashMap<>();
        message.put("mobiles","15083518460");
        message.put("content","短信正文");
        JSONObject smsContent = buildSmsContent(message);
        System.out.println(smsContent.toString());
        System.out.println(smsContent.toJSONString());
//        String str = "{\"mobiles\":\"15083518460\",\"content\":\"短信正文\",\"scheduleTime\":\"2017-8-15 08:09:22\",\"expireTime\":\"\",\"reSend\":true, \"sendnums\":1}";
        String respString = HttpClientUtil.post(serviceUrl, null, smsContent);
        System.out.println(respString);
        return respString;
    }

    private JSONObject buildSmsContent(Map<String, Object> message) {
        JSONObject smsContent = new JSONObject();
        smsContent.put("mobiles",message.get("mobiles").toString());
        smsContent.put("content",message.get("content").toString());
        smsContent.put("scheduleTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        smsContent.put("expireTime","");
        smsContent.put("reSend",true);
        smsContent.put("sendnums",1);
        return smsContent;
    }
}
