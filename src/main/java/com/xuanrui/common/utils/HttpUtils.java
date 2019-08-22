package com.xuanrui.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description: 发送http请求
 * @Author: gdc
 * @Date: 2019-08-19 10:37
 **/
@Service
public class HttpUtils {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送post请求
     * content-type=application/json
     *
     * @param url     请求路径
     * @param dataMap 请求数据
     * @return 失败信息 成功返回NULL 失败返回第三方错误信息
     */
    public String postForObject(String url, Map<String, Object> dataMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return restTemplate.postForObject(url, new HttpEntity<>(JSONObject.toJSONString(dataMap), headers), String.class);
    }
}
    