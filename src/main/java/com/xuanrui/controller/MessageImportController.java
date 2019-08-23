package com.xuanrui.controller;

import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.core.model.result.Result;
import com.xuanrui.service.ImportMessageNoraml;
import com.xuanrui.service.MessageImportService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 导数据
 * @Author: gdc
 * @Date: 2019-08-22 15:00
 **/
@RestController
public class MessageImportController {

    @Autowired
    private MessageImportService messageImportService;
    @Autowired
    private ImportMessageNoraml importMessageNoraml;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageImportController.class);

    /**
     * copyMessage
     *
     * @return Boolean
     */
    @RequestMapping(value = "copyMessage", method = RequestMethod.GET)
    public Result copyMessage() {
        try {
            messageImportService.copyMessage();
            return Result.createSuccess("success");
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====copy-失败 reason:{}", e);
            return Result.createError();
        }
    }

    /**
     * copyMessage
     *
     * @return Boolean
     */
    @RequestMapping(value = "copyMessageNormal", method = RequestMethod.GET)
    public Result copyMessageNormal() {
        try {
            importMessageNoraml.copyMessage();
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
            messageImportService.importUser();
            return Result.createSuccess();
        } catch (BizException b) {
            return Result.createBizError(0, b.getMessage());
        } catch (Exception e) {
            LOGGER.error("<<<<====importUser-失败 reason:{}", e);
            return Result.createError();
        }
    }


    @Async("asyncServiceExecutor")
    public void writeTxt(String fileName) {
        LOGGER.info("线程-" + Thread.currentThread().getId() + "在执行同步");
        try {

        } catch (Exception e) {

        }
    }
}
    