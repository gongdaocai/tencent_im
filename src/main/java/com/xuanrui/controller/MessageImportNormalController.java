package com.xuanrui.controller;

import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.core.model.result.Result;
import com.xuanrui.service.NormalImportMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 导数据
 * @Author: gdc
 * @Date: 2019-08-22 15:00
 **/
@RestController
public class MessageImportNormalController {

    @Autowired
    private NormalImportMessageService normalImportMessage;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageImportNormalController.class);

    /**
     * copyMessage
     *
     * @return Boolean
     */
    @RequestMapping(value = "copyMessageNormal", method = RequestMethod.GET)
    public Result copyMessageNormal() {
        try {
            normalImportMessage.copyMessage();
            return Result.createSuccess();
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====copy-失败 reason:{}", e);
            return Result.createError();
        }
    }

    /**
     * importUser
     *
     * @return Boolean
     */
    @RequestMapping(value = "importUser", method = RequestMethod.GET)
    public Result importUser() {
        try {
            normalImportMessage.importUser();
            return Result.createSuccess();
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====importUser-失败 reason:{}", e);
            return Result.createError();
        }
    }

}
    