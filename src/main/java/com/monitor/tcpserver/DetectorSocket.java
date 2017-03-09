package com.monitor.tcpserver;

import java.net.Socket;

public class DetectorSocket {
    
    private Socket m_socket;
    
    private long device_mainid;
    
    public DetectorSocket() {
        
    }
    
    public Socket getM_socket() {
        return m_socket;
    }
    
    public void setM_socket(Socket m_socket) {
        this.m_socket = m_socket;
    }
    
    public long getDevice_mainid() {
        return device_mainid;
    }
    
    public void setDevice_mainid(long device_mainid) {
        this.device_mainid = device_mainid;
    }
    
}
