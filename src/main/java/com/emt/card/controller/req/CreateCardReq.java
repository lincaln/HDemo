package com.emt.card.controller.req;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.base.BaseReq;
import com.emt.card.db.entity.CardPeriod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CreateCardReq extends BaseReq{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4441010878352954613L;

	@ApiModelProperty(value="卡类型id",required=true)
	private String cardClazzId;
	
	@ApiModelProperty(value="卡数量",required=true)
	private Integer cardCount;
	
	@ApiModelProperty(value="服务商ID",required=true)
	private String channelId;
	
	@ApiModelProperty(value="时间限制数组字符串\r\n "
			+ "[{type:1(1为时间段),bigin:string(开始点:创建create/激活activate/绑定bing),end:string(结束点:激活activate/绑定bing/作废cancel,结束必须是开始点的后面值),quantum:int(时间段),quantumUnit:string(时间单位:年/月/日},{type:2(2位固定日期),end:string(结束点:激活activate/绑定bing/作废cancel),periodDate:string(日期yyyy-MM-dd)}] ")
	private String cardPeriodArray;
	
	private List<CardPeriod> cardPeriod;

	public List<CardPeriod> toCardPeriodList() {
		cardPeriod=new ArrayList<CardPeriod>();
		cardPeriod=JSONArray.parseArray(cardPeriodArray, CardPeriod.class);
		return cardPeriod;
	}
	public String getCardClazzId() {
		return cardClazzId;
	}

	public void setCardClazzId(String cardClazzId) {
		this.cardClazzId = cardClazzId;
	}

	public Integer getCardCount() {
		return cardCount;
	}

	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCardPeriodArray() {
		return cardPeriodArray;
	}

	public void setCardPeriodArray(String cardPeriodArray) {
		this.cardPeriodArray = cardPeriodArray;
	}


	public void setCardPeriod(List<CardPeriod> cardPeriod) {
		this.cardPeriod = cardPeriod;
	}
	
	
}
