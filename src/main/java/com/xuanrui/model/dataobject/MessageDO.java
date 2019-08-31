package com.xuanrui.model.dataobject;

/**
 * @Description: 入库消息
 * @Author: gdc
 * @Date: 2019-08-21 15:14
 **/
public class MessageDO {

    /**
     * 聊天记录主键
     */
    private Long messageId;
    /**
     * 0：点对点个人消息，1：群消息（高级群）
     */
    private Byte ope;
    /**
     * 消息类型
     */
    private Byte type;
    /**
     * 文本消息
     */
    private String text;

    /**
     * 图片或者视频或者语音资源地址
     */
    private String resourceUrl;
    /**
     * 图片高度
     */
    private Double w;
    /**
     * 图片宽度
     */
    private Double h;
    /**
     * 图片大小
     */
    private Double size;
    /**
     * 文件后缀
     */
    private String ext;
    /**
     * 文件md5
     */
    private String md5;
    /**
     * 持续时间
     */
    private Double dur;
    /**
     * 地理位置标题 或者 主题表情描述
     */
    private String title;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 消息发送方
     */
    private String messageFrom;

    /**
     * 消息接收方
     */
    private String messageTo;
    /**
     * 自定义消息
     */
    private String custom;
    /**
     * 表情索引
     */
    private Integer faceIndex;
    /**
     * 文件序列号。后台用于索引语音的键值
     */
    private String uuid;

    /**
     * 创建者
     */
    private String gmtCreator;
    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 系统通知
     *
     * @return
     */
    private Byte isWatched;

    public Byte getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Byte isWatched) {
        this.isWatched = isWatched;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Byte getOpe() {
        return ope;
    }

    public void setOpe(Byte ope) {
        this.ope = ope;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Double getDur() {
        return dur;
    }

    public void setDur(Double dur) {
        this.dur = dur;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public Integer getFaceIndex() {
        return faceIndex;
    }

    public void setFaceIndex(Integer faceIndex) {
        this.faceIndex = faceIndex;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGmtCreator() {
        return gmtCreator;
    }

    public void setGmtCreator(String gmtCreator) {
        this.gmtCreator = gmtCreator;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "MessageDO{" + "messageId=" + messageId + ", ope=" + ope + ", type=" + type + ", text='" + text + '\'' + ", resourceUrl='" + resourceUrl + '\'' + ", w=" + w + ", h=" + h + ", size=" + size + ", ext='" + ext + '\'' + ", md5='" + md5 + '\'' + ", dur=" + dur + ", title='" + title + '\'' + ", lng=" + lng + ", lat=" + lat + ", messageFrom='" + messageFrom + '\'' + ", messageTo='" + messageTo + '\'' + ", custom='" + custom + '\'' + ", faceIndex=" + faceIndex + ", uuid='" + uuid + '\'' + ", gmtCreator='" + gmtCreator + '\'' + ", gmtCreate='" + gmtCreate + '\'' + ", isWatched=" + isWatched + '}';
    }
}
    