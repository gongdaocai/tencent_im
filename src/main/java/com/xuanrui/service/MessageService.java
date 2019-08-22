package com.xuanrui.service;

import com.xuanrui.common.utils.DateUtil;
import com.xuanrui.common.utils.DateUtil;
import com.xuanrui.dao.MessageDao;
import com.xuanrui.model.dataobject.MessageDO;
import com.xuanrui.model.dataobject.MessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: gdc
 * @Date: 2019-08-19 16:00
 **/
@Service
public class MessageService {

    private MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * 保存消息
     *
     * @param messageDO 消息DO
     */
    public void saveMessage(MessageDO messageDO) {
        messageDO.setGmtCreate(DateUtil.getNowDate());
        messageDao.saveMessage(messageDO);
    }
}
    