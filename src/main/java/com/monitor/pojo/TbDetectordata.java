package com.monitor.pojo;

import java.util.Date;

public class TbDetectordata {
    private Long id;
    
    private Integer deviceMainid;
    
    private Integer deviceMinorid;
    
    private Float current;
    
    private Float voltage;
    
    private Float temperature;
    
    private Float frequency;
    
    private Float apparentPower;
    
    private Date created;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getDeviceMainid() {
        return deviceMainid;
    }
    
    public void setDeviceMainid(Integer deviceMainid) {
        this.deviceMainid = deviceMainid;
    }
    
    public Integer getDeviceMinorid() {
        return deviceMinorid;
    }
    
    public void setDeviceMinorid(Integer deviceMinorid) {
        this.deviceMinorid = deviceMinorid;
    }
    
    public Float getCurrent() {
        return current;
    }
    
    public void setCurrent(Float current) {
        this.current = current;
    }
    
    public Float getVoltage() {
        return voltage;
    }
    
    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }
    
    public Float getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
    
    public Float getFrequency() {
        return frequency;
    }
    
    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }
    
    public Float getApparentPower() {
        return apparentPower;
    }
    
    public void setApparentPower(Float apparentPower) {
        this.apparentPower = apparentPower;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }
}