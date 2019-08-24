package com.xuanrui.controller;

import com.xuanrui.common.core.model.result.Result;
import com.xuanrui.service.UserSigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 获取userSig
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@RestController
@RequestMapping(value = "/userSig")
public class UserSigController {
    @Autowired
    private UserSigService userSigService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSigController.class);

    /**
     * 获取签名
     *
     * @param phone 用户账号
     * @return Result
     */
    @GetMapping(value = "getUserSig")
    public Result getUserSig(@RequestParam(value = "phone") String phone) {
        try {
            return Result.createSuccess("success", userSigService.genSig(phone));
        } catch (Exception e) {
            LOGGER.error("<<<<====获取用户Sig-失败 params:{} reason:{}", phone, e);
            return Result.createError();
        }
    }

}
