package com.emt.card.db.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 卡绑定
 * @author Administrator
 *
 */
@Entity
@Table(name = "card_bindin")
public class CardBindin  extends BaseModel implements Serializable{
	
	
	private static final long serialVersionUID = 3743261957896546525L;

	private String phone;		//电话
	private String name;		//姓名
	
	@Type(type="text")  
	private String massageArray;//信息键值对
	
	private String cardId;		//卡id
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="cardId",insertable=false,updatable=false)
	private Card card;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}

	public String getMassageArray() {
		return massageArray;
	}
	public JSONArray getMassages() {
		if(massageArray!=null)
			try {
		return JSONArray.parseArray(massageArray);
			}catch (Exception e) {}
		return null;
	}
	public void setMassageArray(String massageArray) {
		this.massageArray = massageArray;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}


	
	
	
	

}
