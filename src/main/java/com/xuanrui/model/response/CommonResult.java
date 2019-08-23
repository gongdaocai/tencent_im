package com.xuanrui.model.response;

import java.util.List;

/**
 * @Description: Rest请求返回
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class CommonResult {
    private static final String FAIL = "FAIL";
    private static final String OK = "OK";
    /**
     * 请求处理的结果，OK 表示处理成功，FAIL 表示失败
     */
    private String ActionStatus;
    /**
     * 错误码，0表示成功，非0表示失败
     */
    private Integer ErrorCode;
    /**
     * 错误信息
     */
    private String ErrorInfo;

    private String ErrorDisplay;


    private List<String> FailAccounts;

    public List<String> getFailAccounts() {
        return FailAccounts;
    }

    public void setFailAccounts(List<String> failAccounts) {
        FailAccounts = failAccounts;
    }

    public CommonResult() {
    }

    public CommonResult(String actionStatus, Integer errorCode, String errorInfo) {
        ActionStatus = actionStatus;
        ErrorCode = errorCode;
        ErrorInfo = errorInfo;
    }

    public static CommonResult createSuccess() {
        return new CommonResult("OK", 0, "");
    }

    public static CommonResult createFailed() {
        return new CommonResult("FAIL", 1, "FAIL");
    }

    public static CommonResult createFailed(String errorInfo) {
        return new CommonResult("FAIL", 1, errorInfo);
    }

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }

    public String getErrorDisplay() {
        return ErrorDisplay;
    }

    public void setErrorDisplay(String errorDisplay) {
        ErrorDisplay = errorDisplay;
    }

    public Boolean isSuccess() {
        if (OK.equals(this.getActionStatus()) && 0 == this.getErrorCode()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "CommonResult{" + "ActionStatus='" + ActionStatus + '\'' + ", ErrorCode=" + ErrorCode + ", ErrorInfo='" + ErrorInfo + '\'' + '}';
    }
}
