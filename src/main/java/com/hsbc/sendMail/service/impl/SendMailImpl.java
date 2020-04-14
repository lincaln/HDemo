package com.hsbc.sendMail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.hsbc.sendMail.db.dao.IBaseDao;
import com.hsbc.sendMail.model.SendMailRequest;
import com.hsbc.sendMail.req.MailReq;
import com.hsbc.sendMail.service.ISendMailService;

public class SendMailImpl implements ISendMailService {

	@Autowired
    private IBaseDao baseDao;
	
	@Override
	public void sendMail(MailReq mailReq) {
		SendMailRequest smr=new SendMailRequest();
		String[] tos=mailReq.getTo().split(",");
		smr.setToArray(JSONArray.toJSONString(tos));
		String[] ccs=mailReq.getCc().split(",");
		smr.setToArray(JSONArray.toJSONString(ccs));
		String[] bccs=mailReq.getBcc().split(",");
		smr.setToArray(JSONArray.toJSONString(bccs));
		smr.setSubject(mailReq.getSubject());
		smr.setMainBody(mailReq.getMainBody());
		smr.setHasSend(false);
		baseDao.insert(smr);
	}

}
