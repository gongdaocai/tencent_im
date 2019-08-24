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
public class ServiceNameURL implements CommandLineRunner {
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TLSSigAPIv2 tlsSigAPIv2;
    /**
     * 管理员签名过期时间 半年 180*24*60*60
     */
    private static final long ADMIN_SIG_EXPIRE = 15552000;

    private static String adminUserSig = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNameURL.class);

    public String getServiceUrl(ServiceName serviceName) {
        switch (serviceName) {
            //账号
            case ACCOUNT_IMPORT:
                return "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case ACCOUNT_IMPORT_BATCH:
                return "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case INFO_GET:
                return "https://console.tim.qq.com/v4/profile/portrait_get?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case INFO_SET:
                return "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case USER_STATE:
                return "https://console.tim.qq.com/v4/openim/querystate?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";

            //消息发送
            case MSG_SEND:
                return "https://console.tim.qq.com/v4/openim/sendmsg?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case MSG_SEND_BATCH:
                return "https://console.tim.qq.com/v4/openim/batchsendmsg?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case MSG_IMPORT:
                return "https://console.tim.qq.com/v4/openim/importmsg?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";

            //好友
            case FRIEND_ADD:
                return "https://console.tim.qq.com/v4/sns/friend_add?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_UPDATE:
                return "https://console.tim.qq.com/v4/sns/friend_update?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_DELETE:
                return "https://console.tim.qq.com/v4/sns/friend_delete?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_DELETE_ALL:
                return "https://console.tim.qq.com/v4/sns/friend_delete_all?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_LIST:
                return "https://console.tim.qq.com/v4/sns/friend_get?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_GET:
                return "https://console.tim.qq.com/v4/sns/friend_get_list?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";
            case FRIEND_CHECK:
                return "https://console.tim.qq.com/v4/sns/friend_check  ?sdkappid=" + myConfig.getAppId() + "&identifier=" + myConfig.getAdministrator() + "&usersig=" + adminUserSig + "&random=99999999&contenttype=json";

            default:
                return "";
        }
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
