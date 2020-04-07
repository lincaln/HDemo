package com.emt.card.controller.req;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.base.BaseReq;
import com.emt.card.db.entity.CardClazz;
import com.emt.card.db.entity.CardEquities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel
public class SaveCardClazzReq extends BaseReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936799651464759588L;
	@ApiModelProperty(value="id")
	private String id;
	@ApiModelProperty(value="卡名")
    private String cardName;		//卡名
	@ApiModelProperty(value="是否需要激活")
	private Boolean isNeedActivate;		//是否需要激活
	@ApiModelProperty(value="是否需要绑定")
	private Boolean isNeedBinding;		//是否需要绑定
	@ApiModelProperty(value="是否绑定联系信息")
	private Boolean isBindingLinkman;		//是否绑定联系信息
	@ApiModelProperty(value="绑定人数")
	private Integer bindingNum;		//绑定人数
	@ApiModelProperty(value="自定义问题数组[{title:'标题1',value:''},{title:'标题2',value:''}....]")
	private String customizeArr;		//自定义填写项 数组
	@ApiModelProperty(value="产品详情")
	private String productDetails;		//产品详情
	@ApiModelProperty(value="权益列表字符串[{equitiesId:'string'(权益id),affirmNumber:num(核销数量),facilitatorIds:['string'(服务商id),'string'(服务商id)]}]")
	private String cardEquitiesArray;
	
	public CardClazz toCardCalzz() {
		CardClazz cc=new CardClazz();
		BeanUtils.copyProperties(this, cc);
		List<CardEquities> list=
				JSONArray.parseArray(
						cardEquitiesArray, CardEquities.class);
		cc.setCardEquities(list);
		return cc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Boolean getIsNeedActivate() {
		return isNeedActivate;
	}
	public void setIsNeedActivate(Boolean isNeedActivate) {
		this.isNeedActivate = isNeedActivate;
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
	public Integer getBindingNum() {
		return bindingNum;
	}
	public void setBindingNum(Integer bindingNum) {
		this.bindingNum = bindingNum;
	}
	public String getCustomizeArr() {
		return customizeArr;
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
	public String getCardEquitiesArray() {
		return cardEquitiesArray;
	}
	public void setCardEquitiesArray(String cardEquitiesArray) {
		this.cardEquitiesArray = cardEquitiesArray;
	}
	
	
	
}
