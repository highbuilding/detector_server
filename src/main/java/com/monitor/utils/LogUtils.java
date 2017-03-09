package com.monitor.utils;

import org.apache.log4j.Logger;

public class LogUtils {
    
    //Logger实例  
    public Logger logger = null;
    
    //将Log类封装为单例模式  
    private static LogUtils log = null;
    
    //构造函数，用于初始化Logger配置需要的属性  
    private LogUtils() {
        // System.out.println("hello");
        //获得当前目录路径     
        // String filePath = this.getClass().getResource("/").getPath();
        //System.out.println("current path:" + filePath);
        //找到log4j.properties配置文件所在的目录(已经创建好)     
        //filePath = filePath.substring(1).replace("bin", "src");
        //logger所需的配置文件路径     
        //PropertyConfigurator.configure(filePath + "log4j.properties");
        //PropertyConfigurator.configure("../log4j.properties");
        //获得日志类logger的实例     
        logger = Logger.getLogger(this.getClass());
        
    }
    
    public static LogUtils getLogger() {
        if (log != null)
            return log;
        else
            return new LogUtils();
    }
    
}
