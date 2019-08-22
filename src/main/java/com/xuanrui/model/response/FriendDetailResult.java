package com.xuanrui.model.response;

import java.util.List;

/**
 * @Description: 好友资料
 * @Author: gdc
 * @Date: 2019-08-19 17:26
 **/
public class FriendDetailResult extends CommonResult {

    private List<InfoItem> InfoItem;

    public List<FriendDetailResult.InfoItem> getInfoItem() {
        return InfoItem;
    }

    public void setInfoItem(List<FriendDetailResult.InfoItem> infoItem) {
        InfoItem = infoItem;
    }

    public static class InfoItem {
        private String To_Account;
        private List<SnsProfileItem> SnsProfileItem;
        private int ResultCode;
        private String ResultInfo;

        public String getTo_Account() {
            return To_Account;
        }

        public void setTo_Account(String to_Account) {
            To_Account = to_Account;
        }

        public List<FriendDetailResult.SnsProfileItem> getSnsProfileItem() {
            return SnsProfileItem;
        }

        public void setSnsProfileItem(List<FriendDetailResult.SnsProfileItem> snsProfileItem) {
            SnsProfileItem = snsProfileItem;
        }

        public int getResultCode() {
            return ResultCode;
        }

        public void setResultCode(int resultCode) {
            ResultCode = resultCode;
        }

        public String getResultInfo() {
            return ResultInfo;
        }

        public void setResultInfo(String resultInfo) {
            ResultInfo = resultInfo;
        }
    }

    public static class SnsProfileItem {

        private String Tag;
        private Object Value;

        public String getTag() {
            return Tag;
        }

        public void setTag(String tag) {
            Tag = tag;
        }

        public Object getValue() {
            return Value;
        }

        public void setValue(Object value) {
            Value = value;
        }
    }
}
    