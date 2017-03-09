package com.monitor.tcpserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import org.apache.ibatis.session.SqlSessionFactory;

import com.monitor.pojo.Controllcmd;
import com.monitor.pojo.Report;
import com.monitor.utils.IntByteUtils;
import com.monitor.utils.LogUtils;

public class MonitorServer {
    private boolean bExit = false;
    
    public Thread server_thread;
    
    private ServerSocket server;
    
    public MonitorWorkThread[] workthreads;
    
    private List<DetectorSocket> connection_list;
    
    private int listen_port;
    
    public ServerSocket getServer() {
        return server;
    }
    
    public void setServer(ServerSocket server) {
        this.server = server;
    }
    
    public int getListen_port() {
        return listen_port;
    }
    
    public void setListen_port(int listen_port) {
        this.listen_port = listen_port;
        
    }
    
    public MonitorServer(int port, BlockingQueue<Report> report_queue, BlockingQueue<Controllcmd> msg_queue,
        SqlSessionFactory sqlSessionFactory)
        throws IOException {
        this.listen_port = port;
        server = new ServerSocket(this.listen_port, 10, InetAddress.getByName("115.29.140.63"));
        connection_list = new LinkedList<>();
        workthreads = new MonitorWorkThread[10];
        DetectorProtocol.InitValue();
        DetectorProtocol.Controll.InitControllValue();
        DetectorProtocol.SOE.InitSOE();
        LogUtils log = LogUtils.getLogger();
        log.logger.info("MonitorServer start");
        for (int i = 0; i < workthreads.length; i++) {
            workthreads[i] = new MonitorWorkThread(false, report_queue, msg_queue, sqlSessionFactory);//默认不退出
            workthreads[i].start();
            
        }
        
    }
    
    public synchronized void setExit(boolean exit) {
        this.bExit = exit;
    }
    
    public boolean StartServer() {
        Timer timer = new Timer();
        /**
         * 定时任务，每五分钟执行一次
         * 同步redis中电表的数据
         */
        timer.schedule(new TimerTask() {
            
            public void run() {
                System.out.println("syn redis");
                if (connection_list.size() > 0) {
                    for (DetectorSocket detector : connection_list) {
                        long device_mainid = detector.getDevice_mainid();
                        DetectorProtocol.synDeviceMinoridList(device_mainid);
                    }
                }
            }
        }, 1000 * 60, 1000 * 60 * 5);//5分钟更新一次
        server_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] read_buffer = new byte[200];
                int count = 0;
                //具体实现
                Socket new_socket = null;
                while (!bExit) {
                    try {
                        DetectorSocket detectorconn = new DetectorSocket();
                        new_socket = server.accept();
                        if (new_socket != null) {
                            new_socket.setSoTimeout(10000);
                            InputStream is = new_socket.getInputStream();
                            int length = is.read(read_buffer);
                            long device_mainid = IntByteUtils.byte2int(read_buffer);
                            detectorconn.setDevice_mainid(device_mainid);
                            
                            LogUtils log = LogUtils.getLogger();
                            log.logger.info("devicemain:" + device_mainid + " connected");
                            detectorconn.setM_socket(new_socket);
                            connection_list.add(detectorconn);
                            if (detectorconn != null) {
                                
                                workthreads[count++].AddSocket2WorkThread(detectorconn);
                            }
                            if (count >= workthreads.length) {
                                count = 0;
                            }
                            
                        }
                        
                    }
                    catch (SocketTimeoutException e) {
                        e.printStackTrace();
                    }
                    catch (SocketException e) {
                        e.printStackTrace();
                        return;
                        
                        //e.printStackTrace();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        
        server_thread.start();
        return true;
        
    }
}
