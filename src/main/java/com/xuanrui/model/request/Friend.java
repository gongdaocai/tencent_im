package com.xuanrui.model.request;

import java.util.List;

/**
 * @Description: 好友
 * @Author: gdc
 * @Date: 2019-08-19 17:33
 **/
public class Friend {
    /**
     * 加好友者 必填
     */
    private String phone;
    /**
     * 被加好友列表 必填
     */
    private List<AddFriend> addFriendList;
    /**
     * 加好友方式（默认双向加好友方式）：
     * "Add_Type_Single" 表示单向加好友；
     * "Add_Type_Both" 表示双向加好友
     */
    private String addType;
    /**
     * 管理员强制加好友标记：1表示强制加好友；0表示常规加好友方式
     */
    private int aorceAddFlags;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<AddFriend> getAddFriendList() {
        return addFriendList;
    }

    public void setAddFriendList(List<AddFriend> addFriendList) {
        this.addFriendList = addFriendList;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public int getAorceAddFlags() {
        return aorceAddFlags;
    }

    public void setAorceAddFlags(int aorceAddFlags) {
        this.aorceAddFlags = aorceAddFlags;
    }

    /**
     * 好友结构体对象
     */
    public static class AddFriend {
        /**
         *
         * 好友的 Identifier 必填
         */
        private String phone;
        /**
         * From_Account 对 To_Account 的好友备注
         */
        private String remark;
        /**
         * From_Account 对 To_Account 的分组信息
         */
        private String groupName;
        /**
         * 加好友来源字段 必填
         * 1. 加好友来源字段包含前缀和关键字两部分；
         * 2. 加好友来源字段的前缀是：AddSource_Type_ ；
         * 3. 关键字：必须是英文字母，且长度不得超过 8 字节，建议用一个英文单词或该英文单词的缩写；
         * 4. 示例：加好友来源的关键字是 Android，则加好友来源字段是：AddSource_Type_Android
         */
        private String addSource;
        /**
         * From_Account 和 To_Account 形成好友关系时的附言信息
         */
        private String addWording;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getAddSource() {
            return addSource;
        }

        public void setAddSource(String addSource) {
            this.addSource = addSource;
        }

        public String getAddWording() {
            return addWording;
        }

        public void setAddWording(String addWording) {
            this.addWording = addWording;
        }
    }
}
    