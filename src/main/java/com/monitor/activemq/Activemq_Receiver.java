package com.monitor.activemq;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author where1993
 *
 */
public class Activemq_Receiver {
    private static ActiveMQConnectionFactory connectionFactory = null;
    
    // Connection ：JMS 客户端到JMS Provider 的连接
    private Connection connection = null;
    
    // Session： 一个发送或接收消息的线程
    private Session session;
    
    // Destination ：消息的目的地;消息发送给谁.
    // 消费者，消息接收者
    private MessageConsumer consumer;
    
    private Topic topic;
    
    private Integer ClientID;
    
    public Activemq_Receiver(Integer clientid, String topic_name, MessageListener callback) {
        if (connectionFactory == null) {
            connectionFactory = new ActiveMQConnectionFactory("root", "xiangrong@1993", "tcp://localhost:61616");
        }
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            connection.setClientID("consumer" + clientid); //持久订阅需要设置这个。
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            // destination = session.createQueue("FirstQueue");
            
            topic = session.createTopic(topic_name);
            consumer = session.createDurableSubscriber(topic, "consumer" + clientid); //持久订阅
            consumer = session.createConsumer(topic);
            consumer.setMessageListener(callback);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
    public ActiveMQConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
    
    public void setConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    public MessageConsumer getConsumer() {
        return consumer;
    }
    
    public void setConsumer(MessageConsumer consumer) {
        this.consumer = consumer;
    }
    
    public Topic getTopic() {
        return topic;
    }
    
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    
    public Integer getClientID() {
        return ClientID;
    }
    
    public void setClientID(Integer clientID) {
        ClientID = clientID;
    }
    
}