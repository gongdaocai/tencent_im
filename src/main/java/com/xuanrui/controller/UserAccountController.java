package com.xuanrui.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.constant.BusinessConstant;
import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.core.model.result.Result;
import com.xuanrui.model.request.Friend;
import com.xuanrui.model.request.UserAccount;
import com.xuanrui.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Description: 用户
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@RestController
@RequestMapping(value = "account")
public class UserAccountController {


    private UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);

    /**
     * 导入账号
     *
     * @param userAccount 用户数据
     * @return Result
     */
    @PostMapping(value = "createAccount")
    public Result createAccount(@RequestBody UserAccount userAccount) {
        Optional.ofNullable(userAccount.getPhone()).orElseThrow(() -> new BizException(BusinessConstant.PARAMTER_EMTTY));
        try {
            return Result.createSuccess("success", userAccountService.createAccount(userAccount));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====导入账号-失败 params:{} reason:{}", JSONObject.toJSONString(userAccount), e);
            return Result.createError();
        }

    }

    /**
     * 获取用户信息
     *
     * @param phone 用户账号
     * @return Result
     */
    @GetMapping(value = "getUserInfo")
    public Result getUserInfo(@RequestParam(value = "phone") String phone) {
        try {
            return Result.createSuccess("success", userAccountService.getUserInfo(phone));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====获取用户信息-失败 params:{} reason:{}", phone, e);
            return Result.createError();
        }

    }

    /**
     * 更新用户信息
     *
     * @param userAccount 用户
     * @return Result
     */
    @PostMapping(value = "updateUserInfo")
    public Result updateUserInfo(@RequestBody UserAccount userAccount) {
        try {
            return Result.createSuccess("success", userAccountService.updateUserInfo(userAccount));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====更新用户信息-失败 params:{} reason:{}", JSONObject.toJSONString(userAccount), e);
            return Result.createError();
        }

    }


    /**
     * 获取用户好友
     *
     * @param phone 用户账号
     * @return Result
     */
    @GetMapping(value = "listFriend")
    public Result listFriend(@RequestParam(value = "phone") String phone) {
        try {
            return Result.createSuccess("success", userAccountService.listFriend(phone));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====获取用户好友-失败 params:{} reason:{}", phone, e);
            return Result.createError();
        }
    }

    /**
     * 获取指定用户好友
     *
     * @param fromAccount 用户
     * @param toAccount   指定好友
     * @return Result
     */
    @GetMapping(value = "getFriendDetail")
    public Result getFriendDetail(@RequestParam(value = "fromAccount") String fromAccount, @RequestParam(value = "toAccount") String toAccount) {
        try {
            return Result.createSuccess("success", userAccountService.getFriendDetail(fromAccount, toAccount));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====获取指定用户好友-失败 params:{fromAccount={} toAccount={}} reason:{}", fromAccount, toAccount, e);
            return Result.createError();
        }
    }

    /**
     * 添加好友
     *
     * @param friend 好友
     * @return Result
     */
    @PostMapping(value = "addFriend")
    public Result addFriend(@RequestBody Friend friend) {
        try {
            return Result.createSuccess("success", userAccountService.addFriend(friend));
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====添加好友-失败 params:{} reason:{}", JSONObject.toJSONString(friend), e);
            return Result.createError();
        }

    }
}
