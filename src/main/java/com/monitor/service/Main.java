package com.monitor.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.monitor.pojo.Controllcmd;
import com.monitor.pojo.Report;
import com.monitor.tcpserver.MonitorServer;
import com.monitor.tcpserver.Talk2Web;

public class Main {
    private static BlockingQueue<Controllcmd> msg_queue = new ArrayBlockingQueue<>(1000);//传递控制命令消息由APP发送控制命令给电表
    
    private static BlockingQueue<Report> report_queue = new ArrayBlockingQueue<>(1000);//由电表server采集到soe信息发送
    
    private static MonitorServer server = null;
    
    private static Talk2Web t2w = null;
    
    static private class ExitHandler extends Thread {
        
        public ExitHandler() {
            super("Exit Handler");
        }
        
        @Override
        public void run() {
            System.out.println("Set exit");
            try {
                server.getServer().close();
                for (int i = 0; i < server.workthreads.length; i++) {
                    server.workthreads[i].setExit(true);
                    
                    try {
                        System.out.println("workthread join");
                        server.workthreads[i].join();
                    }
                    catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    
                }
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        }
    }
    
    public static void main(String[] args)
        throws Exception {
        InputStream inputStream_db;
        inputStream_db = Resources.getResourceAsStream("DBConfiguration.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream_db);
        if (sqlSessionFactory != null) {
            t2w = new Talk2Web(1, report_queue, msg_queue, sqlSessionFactory);
            server = new MonitorServer(6868, report_queue, msg_queue, sqlSessionFactory);
            server.StartServer();
        }
        ExitHandler ShutdownHook = new Main.ExitHandler();
        Runtime.getRuntime().addShutdownHook(ShutdownHook);
        
        while (true) {
            Thread.sleep(10000);
            // System.out.println("sleeping");
            
        }
    }
}
