package com.xuanrui.service;

import com.xuanrui.tencentim.common.TLSSigAPIv2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户
 * @Author: gdc
 * @Date: 2019-08-19 16:00
 **/
@Service
public class UserSigService {
    @Autowired
    private TLSSigAPIv2 tlsSigAPIv2;
    /**
     * 签名过期时间
     * 时间单位：秒
     * 默认时间：7 x 24 x 60 x 60 = 604800 = 7 天
     */
    public static final int EXPIRE_TIME = 31536000;

    /**
     * 获取签名
     *
     * @param phone
     * @return
     */
    public String genSig(String phone) {
        return tlsSigAPIv2.genSig(phone, EXPIRE_TIME);
    }
}
    