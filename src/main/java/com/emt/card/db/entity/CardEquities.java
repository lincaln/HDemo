package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "card_equities")
public class CardEquities extends BaseModel implements Serializable{

	/**
	 * 卡权益
	 */
	private static final long serialVersionUID = -5403615900909099765L;
	private String cardClazzId;//卡类型id
	private String cardId;     //具体卡的id
	private String equitiesId;			//权益id
	private Integer affirmNumber;		//核销次数
	
	
	@ManyToOne
	@JoinColumn(name="equitiesId",insertable=false,updatable=false)
	private EquitiesProduct equitiesProduct;
	
	@JsonIgnore
	@ManyToMany
	private Set<Facilitator> facilitators;
	
	
	public CardEquities() {
		super();
	}



	public CardEquities(String cardId, String equitiesId) {
		super();
		this.cardId = cardId;
		this.equitiesId = equitiesId;
	}



	public CardEquities(String equitiesId, Set<Facilitator> facilitators, Integer affirmNumber) {
		super();
		this.equitiesId = equitiesId;
		this.facilitators = facilitators;
		this.affirmNumber = affirmNumber;
	}



	public String getEquitiesId() {
		return equitiesId;
	}
	public void setEquitiesId(String equitiesId) {
		this.equitiesId = equitiesId;
	}
	public Integer getAffirmNumber() {
		return affirmNumber;
	}
	public void setAffirmNumber(Integer affirmNumber) {
		this.affirmNumber = affirmNumber;
	}
	public String getCardClazzId() {
		return cardClazzId;
	}
	public void setCardClazzId(String cardClazzId) {
		this.cardClazzId = cardClazzId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public EquitiesProduct getEquitiesProduct() {
		return equitiesProduct;
	}
	public void setEquitiesProduct(EquitiesProduct equitiesProduct) {
		this.equitiesProduct = equitiesProduct;
	}



	public Set<Facilitator> getFacilitators() {
		return facilitators;
	}


	public void setFacilitators(Set<Facilitator> facilitators) {
		this.facilitators = facilitators;
	}
	
	public void setFacilitatorIds(String[] facilitatorIds) {
		for (int i = 0; i < facilitatorIds.length; i++) {
			if(facilitators==null) {
				facilitators=new HashSet<Facilitator>();
			}
			Facilitator f=new Facilitator();
			f.setId(facilitatorIds[i]);
			facilitators.add(f);
		};
	}

	
	public JSONArray getFacilitatorIds() {
		if(facilitators!=null) {
			JSONArray array=new JSONArray();
			for (Facilitator facilitator : facilitators) {
				array.add(facilitator.getId());
			}
			return array;
		}
		return null;
	}
	
	public JSONArray getFacilitatorNames() {
		if(facilitators!=null) {
			JSONArray array=new JSONArray();
			for (Facilitator facilitator : facilitators) {
				array.add(facilitator.getCompanyName());
			}
			return array;
		}
		return null;
	}
	
}
