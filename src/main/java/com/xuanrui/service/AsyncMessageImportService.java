package com.xuanrui.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.config.ServiceNameURL;
import com.xuanrui.common.constant.BusinessConstant;
import com.xuanrui.common.constant.ServiceName;
import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.utils.HttpUtils;
import com.xuanrui.model.request.Message;
import com.xuanrui.model.request.UserAccount;
import com.xuanrui.model.response.CommonResult;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @Description: 数据导入多线程
 * @Author: gdc
 * @Date: 2019-08-22 15:01
 **/
@Service
public class AsyncMessageImportService {
    @Autowired
    HttpUtils httpUtils;
    @Autowired
    private ServiceNameURL serviceNameURL;
    @Autowired
    private MessageSendService messageSendService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncMessageImportService.class);


    /**
     * 发送消息
     *
     * @param message 消息数据
     * @return Boolean
     */
    @Async("asyncServiceExecutor")
    public Future<Map<String, Object>> importMessage(Message message) {
        boolean result;
        //LOGGER.info("<<<<<======线程{}执行", Thread.currentThread().getName() + "-" + Thread.currentThread().getId());
        Map<String, Object> dataMap = new HashMap<>(8);
        String url;
        dataMap.put("SyncOtherMachine", message.getSyncOtherMachine());
        dataMap.put("From_Account", message.getMessageFrom());
        if (message.getMessageTo().startsWith(BusinessConstant.PREFIX) && message.getMessageTo().endsWith(BusinessConstant.SUFFIX)) {
            JSONArray jsonArray = JSONArray.parseArray(message.getMessageTo());
            if (CollectionUtils.isEmpty(jsonArray)) {
                throw new BizException(BusinessConstant.MSG_RECEIVER_EMPTY);
            }
            if (jsonArray.size() > BusinessConstant.MAX_SEND_COUNT) {
                throw new BizException(BusinessConstant.MSG_SEND_MAX_LIMIT);
            }
            url = serviceNameURL.getServiceUrl(ServiceName.MSG_SEND_BATCH);
            dataMap.put("To_Account", jsonArray);
        } else {
            if (message.getMsgTimeStamp() != null && message.getSyncFromOldSystem() != null) {
                url = serviceNameURL.getServiceUrl(ServiceName.MSG_IMPORT);
                dataMap.put("SyncFromOldSystem", message.getSyncFromOldSystem());
            } else {
                url = serviceNameURL.getServiceUrl(ServiceName.MSG_SEND);
            }
            dataMap.put("To_Account", message.getMessageTo());
        }

        dataMap.put("MsgRandom", RandomUtils.nextInt());
        dataMap.put("MsgTimeStamp", message.getMsgTimeStamp() == null ? LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) : message.getMsgTimeStamp());
        dataMap.put("MsgBody", messageSendService.msgBody(message));

        CommonResult commonResult = JSONObject.parseObject(httpUtils.postForObject(url, dataMap), CommonResult.class);
//        LOGGER.info(JSONObject.toJSONString(commonResult));
        if (commonResult == null || !commonResult.isSuccess()) {
            LOGGER.error("<<<===发送消息-失败 params:{} reason:{errorCode={} errorInfo={}}", JSONObject.toJSONString(dataMap), commonResult == null ? "" : commonResult.getErrorCode(), commonResult == null ? "" : commonResult.getErrorInfo());
            result = false;
        } else {
            result = true;
        }

        Map<String, Object> map = new HashMap(2);
        map.put("id", message.getMessageId());
        map.put("success", result);
        return new AsyncResult<>(map);
    }


    @Async("asyncServiceExecutor")
    public Future<Map<String, Object>> createAccount(UserAccount userAccount) {
        boolean result = true;
        Map<String, Object> map = new HashMap<>();
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
            result = false;
        }
        if (!CollectionUtils.isEmpty(commonResult.getFailAccounts())) {
            LOGGER.error("导入失败账号 {}", JSONObject.toJSONString(commonResult.getFailAccounts()));
        }
        map.put("phone", userAccount.getPhone());
        map.put("success", result);
        return new AsyncResult<>(map);
    }
}
    