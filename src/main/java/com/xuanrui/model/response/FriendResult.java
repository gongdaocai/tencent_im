package com.xuanrui.model.response;

import java.util.List;

/**
 * @Description: 好友列表
 * @Author: gdc
 * @Date: 2019-08-19 17:26
 **/
public class FriendResult extends CommonResult {
    private List<UserDataItem> UserDataItem;
    private int StandardSequence;
    private int CustomSequence;
    private int FriendNum;
    private int CompleteFlag;
    private int NextStartIndex;

    public List<FriendResult.UserDataItem> getUserDataItem() {
        return UserDataItem;
    }

    public void setUserDataItem(List<FriendResult.UserDataItem> userDataItem) {
        UserDataItem = userDataItem;
    }

    public int getStandardSequence() {
        return StandardSequence;
    }

    public void setStandardSequence(int standardSequence) {
        StandardSequence = standardSequence;
    }

    public int getCustomSequence() {
        return CustomSequence;
    }

    public void setCustomSequence(int customSequence) {
        CustomSequence = customSequence;
    }

    public int getFriendNum() {
        return FriendNum;
    }

    public void setFriendNum(int friendNum) {
        FriendNum = friendNum;
    }

    public int getCompleteFlag() {
        return CompleteFlag;
    }

    public void setCompleteFlag(int completeFlag) {
        CompleteFlag = completeFlag;
    }

    public int getNextStartIndex() {
        return NextStartIndex;
    }

    public void setNextStartIndex(int nextStartIndex) {
        NextStartIndex = nextStartIndex;
    }

    public static class UserDataItem {

        private String To_Account;
        private List<ValueItem> ValueItem;

        public String getTo_Account() {
            return To_Account;
        }

        public void setTo_Account(String to_Account) {
            To_Account = to_Account;
        }

        public List<FriendResult.ValueItem> getValueItem() {
            return ValueItem;
        }

        public void setValueItem(List<FriendResult.ValueItem> valueItem) {
            ValueItem = valueItem;
        }
    }

    public  static  class ValueItem {
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
    