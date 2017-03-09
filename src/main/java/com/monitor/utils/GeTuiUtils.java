package com.monitor.utils;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GeTuiUtils {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static final String appId = "LfFRqrmFfE6R4EylRJGnZ1";
    
    private static final String appKey = "sTflModDyx6MOQGXjNICl1";
    
    private static final String masterSecret = "JqaXrZ7PSq5mQV4M0EEt58";
    
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    
    private static IGtPush push = null;
    
    public static synchronized String TransmissionSingle(String cid, String msg_json) {//发送单独的推送消息
        if (push == null) {
            push = new IGtPush(host, appKey, masterSecret);
        }
        
        TransmissionTemplate template = transmission(msg_json);
        //NotificationTemplate template = notificationTemplateDemo();
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        }
        catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            
            return ("200:" + ret.getResponse().toString());
        }
        else {
            return "500:";
        }
    }
    
    public static String TransmissionList(List<String> list_cid, String msg_json) {//发送透传消息到一个列表中
        if (push == null) {
            push = new IGtPush(host, appKey, masterSecret);
        }
        
        TransmissionTemplate template = transmission(msg_json);
        //NotificationTemplate template = notificationTemplateDemo();
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        List<Target> targets = new ArrayList<>();
        for (String cid : list_cid) {
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            targets.add(target);
        }
        
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            // taskId用于在推送时去查找对应的message
            String taskId = push.getContentId(message);
            ret = push.pushMessageToList(taskId, targets);
        }
        catch (RequestException e) {
            e.printStackTrace();
            // ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            
            return ("200:" + ret.getResponse().toString());
        }
        else {
            return "500";
        }
    }
    
    //    private LinkTemplate linkTemplateDemo() {  //发送一段打开网址的应用
    //        LinkTemplate template = new LinkTemplate();
    //        // 设置APPID与APPKEY
    //        template.setAppId(appId);
    //        template.setAppkey(appKey);
    //        // 设置通知栏标题与内容
    //        template.setTitle("请输入通知栏标题");
    //        template.setText("请输入通知栏内容");
    //        // 配置通知栏图标
    //        template.setLogo("icon.png");
    //        // 配置通知栏网络图标，填写图标URL地址
    //        template.setLogoUrl("");
    //        // 设置通知是否响铃，震动，或者可清除
    //        template.setIsRing(true);
    //        template.setIsVibrate(true);
    //        template.setIsClearable(true);
    //        // 设置打开的网址地址
    //        template.setUrl("http://www.baidu.com");
    //        return template;
    //    }
    
    private static TransmissionTemplate transmission(String msg_json) {//发送透传消息
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(msg_json);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
    
    //    public NotificationTemplate notificationTemplateDemo() { //透传+消息栏通知
    //        NotificationTemplate template = new NotificationTemplate();
    //        // 设置APPID与APPKEY
    //        template.setAppId(appId);
    //        template.setAppkey(appKey);
    //        // 设置通知栏标题与内容
    //        template.setTitle("请输入通知栏标题");
    //        template.setText("请输入通知栏内容");
    //        // 配置通知栏图标
    //        template.setLogo("icon.png");
    //        // 配置通知栏网络图标
    //        template.setLogoUrl("");
    //        // 设置通知是否响铃，震动，或者可清除
    //        template.setIsRing(true);
    //        template.setIsVibrate(true);
    //        template.setIsClearable(true);
    //        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
    //        template.setTransmissionType(1);
    //        template.setTransmissionContent("请输入您要透传的内容");
    //        // 设置定时展示时间
    //        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
    //        return template;
    //    }
}
