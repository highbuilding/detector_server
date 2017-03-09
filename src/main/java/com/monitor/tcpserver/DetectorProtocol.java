package com.monitor.tcpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.monitor.mapper.TbDetectorMapper;
import com.monitor.mapper.TbErrorreportMapper;
import com.monitor.mapper.TbGetuiclientidMapper;
import com.monitor.pojo.Controllcmd;
import com.monitor.pojo.FeedMsg;
import com.monitor.pojo.TbDetector;
import com.monitor.pojo.TbDetectorExample;
import com.monitor.pojo.TbDetectordata;
import com.monitor.pojo.TbErrorreport;
import com.monitor.pojo.TbErrorreportExample;
import com.monitor.pojo.TbErrorreportExample.Criteria;
import com.monitor.pojo.TbGetuiclientid;
import com.monitor.pojo.TbGetuiclientidExample;
import com.monitor.utils.GeTuiUtils;
import com.monitor.utils.JsonUtils;
import com.monitor.utils.LogUtils;
import com.sso.jedis.dao.JedisClient;
import com.sso.jedis.dao.JedisClientSingle;

/**
 * @author where1993
 * 与电表的通信协议
 */
//数据格式: 地址码 功能码 数据区 校验码
public class DetectorProtocol {
    
    private static List<DetectorBean> list_bean = new ArrayList<>();
    
    private static JedisClient jediscli = new JedisClientSingle();
    
    //private static byte[] data = new byte[6];
    
    private static byte[] result = new byte[8];
    
    static final public Byte READ_TYPE = 0x03;//
    
    static final public Byte WRITE_TYPE = 0x06;//
    
    static final public int DEVICE_OFFLINE = 0;
    
    static final public int DEVICE_ONELINE = 1;
    
    static private byte[] crc16(byte[] data_array, int effective_cnt) {//CRC校验
        byte[] crc_data = {0, 0};
        long wcrc = 0xffff;//预置16位crc寄存器，初值全部为1
        byte temp;//定义中间变量
        int j = 0;//定义计数
        for (int cnt = 0; cnt < effective_cnt; cnt++)//循环计算每个数据
        {
            temp = (byte)(data_array[cnt] & 0x00ff);//将八位数据与crc寄存器亦或
            wcrc ^= temp;//将数据存入crc寄存器
            for (j = 0; j < 8; j++)//循环计算数据的
            {
                if ((wcrc & 0x0001) == 1)//判断右移出的是不是1，如果是1则与多项式进行异或。
                {
                    wcrc >>= 1;//先将数据右移一位
                    wcrc ^= 0XA001;//与上面的多项式进行异或
                }
                else//如果不是1，则直接移出
                {
                    wcrc >>= 1;//直接移出
                }
            }
        }
        crc_data[0] = (byte)wcrc;//crc的低八位
        crc_data[1] = (byte)(wcrc >> 8);//crc的高八位
        return crc_data;
    }
    
    static public int getDetectorcnt() {//获得数据的条数
        return list_bean.size();
    }
    
    public class DetectorBean {
        private String name;
        
        private Integer address;
        
        private Integer length;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Integer getAddress() {
            return address;
        }
        
        public void setAddress(Integer address) {
            this.address = address;
        }
        
        public Integer getLength() {
            return length;
        }
        
        public void setLength(Integer length) {
            this.length = length;
        }
        
    }
    
    public static void InitValue() {
        DetectorProtocol detectorprotocol = new DetectorProtocol();
        DetectorBean voltage = detectorprotocol.new DetectorBean();
        voltage.setName("voltage");
        voltage.setAddress(0x0100);
        voltage.setLength(1);
        list_bean.add(voltage);
        
        DetectorBean current = detectorprotocol.new DetectorBean();
        current.setName("current");
        current.setAddress(0x0101);
        current.setLength(1);
        list_bean.add(current);
        
        DetectorBean frequency = detectorprotocol.new DetectorBean();
        frequency.setName("frequency");
        frequency.setAddress(0x0106);
        frequency.setLength(1);
        list_bean.add(frequency);
        
        DetectorBean cputemp = detectorprotocol.new DetectorBean();
        cputemp.setName("cputemp");
        cputemp.setAddress(0x0107);
        cputemp.setLength(1);
        list_bean.add(cputemp);
        
        //      DetectorBean activepower = detectorprotocol.new DetectorBean();
        //        activepower.setName("activepower");
        //        activepower.setAddress(0x0102);
        //        activepower.setLength(2);
        //        list_bean.add(activepower);
        //        
        //        DetectorBean reactivepower = detectorprotocol.new DetectorBean();
        //        reactivepower.setName("reactivepower");
        //        reactivepower.setAddress(0x0103);
        //        reactivepower.setLength(2);
        //        list_bean.add(reactivepower);
        //        
        //        DetectorBean apparentpower = detectorprotocol.new DetectorBean();
        //        apparentpower.setName("apparentpower");
        //        apparentpower.setAddress(0x0104);
        //        apparentpower.setLength(2);
        //        list_bean.add(apparentpower);
        //        
        //        DetectorBean powerfactor = detectorprotocol.new DetectorBean();
        //        powerfactor.setName("powerfactor");
        //        powerfactor.setAddress(0x0105);
        //        powerfactor.setLength(2);
        //        list_bean.add(powerfactor);
        
    }
    
