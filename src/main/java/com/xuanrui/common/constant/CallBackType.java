package com.xuanrui.common.constant;

import java.util.NoSuchElementException;

/**
 * @Description: 消息类型枚举
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
public enum CallBackType {

    C2C_CallbackAfterSendMsg(Byte.valueOf("1")) {
        @Override
        public String getCallBackType() {
            return "C2C.CallbackAfterSendMsg";
        }
    }, Group_CallbackBeforeSendMsg(Byte.valueOf("2")) {
        @Override
        public String getCallBackType() {
            return "Group.CallbackBeforeSendMsg";
        }
    };

    CallBackType(Byte key) {
        this.key = key;
    }

    private Byte key;

    public Byte getKey() {
        return this.key;
    }

    public abstract String getCallBackType();

    public static CallBackType getCallBackType(Byte code) {
        for (CallBackType callBackType : CallBackType.values()) {
            if (code.equals((callBackType.getKey()))) {
                return callBackType;
            }
        }
        throw new NoSuchElementException(code.toString());
    }

    public static CallBackType getCallBackType(String value) {
        for (CallBackType messageType : CallBackType.values()) {
            if (value.equals((messageType.getCallBackType()))) {
                return messageType;
            }
        }
        throw new NoSuchElementException(value);
    }
}
