package com.xuanrui.model.query;

import com.xuanrui.tencentim.model.request.Message;

/**
 * @Description: 消息
 * @Author: gdc
 * @Date: 2019-08-20 11:30
 **/
public class MessageQuery extends Message {
    private String beginTime;
    private String endTime;
    private Short multi;
    private Integer pageNum;
    private Integer pageSize;
    private short isWatched;
    private Boolean sysMessage;
    private short notReadCount;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Short getMulti() {
        return multi;
    }

    public void setMulti(Short multi) {
        this.multi = multi;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public short getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(short isWatched) {
        this.isWatched = isWatched;
    }

    @Override
    public Boolean getSysMessage() {
        return sysMessage;
    }

    @Override
    public void setSysMessage(Boolean sysMessage) {
        this.sysMessage = sysMessage;
    }

    public short getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(short notReadCount) {
        this.notReadCount = notReadCount;
    }
}
    