package test;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class SendMq {

	/*@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;*/
	
	public static void main(String[] args) throws JMSException {
		//创建工厂
		ActiveMQConnectionFactory mqf=new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
		//创建连接
		Connection connection=mqf.createConnection();
		//启动连接
		connection.start();
		//创建会话
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//创建目标
		Destination destinatio=session.createTopic("测试MQ");
		//Destination destinatio=session.createQueue("队列测试mq");
		//创建生产者
		MessageProducer messageProducer =session.createProducer(destinatio);
		for (int i = 0; i < 10; i++) {
			//创建信息
			TextMessage tm=session.createTextMessage("test"+i);
			messageProducer.send(tm);
			System.err.println("发消息:"+tm.getText());
		}
		//关闭连接
		connection.close();
	}
}
