package com.emt.card.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.db.entity.BaseModel;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "facilitator_order")
public class FacilitatorOrder extends BaseModel implements Serializable{


	/**
	 * 服务商订单
	 */
	private static final long serialVersionUID = -540361335468798546L;
	
	public enum OrderStatus{
		待使用,已使用,已取消;
	}
	public enum OrderType{
		后台订单,预约订单,核销订单
	}
	
	@ApiModelProperty("订单号")
	private String orderNo;		//订单号
	@ApiModelProperty("卡Id")
	private String cardId;		//id
	@ApiModelProperty("卡号")
	private String cardNum;		//卡号
	@ApiModelProperty("权益Id")
	private String equitiesId;		//权益id
			//权益名
	@ApiModelProperty("下单人账号")
	private String userId;		//下单人账号
	@ApiModelProperty("下单人姓名")
	private String userName;		//下单人姓名
	@ApiModelProperty("下单人电话")
	private String userPhone;		//下单人电话
	@ApiModelProperty("订单状态")
	private OrderStatus orderStatus;		//订单状态
	@ApiModelProperty("服务商备注")
	private String SerivceProviderRemark;		//服务商备注
	@ApiModelProperty(value="预约信息,[{title:'问题标题',type:'QuestionType字段',value:'回答值'}]")
	@Type(type="text") 
	private String bookingMsgJsonArray;			//预约信息
	@ApiModelProperty("服务次数")
	private Integer serviceTimes;			//服务次数
	@ApiModelProperty("服务商Id")
	private String facilitatorId;		//服务商ID
	@ApiModelProperty("订单类型")
	private OrderType orderType;
	@ApiModelProperty("卡绑定人Id")
	private String cardBindinId;
	
	@ApiModelProperty("派单状态")
	@Transient
	private String sendType;		//派单状态
	@ApiModelProperty("权益名")
	@Transient
	private String equitiesName;
	
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="facilitatorId",insertable=false,updatable=false)
	private Facilitator facilitator;			//服务商
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="equitiesId",insertable=false,updatable=false)
	private EquitiesProduct equities;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Facilitator getFacilitator() {
		return facilitator;
	}

	public void setFacilitator(Facilitator facilitator) {
		this.facilitator = facilitator;
	}



	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSerivceProviderRemark() {
		return SerivceProviderRemark;
	}

	public void setSerivceProviderRemark(String serivceProviderRemark) {
		SerivceProviderRemark = serivceProviderRemark;
	}

	public Integer getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(Integer serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	public String getBookingMsgJsonArray() {
		return bookingMsgJsonArray;
	}

	public void setBookingMsgJsonArray(String bookingMsgJsonArray) {
		this.bookingMsgJsonArray = bookingMsgJsonArray;
	}

	@Transient
	public JSONArray getBookingMsg() {
		if(bookingMsgJsonArray!=null) {
			return JSONArray.parseArray(bookingMsgJsonArray);
		}
		return null;
	}

	public String getFacilitatorId() {
		return facilitatorId;
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

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getEquitiesName() {
		if(equities!=null)
			equitiesName=equities.getEquitiesName();
		return equitiesName;
	}

	public void setEquitiesName(String equitiesName) {
		this.equitiesName = equitiesName;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public EquitiesProduct getEquities() {
		return equities;
	}

	public void setEquities(EquitiesProduct equities) {
		this.equities = equities;
	}




	



    
}
