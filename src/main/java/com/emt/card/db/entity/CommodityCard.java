package com.emt.card.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Commodity_Card")
public class CommodityCard  extends BaseModel implements Serializable{
	
	/**
	 * 商品 卡 关联表
	 */
	private static final long serialVersionUID = -840361595468785546L;
	
	
	private String commodityId;			//商品ID
	private String cardId;				//卡ID

	
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	
	
	

}
