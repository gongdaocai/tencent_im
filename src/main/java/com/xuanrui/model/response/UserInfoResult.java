package com.xuanrui.model.response;

import java.util.List;

/**
 * @Description: rest用户返回
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
public class UserInfoResult extends CommonResult {


    private List<UserProfileItem> UserProfileItem;


    public List<UserProfileItem> getUserProfileItem() {
        return UserProfileItem;
    }

    public void setUserProfileItem(List<UserInfoResult.UserProfileItem> userProfileItem) {
        UserProfileItem = userProfileItem;
    }


    public static class UserProfileItem {
        private String To_Account;

        private List<ProfileItem> ProfileItem;

        private int ResultCode;

        private String ResultInfo;

        public String getTo_Account() {
            return To_Account;
        }

        public void setTo_Account(String to_Account) {
            To_Account = to_Account;
        }

        public List<ProfileItem> getProfileItem() {
            return ProfileItem;
        }

        public void setProfileItem(List<ProfileItem> profileItem) {
            ProfileItem = profileItem;
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


    public static class ProfileItem {
        private String Tag;

        private String Value;

        public String getTag() {
            return Tag;
        }

        public void setTag(String tag) {
            Tag = tag;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}

