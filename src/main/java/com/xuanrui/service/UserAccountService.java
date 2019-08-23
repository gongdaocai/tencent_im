package com.xuanrui.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.config.ServiceNameURL;
import com.xuanrui.common.constant.AllowType;
import com.xuanrui.common.constant.BusinessConstant;
import com.xuanrui.common.constant.Gender;
import com.xuanrui.common.constant.ServiceName;
import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.utils.HttpUtils;
import com.xuanrui.model.request.Friend;
import com.xuanrui.model.request.UserAccount;
import com.xuanrui.model.response.CommonResult;
import com.xuanrui.model.response.FriendDetailResult;
import com.xuanrui.model.response.FriendResult;
import com.xuanrui.model.response.UserInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 用户
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@Service
public class UserAccountService {

    private ServiceNameURL serviceNameURL;
    private HttpUtils httpUtils;

    @Autowired
    public UserAccountService(ServiceNameURL serviceNameURL, HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
        this.serviceNameURL = serviceNameURL;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonResult.class);

    /**
     * 导入账号
     *
     * @param userAccount 用户数据
     * @return Result
     */
    public Boolean createAccount(UserAccount userAccount) {
        String serviceUrl;
        JSONArray jsonArray;
        Map<String, Object> dataMap = new HashMap<>(4);
        if (userAccount.getPhone().startsWith(BusinessConstant.PREFIX) && userAccount.getPhone().endsWith(BusinessConstant.SUFFIX)) {
            jsonArray = JSON.parseArray(userAccount.getPhone());
            if (CollectionUtils.isEmpty(jsonArray)) {
                throw new BizException(BusinessConstant.PARAMTER_EMTTY);
            }
            serviceUrl = serviceNameURL.getServiceUrl(ServiceName.ACCOUNT_IMPORT_BATCH);
            dataMap.put("Accounts", jsonArray);
        } else {
            Optional.ofNullable(userAccount.getNickName()).orElseThrow(() -> new BizException(BusinessConstant.PARAMTER_EMTTY));
            serviceUrl = serviceNameURL.getServiceUrl(ServiceName.ACCOUNT_IMPORT);

            dataMap.put("Identifier", userAccount.getPhone());
            dataMap.put("Nick", userAccount.getNickName());
            dataMap.put("FaceUrl", StringUtils.isEmpty(userAccount.getFaceUrl()) ? "" : userAccount.getFaceUrl());
            dataMap.put("Type", userAccount.getType());
            if (userAccount.getGender() != null) {

            }
        }

        // start request RestApi
        CommonResult commonResult = JSONObject.parseObject(httpUtils.postForObject(serviceUrl, dataMap), CommonResult.class);
        if (commonResult == null || !commonResult.isSuccess()) {
            LOGGER.error("<<<===导入账号-失败 params:{} reason:{errorCode={} errorInfo={}}", JSONObject.toJSONString(userAccount), commonResult == null ? "" : commonResult.getErrorCode(), commonResult == null ? "" : commonResult.getErrorInfo());
            return false;
        }
        //
        if (!CollectionUtils.isEmpty(commonResult.getFailAccounts())) {
            LOGGER.error("导入失败账号 {}", JSONObject.toJSONString(commonResult.getFailAccounts()));
        }
        return true;
    }

