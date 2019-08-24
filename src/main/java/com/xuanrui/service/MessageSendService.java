package com.xuanrui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.config.ServiceNameURL;
import com.xuanrui.common.constant.BusinessConstant;
import com.xuanrui.common.constant.MessageType;
import com.xuanrui.common.constant.ServiceName;
import com.xuanrui.common.core.model.result.BizException;
import com.xuanrui.common.utils.HttpUtils;
import com.xuanrui.dao.MessageDao;
import com.xuanrui.model.request.Message;
import com.xuanrui.model.response.CommonResult;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 后台服务端发送消息
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@Service
public class MessageSendService {

    private ServiceNameURL serviceNameURL;
    private HttpUtils httpUtils;
    private MessageDao messageDao;
    private UserAccountService userAccountService;

    @Autowired
    public MessageSendService(ServiceNameURL serviceNameURL, HttpUtils httpUtils, MessageDao messageDao, UserAccountService userAccountService) {
        this.httpUtils = httpUtils;
        this.serviceNameURL = serviceNameURL;
        this.messageDao = messageDao;
        this.userAccountService = userAccountService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSendService.class);


    /**
     * 发送消息
     *
     * @param message 消息数据
     * @return Boolean
     */
    public Boolean senMessage(Message message) {
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
        dataMap.put("MsgBody", msgBody(message));

        CommonResult commonResult = JSONObject.parseObject(httpUtils.postForObject(url, dataMap), CommonResult.class);
//        LOGGER.info(JSONObject.toJSONString(commonResult));
        if (commonResult == null || !commonResult.isSuccess()) {
            LOGGER.error("<<<===发送消息-失败 params:{} reason:{errorCode={} errorInfo={}}", JSONObject.toJSONString(dataMap), commonResult == null ? "" : commonResult.getErrorCode(), commonResult == null ? "" : commonResult.getErrorInfo());
            return false;
        }
        return true;
    }

    /**
     * 封装Rest消息Body
     *
     * @param message 消息原数据
     * @return 封装后的Rest请求消息Body
     */
    public List<Map<String, Object>> msgBody(Message message) {
        List<Map<String, Object>> msgList = new ArrayList<>(8);
        if (!CollectionUtils.isEmpty(message.getMsgList())) {
            for (Message x : message.getMsgList()) {
                msgList.add(convertMessage(x));
            }
        } else {
            msgList.add(convertMessage(message));
        }
        return msgList;
    }


    /**
     * 单个消息封装
     * 服务端只支持文本 表情 位置 自定义消息发送 其他类型消息通过客户端发送
     *
     * @param message 消息数据
     * @return Rest请求消息body
     */
    private Map<String, Object> convertMessage(Message message) {
        Optional.ofNullable(message.getType()).orElseThrow(() -> new BizException(BusinessConstant.MSG_TYPE_ERROR));

        Map<String, Object> msgMap = new HashMap<>(6);
        Map<String, Object> content = new HashMap<>(4);

        MessageType messageType = MessageType.getMessageType(message.getType());
        msgMap.put("MsgType", messageType.getMessageType());

        switch (messageType) {
            case MSG_TEXT:
                Optional.ofNullable(message.getText()).orElseThrow(() -> new BizException(BusinessConstant.MSG_CONTENT_ERROR));
                content.put("Text", message.getText());
                break;
            case MSG_FACE:
                if (message.getFaceData() == null || message.getFaceIndex() == null) {
                    throw new BizException(BusinessConstant.MSG_CONTENT_ERROR);
                }
                content.put("Index", message.getFaceIndex());
                content.put("Data", message.getFaceData());
                break;
            case MSG_LOCATION:
                if (message.getLocationDesc() == null || message.getLatitude() == null || message.getLongitude() == null) {
                    throw new BizException(BusinessConstant.MSG_CONTENT_ERROR);
                }
                content.put("Desc", message.getLocationDesc());
                content.put("Latitude", message.getLatitude());
                content.put("Longitude", message.getLongitude());
                break;
            case MSG_CUSTOM:
                Optional.ofNullable(message.getCustom()).orElseThrow(() -> new BizException(BusinessConstant.MSG_CONTENT_ERROR));
                content.put("Data", message.getCustom());
                if (message.getDesc() != null) {
                    content.put("Desc", message.getCustom());
                }
                if (message.getExt() != null) {
                    content.put("Ext", message.getExt());
                }
                if (message.getSound() != null) {
                    content.put("Sound", message.getSound());
                }
                break;
            default:
                throw new BizException(BusinessConstant.MSG_TYPE_NOT_SUPPORT);
        }
        msgMap.put("MsgContent", content);
        return msgMap;
    }
}
