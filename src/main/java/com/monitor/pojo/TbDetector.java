package com.monitor.pojo;

import java.util.Date;

public class TbDetector {
    private Long id;

    private String company;

    private String deviceName;

    private Integer deviceMainid;

    private Integer deviceMinorid;

    private String deviceStatus;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
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

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus == null ? null : deviceStatus.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}