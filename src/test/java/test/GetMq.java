package test;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class GetMq {

	public static void main(String[] args) throws JMSException {
		//创建工程
		ActiveMQConnectionFactory mqf=new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
		//获取连接
		Connection connection=mqf.createConnection();
		//开启连接
		connection.start();
		//用连接获取会话对象
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//会话创建消息队对列名称
		Topic queue=session.createTopic("测试MQ");
		//Queue queue=session.createQueue("队列测试mq");
		//创建消费对象
		MessageConsumer consumer=session.createConsumer(queue);
		
		//接受信息
		consumer.setMessageListener(new MessageListener() {
			//收信息
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage) message;
				try {
					System.err.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});

	}
}
