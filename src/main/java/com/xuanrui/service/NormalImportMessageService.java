package com.xuanrui.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanrui.common.constant.MessageType;
import com.xuanrui.dao.MessageImportDao;
import com.xuanrui.model.request.Message;
import com.xuanrui.model.request.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 数据导入单线程
 * @Author: gdc
 * @Date: 2019-08-23 10:25
 **/
@Service
public class NormalImportMessageService {
    @Autowired
    private MessageImportDao messageImportDao;

    @Autowired
    private MessageSendService sendService;

    @Autowired
    private UserAccountService userAccountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.xuanrui.service.AsyncMessageImportService.class);

    public void copyMessage() {
        Long start = System.currentTimeMillis();
        List<Long> successList;
        List<Long> errorList;
        List<Message> messageList;
        int total = 0;
        int errorTotal = 0;
        int successTotal = 0;
        try {
            PageInfo pageInfo;
            boolean flag = true;
            int pageNum = 1;
            while (flag) {
                PageHelper.startPage(pageNum, 100);
                messageList = messageImportDao.listMessage();
                if (!CollectionUtils.isEmpty(messageList)) {
                    successList = new ArrayList<>();
                    errorList = new ArrayList<>();
                    pageInfo = new PageInfo(messageList);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parse;
                    LOGGER.info("===>>>第{}页数据--同步开始 同步消息总数 :{}", pageNum, messageList.size());
                    for (Message message : messageList) {
                        try {
                            message.setSyncFromOldSystem((byte) 2);
                            if(message.getType()==100){
                                message.setType(MessageType.MSG_CUSTOM.getKey());
                            }else if(message.getType()==0){
                                message.setType(MessageType.MSG_TEXT.getKey());
                            }
                            parse = format.parse(message.getLocationDesc());
                            message.setMsgTimeStamp(parse.getTime() / 1000);
                            Boolean aBoolean = sendService.senMessage(message);
                            if (!aBoolean) {
                                errorList.add(message.getMessageId());
                            } else {
                                successList.add(message.getMessageId());
                            }
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--同步失败 消息id:{} reason:{}", pageNum, message.getMessageId(), e);
                            errorList.add(message.getMessageId());
                            continue;
                        }
                    }
                    LOGGER.info("<<<===第{}页数据--同步结束 同步消息总数 :{} 同步失败总数:{}", pageNum, messageList.size(), errorList.size());
                    //输出到文件
                    if (!CollectionUtils.isEmpty(errorList)) {
                        try {
                            messageImportDao.saveErrorMessage(errorList);
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--保存同步失败数据--失败 errorList:{}", pageNum, JSONObject.toJSON(errorList), e);
                        }
                    }
                    if (!CollectionUtils.isEmpty(successList)) {
                        try {
                            messageImportDao.saveSuccessMessage(successList);
                        } catch (Exception e) {
                            LOGGER.error("<<<===-第{}页数据--保存同步成功数据--失败 errorList:{}", pageNum, JSONObject.toJSON(successList), e);
                        }
                    }
                    total = total + messageList.size();
                    errorTotal = errorTotal + errorList.size();
                    successTotal = successTotal + successList.size();
                    if (pageInfo.isHasNextPage()) {
                        pageNum = pageInfo.getNextPage();
                    } else {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            }
            LOGGER.info("<<<===所有数据--同步结束 同步消息总数 :{} 同步失败总数:{} 同步失败总数:{}", total, successTotal, errorTotal);
        } catch (Exception e) {
            //失败需要将成功的记录记下来
            LOGGER.error("<<<<====同步数据准备中-失败 reason:{}", e);
        }
        LOGGER.info("<<<====同步结束 耗时{}秒", (System.currentTimeMillis() - start) / 1000);
    }


    public void importUser() {
        List<UserAccount> userAccountList;
        Long start = System.currentTimeMillis();
        List<String> successList;
        List<String> errorList;
        int total = 0;
        int errorTotal = 0;
        int successTotal = 0;
        PageInfo pageInfo;
        boolean flag = true;
        int pageNum = 1;
        try {
            while (flag) {
                PageHelper.startPage(pageNum, 500);
                userAccountList = messageImportDao.listUserInfo();
                if (!CollectionUtils.isEmpty(userAccountList)) {
                    successList = new ArrayList<>();
                    errorList = new ArrayList<>();
                    pageInfo = new PageInfo(userAccountList);
                    for (UserAccount account : userAccountList) {
                        try {
                            if (userAccountService.createAccount(account)) {
                                successList.add(account.getPhone());
                            } else {
                                errorList.add(account.getPhone());
                            }
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--同步失败 用户:{} reason:{}", pageNum, JSONObject.toJSON(account), e);
                            errorList.add(account.getPhone());
                        }
                    }
                    LOGGER.info("<<<===第{}页数据--同步结束 同步用户总数 :{} 同步失败总数:{}", pageNum, userAccountList.size(), errorList.size());
                    //输出到文件
                    if (!CollectionUtils.isEmpty(errorList)) {
                        try {
                            messageImportDao.saveErrorUser(errorList);
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--保存同步失败数据--失败 errorList:{}", pageNum, JSONObject.toJSON(errorList), e);
                        }
                    }
                    if (!CollectionUtils.isEmpty(successList)) {
                        try {
                            messageImportDao.saveSuccessUser(successList);
                        } catch (Exception e) {
                            LOGGER.error("<<<===-第{}页数据--保存同步成功数据--失败 errorList:{}", pageNum, JSONObject.toJSON(successList), e);
                        }
                    }
                    total = total + userAccountList.size();
                    errorTotal = errorTotal + errorList.size();
                    successTotal = successTotal + successList.size();
                    if (pageInfo.isHasNextPage()) {
                        pageNum = pageInfo.getNextPage();
                    } else {
                        flag = false;
                    }

                } else {
                    flag = false;
                }
            }
            LOGGER.info("<<<===所有数据--同步结束 同步用户总数 :{} 同步成功总数:{} 同步失败总数:{}", total, successTotal, errorTotal);
        } catch (Exception e) {
            LOGGER.error("<<<<=====同步用户数据-失败 reason:{}", e);
        }
        LOGGER.info("<<<====同步结束 耗时{}秒", (System.currentTimeMillis() - start) / 1000);
    }


}
