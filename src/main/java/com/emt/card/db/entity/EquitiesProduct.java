package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


import io.swagger.annotations.ApiModelProperty;



@Entity
@Table(name = "equities_productcard")
public class EquitiesProduct  extends BaseModel implements Serializable{

	/**
	 * 产品权益
	 */
	private static final long serialVersionUID = -840361595468785546L;
	
	@ApiModelProperty("权益名")
	private String equitiesName;			//权益名
	@ApiModelProperty("连接")
	private String url;						//连接
	@ApiModelProperty("权益详情")
	private String equitiesdetails;			//权益详情
	@ApiModelProperty("是否上架")
	private Boolean putaway;				//是否上架
	

	@OneToMany(cascade=CascadeType.ALL)
	@OrderBy("sort")
	private List<EquitiesBookingQuestion> bookingQuestion;//预约信息
	
	
	@ManyToMany
	@OrderBy("createDate")
	private List<Facilitator> facilitators;//服务商列表
	
	public String getEquitiesName() {
		return equitiesName;
	}

	public void setEquitiesName(String equitiesName) {
		this.equitiesName = equitiesName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEquitiesdetails() {
		return equitiesdetails;
	}

	public void setEquitiesdetails(String equitiesdetails) {
		this.equitiesdetails = equitiesdetails;
	}

	public Boolean getPutaway() {
		return putaway;
	}

	public void setPutaway(Boolean putaway) {
		this.putaway = putaway;
	}

	public List<EquitiesBookingQuestion> getBookingQuestion() {
		return bookingQuestion;
	}

	public void setBookingQuestion(List<EquitiesBookingQuestion> bookingQuestion) {
		this.bookingQuestion = bookingQuestion;
	}

	public List<Facilitator> getFacilitators() {
		return facilitators;
	}

	public void setFacilitators(List<Facilitator> facilitators) {
		this.facilitators = facilitators;
	}

}
