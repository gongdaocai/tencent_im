package com.xuanrui;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuanrui.dao.MessageDao;
import com.xuanrui.model.request.Message;
import com.xuanrui.service.MessageSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class ImportMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportMessage.class);

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private MessageSendService sendService;
    private CountDownLatch countDownLatch;


    @Test
    public void copyMessage() {
        Long start = System.currentTimeMillis();
        List<Long> successList;
        List<Long> errorList;
        List<Message> messageList;
        int total = 0;
        int errorTotal = 0;
        int successTotal = 0;
        List<Future<Map<String, Object>>> futures;
        Map<String, Object> longBooleanMap;
        PageInfo pageInfo;
        boolean flag = true;
        int pageNum = 1;
        try {
            while (flag) {
                PageHelper.startPage(pageNum, 500);
                messageList = messageDao.copydata();
                if (!CollectionUtils.isEmpty(messageList)) {
                    countDownLatch = new CountDownLatch(messageList.size());
                    futures = new ArrayList<>();
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
                            Future<Map<String, Object>> mapFuture = sendService.importMessage(countDownLatch, message);
                            futures.add(mapFuture);
                        } catch (Exception e) {
                            LOGGER.error("<<<===第{}页数据--同步失败 消息id:{} reason:{}", pageNum, message.getMessageId(), e);
                            errorList.add(message.getMessageId());
                            continue;
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
                    countDownLatch.await();
                    //处理本次结果
                    for (Future<Map<String, Object>> future : futures) {
                        longBooleanMap = future.get();
                        if ((Boolean) longBooleanMap.get("success")) {
                            successList.add((Long) longBooleanMap.get("id"));
                        } else {
                            LOGGER.error("<<<===第{}页数据--同步失败 消息id:{} reason:{}", pageNum, longBooleanMap.get("id"));
                            errorList.add((Long) longBooleanMap.get("id"));
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
            LOGGER.info("<<<===所有数据--同步结束 同步消息总数 :{} 同步成功总数:{} 同步失败总数:{}", total, successTotal, errorTotal);
        } catch (Exception e) {
            LOGGER.error("<<<<====同步数据准备中-失败 reason:{}", e);
        }

        LOGGER.info("<<<====同步结束 耗时{}秒", (System.currentTimeMillis() - start) / 1000);
    }

}