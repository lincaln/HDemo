package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONArray;

@Entity
@Table(name = "card_clazz")
public class CardClazz  extends BaseModel implements Serializable{

	private static final long serialVersionUID = -540361595468788874L;
	

	
	private String cardName;		//卡名
	
	private Boolean isNeedBinding;		//是否需要绑定

	private Boolean isBindingLinkman;		//是否绑定联系信息
	
	private Integer bindingNum;		//绑定人数
	
	@Column(columnDefinition="text")
	private String customizeArr;		//自定义选项 数组
	
	private String productDetails;		//产品详情

	
	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("createDate DESC")
	@JoinColumn(name="cardClazzId")
	private List<CardEquities> cardEquities;


	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}



	public Integer getBindingNum() {
		return bindingNum;
	}

	public void setBindingNum(Integer bindingNum) {
		this.bindingNum = bindingNum;
	}

	public String getCustomizeArr() {
		return customizeArr;
	}

	public JSONArray getCustomizeArray() {
		try {
		if(customizeArr!=null)
		return JSONArray.parseArray(customizeArr);
		}catch (Exception e) {}
		return null;
	}
	
	public void setCustomizeArr(String customizeArr) {
		this.customizeArr = customizeArr;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public List<CardEquities> getCardEquities() {
		return cardEquities;
	}

	public void setCardEquities(List<CardEquities> cardEquities) {
		this.cardEquities = cardEquities;
	}

	public Boolean getIsNeedBinding() {
		return isNeedBinding;
	}

	public void setIsNeedBinding(Boolean isNeedBinding) {
		this.isNeedBinding = isNeedBinding;
	}

	public Boolean getIsBindingLinkman() {
		return isBindingLinkman;
	}

	public void setIsBindingLinkman(Boolean isBindingLinkman) {
		this.isBindingLinkman = isBindingLinkman;
	}

	
	
}
