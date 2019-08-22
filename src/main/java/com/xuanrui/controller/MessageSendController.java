package com.xuanrui.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.core.model.result.Result;
import com.xuanrui.common.utils.ValidationUtils;
import com.xuanrui.model.request.Message;
import com.xuanrui.service.MessageSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 消息
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@RestController
@RequestMapping(value = "message")
public class MessageSendController {

    private MessageSendService messageSendService;

    @Autowired
    public MessageSendController(MessageSendService messageSendService) {
        this.messageSendService = messageSendService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);

    /**
     * 发送消息
     *
     * @param message 消息数据
     * @return Boolean
     */
    @RequestMapping(value = "senMessage", method = RequestMethod.POST)
    public Result sendMessage(@RequestBody Message message) {
        try {
            ValidationUtils.validate(message);
            if (messageSendService.senMessage(message)) {
                return Result.createSuccess();
            } else {
                return Result.createBizError(0, "发送失败");
            }
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====发送消息-失败 params:{} reason:{}", JSONObject.toJSONString(message), e);
            return Result.createError();
        }
    }
}
