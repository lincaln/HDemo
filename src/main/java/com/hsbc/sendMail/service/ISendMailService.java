package com.hsbc.sendMail.service;

import org.springframework.stereotype.Service;

import com.hsbc.sendMail.req.MailReq;

@Service
public interface ISendMailService {

	public void sendMail(MailReq mailReq);
}
