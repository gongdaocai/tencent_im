package com.xuanrui.dao;

import com.xuanrui.model.dataobject.MessageDO;
import com.xuanrui.model.query.MessageQuery;
import com.xuanrui.model.request.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Description: 保存消息
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@Mapper
public interface MessageDao {

	/**
	 * 保存聊天记录
	 * 
	 * @param MessageDO 消息	
	 * @return 保存结果
	 */
	void saveMessage(MessageDO MessageDO);

	void saveBatchMassage(List<MessageDO> MessageDOs);

	/**
	 * 获取发送的聊天记录
	 * 
	 * @param MessageDO 消息实体
	 * @return List<MessageDO> 聊天记录
	 */
	List<MessageDO> listMessageFrom(MessageQuery MessageDO);

	/**
	 * 获取接收的聊天记录
	 * 
	 * @param MessageDO 消息实体
	 * @return List<MessageDO> 聊天记录
	 */
	List<MessageDO> listMessageTo(MessageQuery MessageDO);


	/**
	 * 获取发送的聊天记录
	 *
	 * @param MessageDO 消息实体
	 * @return List<MessageDO> 聊天记录
	 */
	List<MessageDO> listSysMessageFrom(MessageQuery MessageDO);

	/**
	 * 获取接收的聊天记录
	 *
	 * @param MessageDO 消息实体
	 * @return List<MessageDO> 聊天记录
	 */
	List<MessageDO> listSysMessageTo(MessageQuery MessageDO);

	int sysMessage(MessageQuery MessageDO);
}