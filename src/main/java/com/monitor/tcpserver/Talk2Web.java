package com.monitor.tcpserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.ibatis.session.SqlSessionFactory;

import com.monitor.activemq.Activemq_Receiver;
import com.monitor.pojo.Controllcmd;
import com.monitor.pojo.Report;
import com.monitor.utils.GeTuiUtils;
import com.monitor.utils.HttpClientUtils;
import com.monitor.utils.JsonUtils;

public class Talk2Web implements MessageListener {
    private String url = "http://115.29.140.63:7123/user/reportEvent";
    
    private boolean exit = false;
    
    private Activemq_Receiver activemq_receiver = null;
    
    private BlockingQueue<Controllcmd> msg_queue = null;
    
    private BlockingQueue<Report> report_queue = null;
    
    private Integer device_mainid;
    
    private SqlSessionFactory sqlSessFac = null;
    
    public void setExit(boolean exit) {
        this.exit = exit;
    }
    
    @Override
    public void onMessage(Message m) {//消息处理函数
        TextMessage textMsg = (TextMessage)m;
        try {
            String json = textMsg.getText();
            Controllcmd controllcmd = JsonUtils.jsonToPojo(json, Controllcmd.class);
            boolean result = msg_queue.offer(controllcmd, 2, TimeUnit.SECONDS);
            if (result == false) {//在一段时间内没能添加入消息队列
                GeTuiUtils.TransmissionSingle(controllcmd.getUser_clientid(), "系统繁忙，操作失败");
            }
            
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Talk2Web(Integer id, final BlockingQueue<Report> report_queue, BlockingQueue<Controllcmd> msg_queue,
        SqlSessionFactory sqlSessionFactory) {
        activemq_receiver = new Activemq_Receiver(id, "controll", Talk2Web.this);//id号 topic名称
        if (activemq_receiver != null) {
            device_mainid = id;
        }
        this.msg_queue = msg_queue;
        this.report_queue = report_queue;
        sqlSessFac = sqlSessionFactory;
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Report> report_list = new ArrayList<>();
                while (!exit) {
                    report_list.clear();
                    if (report_queue.size() > 0) {
                        
                        report_list.add(report_queue.poll());//report中取得SOE消息
                        
                    }
                    else {
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                    if (report_list.size() > 0) {
                        String json = JsonUtils.objectToJson(report_list);
                        System.out.println(json);
                        System.out.println(HttpClientUtils.doPostJson(url, json));
                    }
                }
            }
        });
        background.start();
    }
    
    public Integer getDeviceId() {
        return device_mainid;
    }
    
}
