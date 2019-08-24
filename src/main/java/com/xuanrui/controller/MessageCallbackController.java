package com.xuanrui.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuanrui.common.config.MyConfig;
import com.xuanrui.common.constant.CallBackType;
import com.xuanrui.common.constant.ImageFormat;
import com.xuanrui.common.constant.MessageType;
import com.xuanrui.model.dataobject.MessageDO;
import com.xuanrui.model.response.CallBackDataResult;
import com.xuanrui.model.response.CallBackResult;
import com.xuanrui.model.response.CommonResult;
import com.xuanrui.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 消息回调
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@RestController
public class MessageCallbackController {

    private MessageService callBackService;
    private MyConfig myConfig;

    public MessageCallbackController(MessageService callBackService, MyConfig myConfig) {
        this.callBackService = callBackService;
        this.myConfig = myConfig;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCallbackController.class);

    /**
     * 即时通信发送消息回调
     *
     * @param request 回调请求
     * @return CommonResult
     */
    @RequestMapping(value = "callBack", method = RequestMethod.POST)
    public CommonResult sendMessage(HttpServletRequest request) {
        String msgData = null;
        try {
            CallBackResult callback = getQuery(request);
            //验证应用id是否一致
            if (callback != null && callback.getCallBackCommand() != null && String.valueOf(myConfig.getAppId()).equals(callback.getSdkAppId())) {
                MessageDO messageDO = null;
                msgData = getBody(request);
                //根据回调的类型分别解析消息数据
                switch (CallBackType.getCallBackType(callback.getCallBackCommand())) {
                    case C2C_CallbackAfterSendMsg:
                        messageDO = parseData(msgData);
                        break;
                    case Group_CallbackBeforeSendMsg:
                        break;
                    default:
                }
                //调用service保存
                if (messageDO != null) {
                    callBackService.saveMessage(messageDO);
                }
            }
        } catch (Exception e) {
            LOGGER.error("<<<===消息回调-保存消息失败 params:{queryStr={} msgData={}} reason:{}", request.getQueryString(), msgData, e);
            return CommonResult.createFailed();
        }
        LOGGER.info("<<<===消息回调-保存消息成功 params:{queryStr={} msgData={}}MessageDao", request.getQueryString(), msgData);
        return CommonResult.createSuccess();
    }

    private MessageDO parseData(String msgData) {
        MessageDO message = null;
        //获取消息体
        CallBackDataResult callBackResult = JSONObject.parseObject(msgData, CallBackDataResult.class);
        if (callBackResult != null) {
            message = new MessageDO();
            message.setOpe((byte) 0);
            message.setMessageFrom(callBackResult.getFrom_Account());
            message.setMessageTo(callBackResult.getTo_Account());
            List<CallBackDataResult.MsgBody> msgBody = callBackResult.getMsgBody();
            //单一类型消息
            if (!CollectionUtils.isEmpty(msgBody) && msgBody.size() == 1) {
                CallBackDataResult.MsgBody msg = msgBody.get(0);
                MessageType msgType = MessageType.getMessageType(msg.getMsgType());
                message.setType(msgType.getKey());
                CallBackDataResult.MsgContent msgContent = msg.getMsgContent();
                //根据消息类型进行解析
                switch (msgType) {
                    case MSG_TEXT:
                        message.setText(msgContent.getText());
                        break;
                    case MSG_FACE:
                        message.setFaceIndex(msgContent.getIndex());
                        message.setTitle(msgContent.getData());
                        break;
                    case MSG_LOCATION:
                        message.setTitle(msgContent.getDesc());
                        message.setLat(msgContent.getLatitude());
                        message.setLng(msgContent.getLongitude());
                        break;
                    case MSG_IMAGE:
                        message.setUuid(msgContent.getUUID());
                        message.setExt(ImageFormat.getImageFormat(msgContent.getImageFormat().byteValue()).getImageFormat());
                        List<CallBackDataResult.MsgContent.ImageInfoArray> imageInfoArray = msgContent.getImageInfoArray();
                        if (!CollectionUtils.isEmpty(imageInfoArray)) {
                            Map<Integer, CallBackDataResult.MsgContent.ImageInfoArray> collect = imageInfoArray.stream().collect(Collectors.toMap(x -> x.getType(), x -> x));
                            message.setResourceUrl(collect.get(1).getURL());
                            message.setSize(collect.get(1).getSize());
                            message.setW(collect.get(1).getWidth());
                            message.setH(collect.get(1).getHeight());
                        }
                        break;
                    case MSG_SOUND:
                        message.setSize(msgContent.getSize());
                        message.setDur(msgContent.getSecond());
                        message.setResourceUrl(msgContent.getUrl());
                        message.setUuid(msgContent.getUUID());
                        break;
                    case MSG_CUSTOM:
                        message.setCustom(JSONObject.toJSONString(msgContent));
                        break;
                    case MSG_FILE:
                        message.setResourceUrl(msgContent.getUrl());
                        message.setSize(msgContent.getFileSize());
                        message.setUuid(msgContent.getUUID());
                        message.setTitle(msgContent.getFileName());
                        break;
                    case MSG_OTHER:
                        break;
                    default:
                }

            } else if (!CollectionUtils.isEmpty(msgBody) && msgBody.size() > 1) {
                //多类型消息
                message.setType(MessageType.MSG_OTHER.getKey());
                message.setCustom(JSONObject.toJSONString(msgBody));
            }
        }
        return message;
    }

    private CallBackResult getQuery(HttpServletRequest request) {
        CallBackResult callBack = new CallBackResult();
        String queryString = request.getQueryString();
        if (queryString != null) {
            String[] split = queryString.split("&");
            String[] split1;
            if (split.length > 0) {
                for (String s : split) {
                    split1 = s.split("=");
                    if (split1.length == 2) {
                        if ("CallbackCommand".endsWith(split1[0])) {
                            callBack.setCallBackCommand(split1[1]);
                        } else if ("SdkAppid".endsWith(split1[0])) {
                            callBack.setSdkAppId(split1[1]);
                        }
                    }
                }
                return callBack;
            }
        }
        return null;
    }

    private String getBody(HttpServletRequest request) {
        String body = null;
        InputStream is = null;
        try {
            is = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            body = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }
}
