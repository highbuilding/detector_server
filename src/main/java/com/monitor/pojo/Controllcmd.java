package com.monitor.pojo;

/**
 * @author where1993
 * 用户控制命令
 */
public class Controllcmd {
    Integer device_mainid; //设备主id号
    
    Integer device_minorid;
    
    Integer device_cmdins;//控制命令
    
    String user_clientid;//个推的客户端id号，唯一
    
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
    
    public Integer getDevice_cmdins() {
        return device_cmdins;
    }
    
    public void setDevice_cmdins(Integer device_cmdins) {
        this.device_cmdins = device_cmdins;
    }
    
    public String getUser_clientid() {
        return user_clientid;
    }
    
    public void setUser_clientid(String user_clientid) {
        this.user_clientid = user_clientid;
    }
    
    @Override
    public String toString() {
        return ("user_clientid" + user_clientid + "device_mainid:" + device_mainid + " " + "device_minorid:"
            + device_minorid + "device_cmdins:" + device_cmdins);
    }
    
}
