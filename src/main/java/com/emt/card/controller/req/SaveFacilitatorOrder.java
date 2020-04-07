package com.emt.card.controller.req;


import org.springframework.beans.BeanUtils;

import com.emt.card.base.BaseReq;
import com.emt.card.db.entity.FacilitatorOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 添加服务商
 * @author Administrator
 *
 */
@ApiModel
public class SaveFacilitatorOrder extends BaseReq {


	private static final long serialVersionUID = 3936799651464787965L;
	

	//@ApiModelProperty("订单号")
	//private String orderNo;		//订单号
	@ApiModelProperty("卡id,后台下单没有卡号,预约订单和核销订单必填")
	private String cardId;		//卡Id
	@ApiModelProperty("卡号,后台下单没有卡号,预约订单和核销订单必填")
	private String cardNum;		//卡号
	@ApiModelProperty(value="权益ID",required=true)
	private String equitiesId;		//权益Id
	@ApiModelProperty("下单人账号 预约下单必填")
	private String userId;		//下单人账号
	@ApiModelProperty("下单人姓名 有绑定人用绑定人否则用当前用户")
	private String userName;		//下单人姓名
	@ApiModelProperty("下单人电话 有绑定人用绑定人否则用当前用户")
	private String userPhone;			//下单人电话
	@ApiModelProperty("服务商备注,后台下单才有，非必填")
	private String SerivceProviderRemark;		//服务商备注
	@ApiModelProperty("预约信息[{title:'标题1',value:''},{title:'标题2',value:''}....]")
	private String bookingMsgJsonArray;			//预约信息
	@ApiModelProperty("服务次数,核销订单才需要填")
	private Integer serviceTimes=1;			//服务次数
	@ApiModelProperty(value="订单类型-0:后台订单,1:预约订单,2:核销订单",required=true)
	private int orderType;
	@ApiModelProperty("卡绑定人Id 小程序端下单卡有绑定使用者必填")
	private String cardBindinId;
	
	
	@ApiModelProperty("服务商ID")
	private String facilitatorId;			//服务商ID
	
	public FacilitatorOrder toFacilitatorOrder() {
		FacilitatorOrder fo=new FacilitatorOrder();
		BeanUtils.copyProperties(this, fo);
		return fo;
	}
	


	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getEquitiesId() {
		return equitiesId;
	}

	public void setEquitiesId(String equitiesId) {
		this.equitiesId = equitiesId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getSerivceProviderRemark() {
		return SerivceProviderRemark;
	}

	public void setSerivceProviderRemark(String serivceProviderRemark) {
		SerivceProviderRemark = serivceProviderRemark;
	}

	public String getBookingMsgJsonArray() {
		return bookingMsgJsonArray;
	}


	public void setBookingMsgJsonArray(String bookingMsgJsonArray) {
		this.bookingMsgJsonArray = bookingMsgJsonArray;
	}


	public String getFacilitatorId() {
		return facilitatorId;
	}


	public Integer getOrderType() {
		return orderType;
	}


	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


	public void setFacilitatorId(String facilitatorId) {
		this.facilitatorId = facilitatorId;
	}


	public String getCardBindinId() {
		return cardBindinId;
	}


	public void setCardBindinId(String cardBindinId) {
		this.cardBindinId = cardBindinId;
	}


	public int getServiceTimes() {
		return serviceTimes;
	}


	public void setServiceTimes(int serviceTimes) {
		this.serviceTimes = serviceTimes;
	}


	public String getCardNum() {
		return cardNum;
	}


	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}






	
	
}
