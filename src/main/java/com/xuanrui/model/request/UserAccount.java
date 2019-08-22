package com.xuanrui.model.request;
/**
 * @Description: 用户
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class UserAccount {
    /**
     * 用户名
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像 URL
     */
    private String faceUrl;

    /**
     * 性别
     */
    private Byte gender;
    /**
     * 生日 20190805
     */
    private Integer birthDay;

    /**
     * 位置
     */
    private String location;

    /**
     * 个性签名
     */
    private String selfSignature;

    /**
     * 加好友验证方式
     */
    private Byte allowType;

    /**
     * 语言
     */
    private Integer language;

    /**
     * 是否接受消息 0接受 1不接受
     */
    private Integer msgSetting;
    /**
     * 管理员禁止加好友标识
     */
    private String adminForbidType;


    /**
     * 帐号类型，开发者默认无需填写，值0表示普通帐号，1表示机器人帐号
     */
    private Integer type = 0;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSelfSignature() {
        return selfSignature;
    }

    public void setSelfSignature(String selfSignature) {
        this.selfSignature = selfSignature;
    }

    public Byte getAllowType() {
        return allowType;
    }

    public void setAllowType(Byte allowType) {
        this.allowType = allowType;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getMsgSetting() {
        return msgSetting;
    }

    public void setMsgSetting(Integer msgSetting) {
        this.msgSetting = msgSetting;
    }

    public String getAdminForbidType() {
        return adminForbidType;
    }

    public void setAdminForbidType(String adminForbidType) {
        this.adminForbidType = adminForbidType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
