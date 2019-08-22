package com.xuanrui.common.constant;

import java.util.NoSuchElementException;

/**
 * @Description: 加好友方式枚举
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
public enum AllowType {

    NEED_CONFIRM(Byte.valueOf("1")) {
        @Override
        public String getAllowType() {
            return "AllowType_Type_NeedConfirm";
        }
    }, ALLOW_ANY(Byte.valueOf("2")) {
        @Override
        public String getAllowType() {
            return "AllowType_Type_AllowAny";
        }
    }, DENY_ANY(Byte.valueOf("3")) {
        @Override
        public String getAllowType() {
            return "AllowType_Type_DenyAny";
        }
    };

    AllowType(Byte key) {
        this.key = key;
    }

    private Byte key;

    public Byte getKey() {
        return this.key;
    }

    public abstract String getAllowType();

    public static AllowType getAllowType(Byte code) {
        for (AllowType allowType : AllowType.values()) {
            if (code.equals((allowType.getKey()))) {
                return allowType;
            }
        }
        throw new NoSuchElementException(code.toString());
    }
    public static AllowType getAllowType(String value) {
        for (AllowType allowType : AllowType.values()) {
            if (value.equals((allowType.getAllowType()))) {
                return allowType;
            }
        }
        throw new NoSuchElementException(value.toString());
    }
}
