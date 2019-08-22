package com.xuanrui.common.constant;

import java.util.NoSuchElementException;

/**
 * @Description: 图片类型类型枚举
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
public enum ImageFormat {

    //图片格式。JPG = 1，GIF = 2，PNG = 3，BMP = 4，其他 = 255
    JPG(Byte.valueOf("1")) {
        @Override
        public String getImageFormat() {
            return "JPG";
        }
    },
    GIF(Byte.valueOf("2")) {
        @Override
        public String getImageFormat() {
            return "GIF";
        }
    },
    PNG(Byte.valueOf("3")) {
        @Override
        public String getImageFormat() {
            return "PNG";
        }
    },
    BMP(Byte.valueOf("4")) {
        @Override
        public String getImageFormat() {
            return "BMP";
        }
    },
    OTHER(Byte.valueOf("5")) {
        @Override
        public String getImageFormat() {
            return "其他";
        }
    };

    ImageFormat(Byte key) {
        this.key = key;
    }

    private Byte key;

    public Byte getKey() {
        return this.key;
    }

    public abstract String getImageFormat();

    public static ImageFormat getImageFormat(Byte code) {
        for (ImageFormat imageFormat : ImageFormat.values()) {
            if (code.equals((imageFormat.getKey()))) {
                return imageFormat;
            }
        }
        throw new NoSuchElementException(code.toString());
    }

    public static ImageFormat getImageFormat(String value) {
        for (ImageFormat imageFormat : ImageFormat.values()) {
            if (value.equals((imageFormat.getImageFormat()))) {
                return imageFormat;
            }
        }
        throw new NoSuchElementException(value);
    }
}
