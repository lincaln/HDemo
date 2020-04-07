package com.emt.card.controller.req;


import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;
import com.emt.card.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GetCardPageReq extends PageReq{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7996810851737161555L;

	@ApiModelProperty(value="卡类型id")
	private String cardClazzId;
	
	@ApiModelProperty(value="卡号")
	private String cardNum;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value="开始时间,yyyy-MM-dd")
	private Date biginDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value="结束时间,yyyy-MM-dd")
	private Date endDate;

	@ApiModelProperty(value="渠道商名称")
	private String channelName;

	@ApiModelProperty(value="用户Id")
	private String userId;
	
	@ApiModelProperty(value="状态:\r\n"+
			"0.未生效 （未激活）\r\n" + 
			"1.待绑定 （已激活，需要绑定又未绑定信息）\r\n" + 
			"2.生效中 （已激活）并在保障期内.\r\n" + 
			"3.已过期 （已激活）过了保障期限。\r\n" + 
			"4.已作废   使用完")
	private Integer stauts;
	
	public String getCardClazzId() {
		return cardClazzId;
	}

	public void setCardClazzId(String cardClazzId) {
		this.cardClazzId = cardClazzId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Date getBiginDate() {
		return biginDate;
	}

	public void setBiginDate(Date biginDate) {
		this.biginDate = biginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
