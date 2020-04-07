package com.emt.card.controller.req;


import com.emt.card.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class GetCommodityCardReq extends PageReq{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7996810851737789689L;
	
	private String id;			
	@ApiModelProperty(value="商品ID")
	private String commodityId;			//商品ID
	@ApiModelProperty(value="卡Id")
	private String cardId;				//卡ID
	@ApiModelProperty(value="商品名")
	private String commodityName;			//商品名
	@ApiModelProperty(value="卡名")
	private String cardName;				//卡名

	
	
}