    //数据格式: 地址码 功能码 数据区 校验码
    //01 03 01 00 00 02 c5 f7
    public static byte[] PrepareDetectorTransData(Byte address, Byte type, int index) {//准备电表传输数据
        byte[] temp = new byte[6];
        DetectorBean bean = list_bean.get(index);
        int bean_address = bean.getAddress().intValue();//寄存器地址
        int bean_length = bean.getLength().intValue();
        temp[0] = address;
        temp[1] = type;
        temp[2] = (byte)(bean_address >> 8);
        temp[3] = (byte)(bean_address);
        temp[4] = (byte)(bean_length >> 8);
        temp[5] = (byte)(bean_length);
        
        byte[] crc_data = DetectorProtocol.crc16(temp, 6);
        
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i];
        }
        for (int i = 0; i < crc_data.length; i++) {
            result[6 + i] = crc_data[i];
        }
        return result;
    }
    
    public static TbDetectordata ParseData(int main_id, int minor_id, byte[][] array_data) {
        TbDetectordata obj = new TbDetectordata();
        short temp = 0;
        for (int cnt = 0; cnt < getDetectorcnt(); cnt++) {
            String name = list_bean.get(cnt).getName();
            
            if (name.equals("voltage")) {
                byte[] voltage_array = array_data[cnt];
                minor_id = voltage_array[0];
                temp = (short)((voltage_array[3] & 0xff) << 8 | (voltage_array[4] & 0xff));
                
                float voltage = temp;
                voltage /= 100;
                obj.setVoltage(voltage);
            }
            else if (name.equals("current")) {
                byte[] current_array = array_data[cnt];
                temp = 0;
                temp = (short)((current_array[3] & 0xff) << 8 | (current_array[4] & 0xff));
                float current = temp;
                current /= 100;
                obj.setCurrent(current);
            }
            else if (name.equals("cputemp")) {
                byte[] cputemp_array = array_data[cnt];
                temp = 0;
                temp = (short)((cputemp_array[3] & 0xff) << 8 | (cputemp_array[4] & 0xff));
                float cputemp = temp;
                cputemp /= 100;
                obj.setTemperature(cputemp);
                
            }
            else if (name.equals("frequency")) {
                byte[] cputemp_array = array_data[cnt];
                temp = 0;
                temp = (short)((cputemp_array[3] & 0xff) << 8 | (cputemp_array[4] & 0xff));
                float frequency = temp;
                frequency /= 100;
                obj.setFrequency(frequency);
                
            }
            
        }
        obj.setDeviceMainid(main_id);
        obj.setDeviceMinorid(minor_id);
        obj.setCreated(new Date());
        //System.out.println(obj.toString());
        return obj;
    }
    
    public static List<Integer> getDeviceMinoridList(long device_mainid) {
        String[] temp_array = null;
        List<Integer> minorid_list = new ArrayList<>();
        String id = String.format("%08d", device_mainid);
        String key = "DEVICE_MAINID:" + id;
        String value = jediscli.get(key);
        if (value == null || value.equals("")) {
            return null;
        }
        
        key = "DEVICE_FACTMAINID:" + id;
        
        String value_fac = jediscli.get(key);
        if (value_fac != null && (value_fac.equals("") == false)) {
            temp_array = value_fac.split(",");
            for (String temp : temp_array) {
                int a = Integer.parseInt(temp);
                minorid_list.add(a);
            }
        }
        else {
            if (value != null && (value.equals("") == false)) {
                temp_array = value.split(",");
                for (String temp : temp_array) {
                    int a = Integer.parseInt(temp);
                    minorid_list.add(a);
                }
            }
        }
        return minorid_list;
    }
    
    public static void setDeviceMinoridList(long device_mainid, List<Integer> device_minoridlist) {
        
        String id = String.format("%08d", device_mainid);
        String key = "DEVICE_FACTMAINID:" + id;
        String value = "";
        if (device_minoridlist == null || device_minoridlist.size() == 0) {//
            jediscli.set(key, "");
            return;
        }
        for (int i = 0; i < device_minoridlist.size(); i++) {
            String minorid = String.format("%03d", device_minoridlist.get(i));
            value += (minorid + ",");
            
        }
        jediscli.set(key, value);
    }
    
    public static void synDeviceMinoridList(long device_mainid) {
        
        String id = String.format("%08d", device_mainid);
        String key = "DEVICE_MAINID:" + id;
        // System.out.println(key);
        String value = jediscli.get(key);
        key = "DEVICE_FACTMAINID:" + id;
        jediscli.set(key, value);
        
    }
    
    public static class Controll {
        private static ModbusIns modbusins = null;
        
        public static void InitControllValue() {
            Controll controll = new Controll();
            modbusins = controll.new ModbusIns();
            modbusins.ins_content = 0x5555;
            modbusins.ins_type = 6;
            
            // inner = outer.getInner();
            
        }
        
        //static private ModbusIns modbusins;
        
        private class ModbusIns {
            public int modbus_address;//地址
            
            public int ins_type;//指令类型
            
            public int ins_address;//指令地址
            
            public int ins_content;//指令内容
            
            public int toModBusStream(byte[] ins) {
                
                byte[] crc = {0, 0};
                ins[0] = (byte)modbus_address;
                ins[1] = (byte)(ins_type & 0x00ff);
                ins[2] = (byte)((ins_address >> 8) & 0xff);
                ins[3] = (byte)(ins_address & 0xff);
                ins[4] = (byte)((ins_content >> 8) & 0xff);
                ins[5] = (byte)(ins_content & 0xff);
                crc = DetectorProtocol.crc16(ins, 6);
                ins[6] = crc[0];
                ins[7] = crc[1];
                return 8;
                
            }
        }
        
        public static boolean TransferControllCmd(Controllcmd cmd, InputStream is, OutputStream os) {
            Integer device_minorid = cmd.getDevice_minorid();
            Integer controlltype = cmd.getDevice_cmdins();
            byte[] ins = new byte[8];
            byte[] feedback = new byte[32];
            modbusins.modbus_address = device_minorid;
            if (controlltype.equals(1)) {//自检
                modbusins.ins_address = 0x0123;
            }
            else if (controlltype.equals(2)) {//消声
                modbusins.ins_address = 0x0120;
            }
            else if (controlltype.equals(3)) {//复位
                modbusins.ins_address = 0x0121;
            }
            else if (controlltype.equals(4)) {//断电
                modbusins.ins_address = 0x0122;
            }
            int length = modbusins.toModBusStream(ins);
            
            try {
                os.write(ins, 0, length);
                os.flush();
                length = is.read(feedback);
                for (int i = 0; i < length; i++) {
                    if (feedback[i] != ins[i])
                        return false;
                }
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            
            return true;
            
        }
        
    }
    
    //SOE电表错误信息
    public static class SOE {
        //          0x0300  第1条SOE记录
        //          0x0304  第2条SOE记录
        //          0x0308  第3条SOE记录
        //          0x030c  第4条SOE记录
        //          0x0310  第5条SOE记录
        //          0x0314  第6条SOE记录
        private static int baseAddress = 0x300;
        
        private static ModbusIns modbusins = null;
        
        public static void InitSOE() {
            SOE soe = new SOE();
            modbusins = soe.new ModbusIns();
            modbusins.ins_type = 3;
            modbusins.ins_address = baseAddress;
            modbusins.ins_length = 4;
        }
        
        private class ModbusIns {
            public int modbus_address;//地址
            
            public int ins_type;//指令类型
            
            public int ins_address;//指令地址
            
            public int ins_length;//指令内容
            
            public int toModBusStream(byte[] ins) {
                
                byte[] crc = {0, 0};
                ins[0] = (byte)modbus_address;
                ins[1] = (byte)(ins_type & 0x00ff);
                ins[2] = (byte)((ins_address >> 8) & 0xff);
                ins[3] = (byte)(ins_address & 0xff);
                ins[4] = (byte)((ins_length >> 8) & 0xff);
                ins[5] = (byte)(ins_length & 0xff);
                crc = DetectorProtocol.crc16(ins, 6);
                ins[6] = crc[0];
                ins[7] = crc[1];
                return 8;
                
            }
        }
        
        //数据格式: 地址码 功能码 数据区 校验码
        //01 03 01 00 00 02 c5 f7
        public static boolean TransferSOECmd(SqlSessionFactory sqlsessionfac, Integer device_mainid,
            Integer device_minorid, InputStream is, OutputStream os) {
            TbErrorreport report = new TbErrorreport();
            List<String> error_list = new ArrayList<>();
            byte[] ins = new byte[8];
            byte[] feedback = new byte[32];
            int[] result = new int[8];
            modbusins.modbus_address = device_minorid;
            SqlSession session = sqlsessionfac.openSession();
            List<String> list_cid = new ArrayList<>();
            TbDetectorMapper DetectorOperation = (TbDetectorMapper)session.getMapper(TbDetectorMapper.class);
            TbDetectorExample Detectorexample = new TbDetectorExample();
            com.monitor.pojo.TbDetectorExample.Criteria Detectorcriteria = Detectorexample.createCriteria();
            Detectorcriteria.andDeviceMainidEqualTo(device_mainid);
            List<TbDetector> DetectorList = DetectorOperation.selectByExample(Detectorexample);
            if (DetectorList.size() > 0) {
                String company = DetectorList.get(0).getCompany();
                TbGetuiclientidMapper GeTuiOperation =
                    (TbGetuiclientidMapper)session.getMapper(TbGetuiclientidMapper.class);
                TbGetuiclientidExample GeTuiexample = new TbGetuiclientidExample();
                com.monitor.pojo.TbGetuiclientidExample.Criteria GeTuicriteria = GeTuiexample.createCriteria();
                GeTuicriteria.andParentEqualTo(company);
                List<TbGetuiclientid> GeTuiList = GeTuiOperation.selectByExample(GeTuiexample);
                for (TbGetuiclientid id : GeTuiList) {
                    list_cid.add(id.getClientId());
                }
            }
            
            for (int cnt = 0; cnt < 8; cnt++) {
                TbErrorreportExample example = new TbErrorreportExample();
                Criteria criteria = example.createCriteria();
                modbusins.ins_address = baseAddress + (cnt * 4);
                int length = modbusins.toModBusStream(ins);
                
                try {
                    os.write(ins, 0, length);
                    os.flush();
                    length = is.read(feedback);
                    for (int i = 0; i < result.length; i++) {
                        result[i] = feedback[3 + i] & 0xff;
                        
                    }
                    //Date或者String转化为时间戳  
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time =
                        String.format("%d-%d-%d %d:%d:%d",
                            2000 + result[2],
                            result[3],
                            result[4],
                            result[5],
                            result[6],
                            result[7]);
                    Date date = format.parse(time);
                    //System.out.println(time);
                    criteria.andCreatedEqualTo(date);
                    criteria.andDeviceMainidEqualTo(device_mainid);
                    criteria.andDeviceMinoridEqualTo(device_minorid);
                    criteria.andDeviceSoeEqualTo(result[1]);
                    try {
                        TbErrorreportMapper Operation =
                            (TbErrorreportMapper)session.getMapper(TbErrorreportMapper.class);
                        List<TbErrorreport> List = Operation.selectByExample(example);
                        if (List == null || List.size() == 0) {//数据库中不存在这条信息
                            report.setCreated(date);
                            report.setDeviceMainid(device_mainid);
                            report.setDeviceMinorid(device_minorid);
                            report.setDeviceSoe(result[1]);
                            Operation.insert(report);
                            //0x80速断保护跳闸
                            //0x81定时限过流保护跳闸
                            //0x82过压保护报警
                            //0x83低压保护报警
                            //0x84装置上电
                            //0x85故障复归
                            if (result[1] == 0x80) {
                                error_list.add("速断保护跳闸  " + time);
                            }
                            else if (result[1] == 0x81) {
                                error_list.add("定时限过流保护跳闸  " + time);
                            }
                            else if (result[1] == 0x82) {
                                error_list.add("速断保护跳闸  " + time);
                            }
                            else if (result[1] == 0x83) {
                                error_list.add("过压保护报警  " + time);
                            }
                            else if (result[1] == 0x84) {
                                error_list.add("低压保护报警  " + time);
                            }
                            else if (result[1] == 0x85) {
                                error_list.add("装置上电  " + time);
                            }
                            else if (result[1] == 0x86) {
                                error_list.add("故障复归  " + time);
                            }
                            
                        }
                        else {//如果这条数据存在，说明SOE信息全部都在数据库中了
                              //System.out.println(List.size());
                            break;
                        }
                    }
                    
                    catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        
                    }
                    
                }
                catch (SocketTimeoutException e) {
                    LogUtils log = LogUtils.getLogger();
                    log.logger.info("主设备:" + device_mainid + "次设备:" + device_minorid + "soe read time out");
                    session.close();
                    session = null;
                    return false;
                }
                
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
                catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
            
            session.commit();
            session.close();
            session = null;
            for (String errormsg : error_list) {
                FeedMsg msg = new FeedMsg();
                msg.setDevice_mainid(device_mainid);
                msg.setDevice_minorid(device_minorid);
                msg.setType(1);
                msg.setMsg(errormsg);
                msg.setClientid("");
                LogUtils log = LogUtils.getLogger();
                log.logger.info(GeTuiUtils.TransmissionList(list_cid, JsonUtils.objectToJson(msg)));
            }
            
            return true;
        }
    }
}
