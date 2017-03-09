package com.monitor.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtils {
    
    public static final String url = "jdbc:mysql://127.0.0.1:3306/monitor";
    
    public static final String name = "com.mysql.jdbc.Driver";
    
    public static final String user = "root";
    
    public static final String password = "xiangrong@1993";
    
    public PreparedStatement pst = null;
    
    private static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(name); //classLoader,加载对应驱动
            conn = (Connection)DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    //  private Long id;
    //    
    //    private Long device_mainid;
    //    
    //    private Long device_minorid;
    //    
    //    private float current;
    //    
    //    private float voltage;
    //    
    //    private float apparent_power;
    //    public static int insert_Detector(TbDetectorData obj) {
    //        Connection conn = getConn();
    //        int i = 0;
    //        String sql =
    //            "insert into tb_detectordata (device_mainid,device_minorid,current,voltage,created) values(?,?,?,?,?)";
    //        PreparedStatement pstmt;
    //        try {
    //            pstmt = (PreparedStatement)conn.prepareStatement(sql);
    //            pstmt.setLong(1, obj.getDevice_mainid());
    //            pstmt.setLong(2, obj.getDevice_minorid());
    //            pstmt.setFloat(3, obj.getCurrent());
    //            pstmt.setFloat(4, obj.getVoltage());
    //            pstmt.setTimestamp(6, new Timestamp(new java.util.Date().getTime()));
    //            i = pstmt.executeUpdate();
    //            pstmt.close();
    //            conn.close();
    //        }
    //        catch (SQLException e) {
    //            e.printStackTrace();
    //        }
    //        return i;
    //    }
    
}
