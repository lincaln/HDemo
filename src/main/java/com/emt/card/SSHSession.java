package com.emt.card;



import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHSession {

 public static void main(String[] args) {
	 go("39.106.35.118", pwd, 3306, 3306);
	 go("39.106.35.118", pwd, 6379, 6379);
	 //go("192.168.1.203", "emt888888", 6379, 6379);
	 //go("47.107.98.122",pwd, 3306, 3306);
	 //go("47.107.98.122",pwd, 6379, 6379);

}
private static final String pwd="Vv665599";//84268250!qaz

 public static void go(String ip,String pwd,int formPort,int toPort) {
     try {
         JSch jsch = new JSch();
         Session session = jsch.getSession("root", ip, 22);
         session.setPassword(pwd);
         session.setConfig("StrictHostKeyChecking", "no");
         session.connect();
         System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
        int assinged_port = session.setPortForwardingL(
        	   "127.0.0.1",toPort,
     		   "127.0.0.1", formPort);//端口映射 转发 
        System.out.println(session.getHost()+":" + formPort+"="+assinged_port);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 

}
