package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * 渠道表
 * @author Administrator
 *
 */
@Entity
@Table(name = "channel")
public class Channel extends BaseModel implements Serializable{
	
	
	private static final long serialVersionUID = -5403654698785547854L;
	
	@ApiModelProperty("渠道名")
	private String channelName;		//渠道名
	

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy="channel")
	@OrderBy("createDate DESC")
	private List<Card> cards;


	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	
}
