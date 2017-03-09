package com.monitor.pojo;

import java.util.Date;

public class TbErrorreport {
    private Long id;

    private Integer deviceMainid;

    private Integer deviceMinorid;

    private Integer deviceSoe;

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

    public Integer getDeviceSoe() {
        return deviceSoe;
    }

    public void setDeviceSoe(Integer deviceSoe) {
        this.deviceSoe = deviceSoe;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}