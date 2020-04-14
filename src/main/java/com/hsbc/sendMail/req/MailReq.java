package com.hsbc.sendMail.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MailReq {
	@ApiModelProperty("发送人,多个用','分割")
	private String to;
	@ApiModelProperty("抄送人,多个用','分割")
	private String cc;
	@ApiModelProperty("密送人,多个用','分割")
	private String bcc;
	@ApiModelProperty("标题")
	private String subject;
	@ApiModelProperty("正文")
	private String mainBody;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
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
	
	
}
