package com.xuanrui.common.constant;

import java.util.NoSuchElementException;
/**
 * @Description: 性别枚举
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
public enum Gender {

    GENDER_UNKNOWN(Byte.valueOf("0")) {
        @Override
        public String getGender() {
            return "Gender_Type_Unknown";
        }
    }, GENDER_MALE(Byte.valueOf("1")) {
        @Override
        public String getGender() {
            return "Gender_Type_Male";
        }
    }, GENDER_FEMALE(Byte.valueOf("2")) {
        @Override
        public String getGender() {
            return "Gender_Type_Female";
        }
    };

    Gender(Byte key) {
        this.key = key;
    }

    private Byte key;

    public Byte getKey() {
        return this.key;
    }

    public abstract String getGender();

    public static Gender getGender(Byte code) {
        for (Gender gender : Gender.values()) {
            if (code.equals((gender.getKey()))) {
                return gender;
            }
        }
        throw new NoSuchElementException(code.toString());
    }

    public static Gender getGender(String value) {
        for (Gender gender : Gender.values()) {
            if (value.equals((gender.getGender()))) {
                return gender;
            }
        }
        throw new NoSuchElementException(value.toString());
    }
}
