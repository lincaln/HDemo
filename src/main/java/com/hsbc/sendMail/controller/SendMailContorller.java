package com.hsbc.sendMail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emt.card.base.MyResult;
import com.hsbc.sendMail.req.MailReq;
import com.hsbc.sendMail.service.ISendMailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( tags ="发送mail")
@Scope("prototype")
@RestController
@RequestMapping(value = "/sendMail/")
public class SendMailContorller {
	@Autowired
	private ISendMailService sendMailService;
	@ApiOperation(value = "保存卡类型")
	@RequestMapping(value = "send",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<?> send(MailReq req){
		sendMailService.sendMail(req);
		return new MyResult<>();
	}
}
