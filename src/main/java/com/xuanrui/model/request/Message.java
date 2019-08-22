package com.xuanrui.model.request;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @Description: 消息
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class Message {

    private Long messageId;

    private Byte messageMultiType;
    /**
     * 消息同步至发送方 1是 2否 若不填写默认情况下会将消息存 From_Account 漫游
     */
    private Byte syncOtherMachine;
    /**
     * 历史消息导入必填该字段 只能填1或2，其他值是非法值
     * 1表示实时消息导入，消息加入未读计数
     * 2表示历史消息导入，消息不计入未读
     */
    private Byte syncFromOldSystem;

    /**
     * 发送人
     */
    @NotEmpty(message = "发送人为空")
    private String messageFrom;
    /**
     * 接收人
     */
    @NotEmpty(message = "接收人为空")
    private String messageTo;
    /**
     * 随机数
     */
    private Integer msgRandom;
    /**
     * 消息时间
     */
    private Long msgTimeStamp;
    /**
     * 消息内容 同时发送不同类型时传入
     */
    private List<Message> msgList;


    /**
     * 消息类型
     */
    private Byte type;

    /**
     * 纯文本消息内容
     */
    private String text;

    /**
     * 地理位置描述
     */
    private String locationDesc;
    /**
     * 地理位置纬度
     */
    private Double latitude;
    /**
     * 地理位置经度
     */
    private Double longitude;

    /**
     * 表情消息索引
     */
    private Integer faceIndex;
    /**
     * 表情消息描述
     */
    private String faceData;

    //自定义消息
    /**
     * 自定义消息数据。 不作为 APNs 的 payload 字段下发，故从 payload 中无法获取 Data 字段
     */
    private String custom;
    /**
     * 自定义消息描述信息；当接收方为 iOS 或 Android 后台在线时，做离线推送文本展示
     */
    private String desc;
    /**
     * 扩展字段；当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。
     */
    private String ext;
    /**
     * 自定义 APNs 推送铃音。
     */
    private String sound;

    /**
     * 系统消息
     */
    private Boolean sysMessage;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Byte getMessageMultiType() {
        return messageMultiType;
    }

    public void setMessageMultiType(Byte messageMultiType) {
        this.messageMultiType = messageMultiType;
    }

    public Byte getSyncOtherMachine() {
        return syncOtherMachine;
    }

    public void setSyncOtherMachine(Byte syncOtherMachine) {
        this.syncOtherMachine = syncOtherMachine;
    }

    public Byte getSyncFromOldSystem() {
        return syncFromOldSystem;
    }

    public void setSyncFromOldSystem(Byte syncFromOldSystem) {
        this.syncFromOldSystem = syncFromOldSystem;
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

    public Integer getMsgRandom() {
        return msgRandom;
    }

    public void setMsgRandom(Integer msgRandom) {
        this.msgRandom = msgRandom;
    }

    public Long getMsgTimeStamp() {
        return msgTimeStamp;
    }

    public void setMsgTimeStamp(Long msgTimeStamp) {
        this.msgTimeStamp = msgTimeStamp;
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
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

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getFaceIndex() {
        return faceIndex;
    }

    public void setFaceIndex(Integer faceIndex) {
        this.faceIndex = faceIndex;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Boolean getSysMessage() {
        return sysMessage;
    }

    public void setSysMessage(Boolean sysMessage) {
        this.sysMessage = sysMessage;
    }
}
