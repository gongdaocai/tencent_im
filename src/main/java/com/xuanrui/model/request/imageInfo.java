package com.xuanrui.model.request;
/**
 * @Description: 图片详细信息
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class imageInfo {
    /**
     * 图片类型： 1-原图，2-大图，3-缩略图
     */
    private Integer type;
    /**
     * 大小 字节
     */
    private Long size;
    /**
     * 宽
     */
    private Long width;
    /**
     * 高
     */
    private Long height;
    /**
     * 图片下载地址
     */
    private String url;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}