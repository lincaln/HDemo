package com.hsbc.sendMail.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("send_mail_request")
public class SendMailRequest extends BaseModel{

	private String toArray;
	private String ccArray;
	private String bccArray;
	private String subject;
	private String mainBody;
	private Boolean hasSend;
	public String getToArray() {
		return toArray;
	}
	public void setToArray(String toArray) {
		this.toArray = toArray;
	}
	public String getCcArray() {
		return ccArray;
	}
	public void setCcArray(String ccArray) {
		this.ccArray = ccArray;
	}
	public String getBccArray() {
		return bccArray;
	}
	public void setBccArray(String bccArray) {
		this.bccArray = bccArray;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMainBody() {
		return mainBody;
	}
	public void setMainBody(String mainBody) {
		this.mainBody = mainBody;
	}
	public Boolean getHasSend() {
		return hasSend;
	}
	public void setHasSend(Boolean hasSend) {
		this.hasSend = hasSend;
	}
	
	
}
