package com.monitor.pojo;

public class Report {
    int device_mainid;
    
    int device_minorid;
    
    int device_status;
    
    public Report(int device_mainid, int device_minorid, int device_status) {
        this.device_mainid = device_mainid;
        this.device_minorid = device_minorid;
        this.device_status = device_status;
    }
    
    public int getDevice_mainid() {
        return device_mainid;
    }
    
    public void setDevice_mainid(int device_mainid) {
        this.device_mainid = device_mainid;
    }
    
    public int getDevice_minorid() {
        return device_minorid;
    }
    
    public void setDevice_minorid(int device_minorid) {
        this.device_minorid = device_minorid;
    }
    
    public int getDevice_status() {
        return device_status;
    }
    
    public void setDevice_status(int device_status) {
        this.device_status = device_status;
    }
    
    @Override
    public String toString() {
        return "device_mainid" + device_mainid + ";" + "device_minorid" + device_minorid + ";" + "device_status"
            + device_status;
        
    }
}
