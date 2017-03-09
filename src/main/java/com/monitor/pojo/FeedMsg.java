package com.monitor.pojo;

/*
 * 服务器推送给客户端的消息格式对应的类
 * 
 */
public class FeedMsg {
    Integer device_mainid; //设备主id号
    
    Integer device_minorid;
    
    Integer type;//0代表控制返回消息  1代表错误消息
    
    String clientid;
    
    String msg;//
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
    
    public Integer getDevice_mainid() {
        return device_mainid;
    }
    
    public void setDevice_mainid(Integer device_mainid) {
        this.device_mainid = device_mainid;
    }
    
    public Integer getDevice_minorid() {
        return device_minorid;
    }
    
    public void setDevice_minorid(Integer device_minorid) {
        this.device_minorid = device_minorid;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "device_mainid" + device_mainid + ";" + "device_minorid" + device_minorid + ";" + "msg:" + msg;
        
    }
}
