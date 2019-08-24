package com.xuanrui.common.constant;

import java.util.NoSuchElementException;

/**
 * @Description: 消息类型枚举
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
public enum MessageType {

    MSG_TEXT(Byte.valueOf("1")) {
        @Override
        public String getMessageType() {
            return "TIMTextElem";
        }
    }, MSG_LOCATION(Byte.valueOf("2")) {
        @Override
        public String getMessageType() {
            return "TIMLocationElem";
        }
    }, MSG_FACE(Byte.valueOf("3")) {
        @Override
        public String getMessageType() {
            return "TIMFaceElem";
        }
    }, MSG_SOUND(Byte.valueOf("4")) {
        @Override
        public String getMessageType() {
            return "TIMSoundElem";
        }
    }, MSG_IMAGE(Byte.valueOf("5")) {
        @Override
        public String getMessageType() {
            return "TIMImageElem";
        }
    }, MSG_CUSTOM(Byte.valueOf("6")) {
        @Override
        public String getMessageType() {
            return "TIMCustomElem";
        }
    }, MSG_FILE(Byte.valueOf("7")) {
        @Override
        public String getMessageType() {
            return "TIMFileElem";
        }
    }, MSG_VIDEO(Byte.valueOf("8")) {
        @Override
        public String getMessageType() {
            return "TIMVideoFileElem";
        }
    }, MSG_OTHER(Byte.valueOf("8")) {
        @Override
        public String getMessageType() {
            return "Other";
        }
    };


    MessageType(Byte key) {
        this.key = key;
    }

    private Byte key;

    public Byte getKey() {
        return this.key;
    }

    public abstract String getMessageType();

    public static MessageType getMessageType(Byte code) {
        for (MessageType messageType : MessageType.values()) {
            if (code.equals((messageType.getKey()))) {
                return messageType;
            }
        }
        throw new NoSuchElementException(code.toString());
    }

    public static MessageType getMessageType(String value) {
        for (MessageType messageType : MessageType.values()) {
            if (value.equals((messageType.getMessageType()))) {
                return messageType;
            }
        }
        throw new NoSuchElementException(value);
    }
}
