package com.xuanrui.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanrui.dao.MessageDao;
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
import java.util.Set;

/**
 * @Description: 导入数据
 * @Author: gdc
 * @Date: 2019-08-23 10:25
 **/
@Service
public class ImportMessageNoraml {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageSendService sendService;

    @Autowired
    private UserAccountService userAccountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(com.xuanrui.service.MessageImportService.class);

    public void copyMessage() {
        Long start = System.currentTimeMillis();
        List<Long> successList;
        List<Long> errorList;
        List<Message> messageList = new ArrayList<>();
        int total = 0;
        int errorTotal = 0;
        int successTotal = 0;
        try {
            PageInfo pageInfo;
            boolean flag = true;
            int pageNum = 1;
            while (flag) {
                PageHelper.startPage(pageNum, 100);
                messageList = messageDao.copydata();
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
                            message.setType((byte) 1);
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
                            messageDao.saveError(errorList);
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--保存同步失败数据--失败 errorList:{}", pageNum, JSONObject.toJSON(errorList), e);
                        }
                    }
                    if (!CollectionUtils.isEmpty(successList)) {
                        try {
                            messageDao.saveSuccess(successList);
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
        Set<String> userList1 = messageDao.listUserFrom();
        Set<String> userList2 = messageDao.listUserTo();
        userList1.addAll(userList2);
        if (userList1.size() > 0) {
            LOGGER.info("<<<===用户账户-同步开始--本次同步总数{}", userList1.size());
            UserAccount userAccount = new UserAccount();
            userAccount.setPhone(JSONObject.toJSONString(userList1));
            Boolean account = userAccountService.createAccount(userAccount);
            if (account) {
                LOGGER.info("<<<==同步用户成功 共同步总数 :{}", userList1.size());
            }
        }
    }


}