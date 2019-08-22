package com.xuanrui.common.config;

import com.xuanrui.tencentim.common.TLSSigAPIv2;
import com.xuanrui.tencentim.common.constant.ServiceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ServiceNameURL {
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TLSSigAPIv2 tlsSigAPIv2;
    /**
     * 过期时间 60*24*60*60
     */
    private static long expire = 5184000;

    private static String adminUserSig = "eJw1jt0KgjAYQN9lt4V8bjZS6CLojyghC8nuRlvyZTPdlgjRuydal4fDgfMmp93RU22FRpGIw7jn\n" + "RhkSEeoBGdjKQlQVShL5AQANOIRsMChV6fCGfSCkxhKtM8I9zT-FvDMpG1kd5*ukXoX7pi2Uq3V6\n" + "lxC-Lradn5PDY7ndLKYsY9fZL3SouyN-wjkFnzL2*QJmfDPw";

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

    @Scheduled(cron = "0/5 * * * * ? ")//每5秒执行一次
 //   @Scheduled(cron = "0 10 0 1 * ?")
    private void updateAdminSig() {
        String sig = tlsSigAPIv2.genSig(myConfig.getAdministrator(), expire);
        if (!StringUtils.isEmpty(sig)) {
            adminUserSig = sig;
        }
    }


}