    /**
     * 获取用户信息
     *
     * @param phone 用户账号
     * @return Result
     */
    public UserAccount getUserInfo(String phone) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("To_Account", Arrays.asList(phone));
        List<String> tagList = new ArrayList<>();
        tagList.add("Tag_Profile_IM_Nick");
        tagList.add("Tag_Profile_IM_Gender");
        tagList.add("Tag_Profile_IM_BirthDay");
        tagList.add("Tag_Profile_IM_SelfSignature");
        tagList.add("Tag_Profile_IM_AllowType");
        tagList.add("Tag_Profile_IM_Image");
        dataMap.put("TagList", tagList);
        UserInfoResult userInfoResult = JSONObject.parseObject(httpUtils.postForObject(serviceNameURL.getServiceUrl(ServiceName.INFO_GET), dataMap), UserInfoResult.class);
        if (userInfoResult == null || !userInfoResult.isSuccess()) {
            LOGGER.error("<<<===获取用户信息-失败 params:{} reason:{errorCode={} errorInfo={}}", phone, userInfoResult.getErrorCode(), userInfoResult.getErrorInfo());
            throw new BizException("获取用户信息失败");
        }
        List<UserInfoResult.UserProfileItem> list = userInfoResult.getUserProfileItem();
        UserAccount userAccount = new UserAccount();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> collect = list.get(0).getProfileItem().stream().collect(Collectors.toMap(x -> x.getTag(), x -> x.getValue()));
            userAccount.setNickName(collect.get("Tag_Profile_IM_Nick") == null ? null : collect.get("Tag_Profile_IM_Nick"));
            userAccount.setGender(collect.get("Tag_Profile_IM_Gender") == null ? null : Gender.getGender(collect.get("Tag_Profile_IM_Gender")).getKey());
            userAccount.setBirthDay(collect.get("Tag_Profile_IM_BirthDay") == null ? null : Integer.parseInt(collect.get("Tag_Profile_IM_BirthDay")));
            userAccount.setAllowType(collect.get("Tag_Profile_IM_AllowType") == null ? null : AllowType.getAllowType((collect.get("Tag_Profile_IM_AllowType"))).getKey());
            userAccount.setFaceUrl(collect.get("Tag_Profile_IM_Image") == null ? null : collect.get("Tag_Profile_IM_Image"));
            userAccount.setSelfSignature(collect.get("Tag_Profile_IM_SelfSignature") == null ? null : collect.get("Tag_Profile_IM_SelfSignature"));
        }
        return userAccount;
    }


    /**
     * 更新用户信息
     *
     * @param userAccount 用户
     * @return Result
     */
    public Boolean updateUserInfo(UserAccount userAccount) {
        Optional.ofNullable(userAccount.getPhone()).orElseThrow(() -> new BizException(BusinessConstant.PARAMTER_EMTTY));

        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("From_Account", userAccount.getPhone());

        List<Map<String, Object>> profileItemList = new ArrayList<>();
        Map<String, Object> itemMap = new HashMap<>(2);
        if (!StringUtils.isEmpty(userAccount.getNickName())) {
            itemMap.put("Tag", "Tag_Profile_IM_Nick");
            itemMap.put("Value", userAccount.getNickName());
            profileItemList.add(itemMap);
        }
        if (!StringUtils.isEmpty(userAccount.getFaceUrl())) {
            itemMap = new HashMap<>(2);
            itemMap.put("Tag", "Tag_Profile_IM_Image");
            itemMap.put("Value", userAccount.getFaceUrl());
            profileItemList.add(itemMap);
        }
        if (userAccount.getGender() != null) {
            itemMap = new HashMap<>(2);
            itemMap.put("Tag", "Tag_Profile_IM_Gender");
            itemMap.put("Value", Gender.getGender(userAccount.getGender()).getGender());
            profileItemList.add(itemMap);
        }
        if (!StringUtils.isEmpty(userAccount.getBirthDay())) {
            itemMap = new HashMap<>(2);
            itemMap.put("Tag", "Tag_Profile_IM_BirthDay");
            itemMap.put("Value", userAccount.getBirthDay());
            profileItemList.add(itemMap);
        }
        if (!StringUtils.isEmpty(userAccount.getSelfSignature())) {
            itemMap = new HashMap<>(2);
            itemMap.put("Tag", "Tag_Profile_IM_SelfSignature");
            itemMap.put("Value", userAccount.getSelfSignature());
            profileItemList.add(itemMap);
        }
        if (!StringUtils.isEmpty(userAccount.getAllowType())) {
            itemMap = new HashMap<>(2);
            itemMap.put("Tag", "Tag_Profile_IM_AllowType");
            itemMap.put("Value", AllowType.getAllowType(userAccount.getAllowType()).getAllowType());
            profileItemList.add(itemMap);
        }

        dataMap.put("ProfileItem", profileItemList);
        CommonResult commonResult = JSONObject.parseObject(httpUtils.postForObject(serviceNameURL.getServiceUrl(ServiceName.INFO_SET), dataMap), CommonResult.class);
        if (commonResult == null || !commonResult.isSuccess()) {
            LOGGER.error("<<<===更新用户信息-失败 params:{} reason:{errorCode={} errorInfo={}}", userAccount, commonResult == null ? "" : commonResult.getErrorCode(), commonResult == null ? "" : commonResult.getErrorInfo());
            return false;
        }
        return true;
    }

    /**
     * 获取用户好友
     *
     * @param phone 用户账号
     * @return Result
     */
    public Object listFriend(String phone) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("From_Account", phone);
        dataMap.put("StartIndex", 0);
        dataMap.put("StandardSequence", 0);
        dataMap.put("CustomSequence", 0);
        FriendResult friendResult = JSONObject.parseObject(httpUtils.postForObject(serviceNameURL.getServiceUrl(ServiceName.FRIEND_LIST), dataMap), FriendResult.class);
        if (friendResult == null || !friendResult.isSuccess()) {
            LOGGER.error("<<<===获取用户信息-失败 params:{} reason:{errorCode={} errorInfo={}}", phone, friendResult == null ? "" : friendResult.getErrorCode(), friendResult == null ? "" : friendResult.getErrorInfo());
            throw new BizException("获取用户信息失败");
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("hasNextPage", friendResult.getCompleteFlag() == 0);
        returnMap.put("nextStartIndex", friendResult.getNextStartIndex());
        returnMap.put("totalNum", friendResult.getFriendNum());
        List<Map<String, Object>> friendList = new ArrayList<>();
        Map<String, Object> friend;
        Map<String, Object> collect = null;
        for (FriendResult.UserDataItem item : friendResult.getUserDataItem()) {
            friend = new HashMap<>(6);
            if (!CollectionUtils.isEmpty(item.getValueItem())) {
                collect = item.getValueItem().stream().collect(Collectors.toMap(x -> x.getTag(), x -> x.getValue()));
            }
            friend.put("friendAccount", item.getTo_Account());
            if (collect != null) {
                if (collect.get("Tag_SNS_IM_AddSource") != null) {
                    friend.put("addSource", collect.get("Tag_SNS_IM_AddSource"));
                }
                if (collect.get("Tag_SNS_IM_Remark") != null) {
                    friend.put("remark", collect.get("Tag_SNS_IM_Remark"));
                }
                if (collect.get("Tag_SNS_IM_AddWording") != null) {
                    friend.put("addWording", collect.get("Tag_SNS_IM_AddWording"));
                }
                if (collect.get("Tag_SNS_IM_Group") != null) {
                    friend.put("group", collect.get("Tag_SNS_IM_Group"));
                }
                if (collect.get("Tag_SNS_IM_AddTime") != null) {
                    friend.put("addTime", collect.get("Tag_SNS_IM_AddTime"));
                }

                friend.put("addSource", collect.get("Tag_SNS_IM_AddSource"));
                friend.put("addSource", collect.get("Tag_SNS_IM_AddSource"));
            }
            friendList.add(friend);
        }
        returnMap.put("list", friendList);
        return returnMap;
    }

    /**
     * 添加好友
     *
     * @param friend 好友
     * @return Result
     */
    public Boolean addFriend(Friend friend) {
        Optional.ofNullable(friend.getPhone()).orElseThrow(() -> new BizException(BusinessConstant.PARAMTER_EMTTY));
        if (CollectionUtils.isEmpty(friend.getAddFriendList())) {
            throw new BizException(BusinessConstant.PARAMTER_EMTTY);
        }
        Map<String, Object> dataMap = new HashMap<>(4);
        List<Map<String, Object>> friendList = new ArrayList<>();
        Map<String, Object> friendMap;
        for (Friend.AddFriend x : friend.getAddFriendList()) {
            friendMap = new HashMap<>(6);

            friendMap.put("To_Account", x.getPhone());
            if (!StringUtils.isEmpty(x.getRemark())) {
                friendMap.put("Remark", x.getRemark());
            }
            if (!StringUtils.isEmpty(x.getGroupName())) {
                friendMap.put("GroupName", x.getGroupName());
            }
            if (StringUtils.isEmpty(x.getAddSource())) {
                throw new BizException("参数错误");
            }
            //AddSource_Type_android
            friendMap.put("AddSource", x.getAddSource());
            if (!StringUtils.isEmpty(x.getAddWording())) {
                friendMap.put("AddWording", x.getAddWording());
            }
            friendList.add(friendMap);
        }

        dataMap.put("From_Account", friend.getPhone());
        dataMap.put("AddFriendItem", friendList);
        // dataMap.put("AddType",friend.getPhone());

        CommonResult commonResult = JSONObject.parseObject(httpUtils.postForObject(serviceNameURL.getServiceUrl(ServiceName.FRIEND_ADD), dataMap), CommonResult.class);
        if (commonResult == null || !commonResult.isSuccess()) {
            LOGGER.error("<<<===添加好友-失败 params:{} reason:{errorCode={} errorInfo={}}", JSONObject.toJSONString(friend), commonResult == null ? "" : commonResult.getErrorCode(), commonResult == null ? "" : commonResult.getErrorInfo());
            return false;
        }
        return true;
    }

    /**
     * 获取指定用户好友
     *
     * @param fromAccount 用户
     * @param toAccount   指定好友
     * @return Result
     */
    public Object getFriendDetail(String fromAccount, String toAccount) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("From_Account", fromAccount);
        dataMap.put("To_Account", Arrays.asList(toAccount));
        List<String> tagList = new ArrayList<>();
        tagList.add("Tag_Profile_IM_Nick");
        tagList.add("Tag_Profile_IM_Gender");
        tagList.add("Tag_Profile_IM_Image");

        tagList.add("Tag_SNS_IM_Remark");
        tagList.add("Tag_SNS_IM_Group");
        dataMap.put("TagList", tagList);
        FriendDetailResult detailResult = JSONObject.parseObject(httpUtils.postForObject(serviceNameURL.getServiceUrl(ServiceName.FRIEND_GET), dataMap), FriendDetailResult.class);
        if (detailResult == null || !detailResult.isSuccess()) {
            LOGGER.error("<<<===获取指定用户好友-失败 params:{fromAccount={} toAccount={}} reason:{errorCode={} errorInfo={}}", fromAccount, toAccount, detailResult == null ? "" : detailResult.getErrorCode(), detailResult == null ? "" : detailResult.getErrorInfo());
            throw new BizException("获取好友信息失败");
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("friendAccount", toAccount);
        if (!CollectionUtils.isEmpty(detailResult.getInfoItem()) && !CollectionUtils.isEmpty(detailResult.getInfoItem().get(0).getSnsProfileItem())) {
            Map<String, Object> collect = detailResult.getInfoItem().get(0).getSnsProfileItem().stream().collect(Collectors.toMap(x -> x.getTag(), x -> x.getValue()));
            if (collect.get("Tag_Profile_IM_Nick") != null) {
                returnMap.put("friendNickName", collect.get("Tag_Profile_IM_Nick"));
            }
            if (collect.get("Tag_Profile_IM_Gender") != null) {
                returnMap.put("friendGender", Gender.getGender(collect.get("Tag_Profile_IM_Gender").toString()).getKey());
            }
            if (collect.get("Tag_Profile_IM_Image") != null) {
                returnMap.put("friendFaceUrl", collect.get("Tag_Profile_IM_Image"));
            }
            if (collect.get("Tag_SNS_IM_Remark") != null) {
                returnMap.put("remark", collect.get("Tag_SNS_IM_Remark"));
            }
            if (collect.get("Tag_SNS_IM_Group") != null) {
                returnMap.put("friendGroup", collect.get("Tag_SNS_IM_Group"));
            }
        }
        return returnMap;
    }
}
