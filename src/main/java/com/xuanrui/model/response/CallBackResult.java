package com.xuanrui.model.response;

/**
 * @Description: 回调
 * @Author: gdc
 * @Date: 2019-08-21 14:11
 **/
public class CallBackResult {
    /**
     * 控制台分配的 SDKAppID
     */
    private String sdkAppId;
    /**
     * 回调命令 例如 C2C.CallbackAfterSendMsg
     */
    private String callBackCommand;
    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 客户端平台
     */
    private String optPlatform;


    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public String getCallBackCommand() {
        return callBackCommand;
    }

    public void setCallBackCommand(String callBackCommand) {
        this.callBackCommand = callBackCommand;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getOptPlatform() {
        return optPlatform;
    }

    public void setOptPlatform(String optPlatform) {
        this.optPlatform = optPlatform;
    }
}
    