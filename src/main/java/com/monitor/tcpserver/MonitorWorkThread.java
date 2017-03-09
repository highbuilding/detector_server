package com.monitor.tcpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.monitor.mapper.TbDetectordataMapper;
import com.monitor.pojo.Controllcmd;
import com.monitor.pojo.FeedMsg;
import com.monitor.pojo.Report;
import com.monitor.pojo.TbDetectordata;
import com.monitor.utils.GeTuiUtils;
import com.monitor.utils.JsonUtils;
import com.monitor.utils.LogUtils;

public class MonitorWorkThread extends Thread {
    
    private List<DetectorSocket> detector_list = new ArrayList<>();
    
    private BlockingQueue<Controllcmd> msg_queue = null;
    
    private BlockingQueue<Report> report_queue = null;
    
    private boolean exit = false;
    
    private SqlSessionFactory sqlsessionfac = null;
    
    public MonitorWorkThread(boolean flag, BlockingQueue<Report> report_queue, BlockingQueue<Controllcmd> msg_queue,
        SqlSessionFactory sqlSessionFactory)
        throws IOException {
        exit = flag;
        if (msg_queue != null) {
            this.msg_queue = msg_queue;
        }
        if (report_queue != null) {
            this.report_queue = report_queue;
        }
        sqlsessionfac = sqlSessionFactory;
    }
    
    public static int MAX_DETECTORNUMBER = 32;//最大的电表连接数
    
    public MonitorWorkThread() {
        
    }
    
    public boolean isExit() {
        return exit;
    }
    
    public synchronized void setExit(boolean exit) {
        this.exit = exit;
    }
    
    public void Init() {
        
    }
    
    public synchronized void AddSocket2WorkThread(DetectorSocket socket)
        throws SocketException {
        detector_list.add(socket);
        socket.getM_socket().setKeepAlive(false);
        
    }
    
    public int GetSocketCnt() {
        return detector_list.size();
    }
    
