package com.rl.spring_security.enity.sms;

/**
 * @author renlei
 * @date 2022/1/11 22:24
 * @description: 消息格式
 */
public class SmsContent {
    private String mobiles;
    private String content;
    private String scheduleTime;
    private String expireTime;
    private boolean reSend;
    private Integer sendnums;

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isReSend() {
        return reSend;
    }

    public void setReSend(boolean reSend) {
        this.reSend = reSend;
    }

    public Integer getSendnums() {
        return sendnums;
    }

    public void setSendnums(Integer sendnums) {
        this.sendnums = sendnums;
    }
}
