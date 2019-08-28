package com.xuanrui.common.config;

import com.xuanrui.common.TLSSigAPIv2;
import com.xuanrui.common.constant.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description: 服务请求路径
 * @Author: gdc
 * @Date: 2019-08-23 08:59
 **/
@Service
public class RequestUrl implements CommandLineRunner {
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TLSSigAPIv2 tlsSigAPIv2;
    /**
     * 管理员签名过期时间 半年 180*24*60*60
     */
    private static final long ADMIN_SIG_EXPIRE = 15552000;

    private static String adminUserSig = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUrl.class);

    public String getServiceUrl(ServiceName serviceName) {
        String url = "";
        switch (serviceName) {
            //账号
            case ACCOUNT_IMPORT:
                url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import";
                break;
            case ACCOUNT_IMPORT_BATCH:
                url = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import";
                break;
            case INFO_GET:
                url = "https://console.tim.qq.com/v4/profile/portrait_get";
                break;
            case INFO_SET:
                url = "https://console.tim.qq.com/v4/profile/portrait_set";
                break;
            case USER_STATE:
                url = "https://console.tim.qq.com/v4/openim/querystate";
                break;

            //消息发送
            case MSG_SEND:
                url = "https://console.tim.qq.com/v4/openim/sendmsg";
                break;
            case MSG_SEND_BATCH:
                url = "https://console.tim.qq.com/v4/openim/batchsendmsg";
                break;
            case MSG_IMPORT:
                url = "https://console.tim.qq.com/v4/openim/importmsg";
                break;

            //好友
            case FRIEND_ADD:
                url = "https://console.tim.qq.com/v4/sns/friend_add";
                break;
            case FRIEND_UPDATE:
                url = "https://console.tim.qq.com/v4/sns/friend_update";
                break;
            case FRIEND_DELETE:
                url = "https://console.tim.qq.com/v4/sns/friend_delete";
                break;
            case FRIEND_DELETE_ALL:
                url = "https://console.tim.qq.com/v4/sns/friend_delete_all";
                break;
            case FRIEND_LIST:
                url = "https://console.tim.qq.com/v4/sns/friend_get";
                break;
            case FRIEND_GET:
                url = "https://console.tim.qq.com/v4/sns/friend_get_list";
                break;
            case FRIEND_CHECK:
                url = "https://console.tim.qq.com/v4/sns/friend_check";
                break;
            default:
                break;
        }
        return url + "?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
    }

    /**
     * 定时更新管理员签名
     * 每月15日23:59分运行。
     */
    @Scheduled(cron = "0 59 23 15 * ?")
    private void updateAdminSig() {
        String sig = tlsSigAPIv2.genSig(myConfig.getAdministrator(), ADMIN_SIG_EXPIRE);
        if (!StringUtils.isEmpty(sig)) {
            adminUserSig = sig;
            LOGGER.info("<<<===定时更新管理员签名成功");
        }
    }


    @Override
    public void run(String... strings) {
        LOGGER.info(">>>===项目启动初始化管理员签名开始");
        String sig = tlsSigAPIv2.genSig(myConfig.getAdministrator(), ADMIN_SIG_EXPIRE);
        if (!StringUtils.isEmpty(sig)) {
            adminUserSig = sig;
            LOGGER.info("<<<===项目启动初始化管理员签名完成");
        }
    }
}
