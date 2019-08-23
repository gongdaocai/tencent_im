package com.xuanrui.dao;

import com.xuanrui.model.request.Message;
import com.xuanrui.model.request.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 数据迁移
 * @Author: gdc
 * @Date: 2019-08-19 09:51
 **/
@Mapper
public interface MessageImportDao {


    List<UserAccount> listUserInfo();

    void saveErrorMessage(@Param("errorList") List<Long> errorList);

    void saveSuccessMessage(@Param("successList") List<Long> successList);

    void saveErrorUser(@Param("errorList") List<String> errorList);

    void saveSuccessUser(@Param("successList") List<String> successList);

    List<Message> listMessage();
}