    @SuppressWarnings("unused")
    @Override
    public void run() {
        Socket socket = null;
        List<TbDetectordata> data_list = new ArrayList<>();
        int rows = DetectorProtocol.getDetectorcnt();
        byte[] write_buffer;
        byte[] read_buffer = new byte[200];
        byte[][] data_buffer = new byte[rows][20];
        // 获得服务器端的输入流，从客户端接收信息
        InputStream is = null;
        // 服务器端的输出流，向客户端发送信息
        OutputStream os = null;
        SqlSession session = null;
        boolean update_redis = false;
        while (!exit) {
            int size = detector_list.size();//连接socket的个数
            if (size <= 0) {
                try {
                    Thread.sleep(3000);
                    continue;
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    exit = true;
                }
            }
            
            Controllcmd cmd = msg_queue.poll();//获取来自APP的控制信息
            boolean ishandle = false;
            if (cmd != null) {
                for (DetectorSocket ds : detector_list) {
                    if (cmd.getDevice_mainid().longValue() == ds.getDevice_mainid()) {//需要控制的电表主ID号在这个线程所控制的连接中
                        ishandle = true;
                        LogUtils log = LogUtils.getLogger();
                        log.logger.info(cmd.toString());
                        FeedMsg ob = new FeedMsg();
                        try {
                            os = ds.getM_socket().getOutputStream();
                            is = ds.getM_socket().getInputStream();
                            if (DetectorProtocol.Controll.TransferControllCmd(cmd, is, os)) {
                                ob.setType(0);
                                ob.setMsg("用户操作成功");
                                ob.setDevice_mainid(cmd.getDevice_mainid());
                                ob.setDevice_minorid(cmd.getDevice_minorid());
                                ob.setClientid(cmd.getUser_clientid());
                                GeTuiUtils.TransmissionSingle(cmd.getUser_clientid(), JsonUtils.objectToJson(ob));
                            }
                            //  os.close();这句话是不能调用的否则socket就被关闭了
                            else {
                                ob.setType(0);
                                ob.setMsg("用户操作失败");
                                ob.setDevice_mainid(cmd.getDevice_mainid());
                                ob.setDevice_minorid(cmd.getDevice_minorid());
                                ob.setClientid(cmd.getUser_clientid());
                                GeTuiUtils.TransmissionSingle(cmd.getUser_clientid(), JsonUtils.objectToJson(ob));
                                log.logger.info("用户:" + cmd.getUser_clientid() + "操作失败");
                            }
                        }
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                    }
                    
                }
            }
            
            try {
                if (!ishandle && cmd != null) {//如果没有被处理那么重新放回队列中
                    boolean result = msg_queue.offer(cmd, 2, TimeUnit.SECONDS);
                }
            }
            catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            for (int i = 0; i < size; i++) {//socket个数
                update_redis = false;
                int device_mainid = (int)detector_list.get(i).getDevice_mainid();
                int device_minorid = 0;
                List<Integer> deviceminorid_list = DetectorProtocol.getDeviceMinoridList(device_mainid);//根据主ID获取次设备次ID list
                if (deviceminorid_list == null || deviceminorid_list.size() == 0) {//如果该DTU设备没有连接电表,跳过
                    continue;
                }
                socket = detector_list.get(i).getM_socket();
                try {
                    os = socket.getOutputStream();
                    is = socket.getInputStream();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                for (int index = 0; index < deviceminorid_list.size(); index++) {
                    //电表个数
                    device_minorid = deviceminorid_list.get(index).intValue();
                    for (int j = 0; j < DetectorProtocol.getDetectorcnt(); j++) {//此for循环获取一个电表的所有数据
                        if (DetectorProtocol.SOE.TransferSOECmd(sqlsessionfac, device_mainid, device_minorid, is, os) == false) {
                            deviceminorid_list.remove(deviceminorid_list.get(index));
                            index = 0;
                            update_redis = true;
                            break;
                        }
                        write_buffer =
                            DetectorProtocol.PrepareDetectorTransData((byte)(deviceminorid_list.get(index).byteValue()),
                                DetectorProtocol.READ_TYPE,
                                j);
                        
                        //  os.close();这句话是不能调用的否则socket就被关闭了
                        try {
                            os.write(write_buffer);
                            os.flush();
                            int length;
                            
                            length = is.read(read_buffer);
                            for (int k = 0; k < length; k++) {
                                data_buffer[j][k] = read_buffer[k];
                            }
                        }
                        
                        catch (SocketTimeoutException e) {//电表异常则redis中整个主设备号下的次设备号需要调整
                            update_redis = true;
                            //System.out.println("time out");
                            LogUtils log = LogUtils.getLogger();
                            log.logger.info("主设备:" + device_mainid + "次设备:" + device_minorid + "time out");
                            deviceminorid_list.remove(deviceminorid_list.get(index));
                            index = 0;
                            break;
                            
                        }
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                    }
                    //获取完毕一个电表的数据后处理数据
                    TbDetectordata obj = DetectorProtocol.ParseData(device_mainid, device_minorid, data_buffer);
                    data_list.add(obj);
                    
                }
                if (update_redis) {
                    
                    DetectorProtocol.setDeviceMinoridList(device_mainid, deviceminorid_list);//更新redis上保存的主设备下对应的次设备号
                    if (deviceminorid_list == null || deviceminorid_list.size() == 0) {
                        try {
                            socket.close();
                            detector_list.remove(detector_list.get(i));//去除这个连接
                            LogUtils log = LogUtils.getLogger();
                            log.logger.info("主设备:" + device_mainid + "socket掉线");
                        }
                        
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    update_redis = false;
                    break;
                }
            }
            
            session = sqlsessionfac.openSession();
            try {
                
                TbDetectordataMapper Operation = (TbDetectordataMapper)session.getMapper(TbDetectordataMapper.class);
                for (TbDetectordata obj : data_list) {
                    Operation.insert(obj);
                }
                session.commit();
                
            }
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            finally {
                session.close();
                session = null;
                data_list.clear();
            }
        }
        
        try {
            if (session != null) {//回收资源
                session.close();
                session = null;
                
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
                
            }
            System.out.println("monitor thread resource released");
            for (DetectorSocket ds : detector_list) {
                ds.getM_socket().close();
            }
            
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
