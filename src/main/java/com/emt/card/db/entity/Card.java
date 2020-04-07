package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.Transient;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "card")
public class Card extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743261951792837929L;

	/**
	 * 卡状态
     *	0.未生效 （未激活）
     *  1.待绑定 （已激活，需要绑定又未绑定信息）
	 *	2.生效中 （已激活）并在保障期内.
	 *  3.已过期 （已激活）过了保障期限。
	 *	4.已作废   使用完
	 */
	public enum CardStatus{
		invalid("未生效 （未激活）"),
		nobing("待绑定 （已激活，需要绑定又未绑定信息）"),
		effectOf("生效中 （已激活）并在保障期内"),
		expired("已过期 （已激活）过了保障期限"),
		Cancellation("已作废   使用完");

		private String note;
		
		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		private CardStatus(String note) {
			this.note=note;
		}
	}
	
	private String cardNum;  		//卡号
	
	private String password; 		//密码
	
	private CardStatus cardStatus; //卡状态

	private String userId;			//归属权
	
	private String channelId; 		//渠道商
	
	private String orderItemId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="channelId",insertable=false,updatable=false)
	private Channel channel;
	
	@JsonIgnore
	@ManyToOne
	private CardClazz cardClazz;
	
    private String cardName;			//卡名
	
	private Boolean isNeedBinding;		//是否需要绑定

	private Boolean isBindingLinkman;		//是否绑定联系信息
	
	private Integer bindingNum;		//绑定人数
	@Column(columnDefinition="text")
	private String customizeArr;		//自定义填写项目 数组
	
	private String productDetails;		//产品详情
	
	/*时间点*/	
	private Date activateDate;//激活时间

	private Date endActivateDate;//最后激活时间
	
	private Date endBingDate;//最后绑定人员信息时间
	
	private Date endUseDate;//最后使用时间
	
	@JsonIgnore
	@ManyToMany
	@OrderBy("createDate")
	private List<CardPeriod> periods;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	@OrderBy("createDate DESC")
	@JoinColumn(name="cardId")
	private List<CardEquities> cardEquities;
	
	@OneToMany(mappedBy="cardId")
	@OrderBy("createDate DESC")
	private List<CardBindin> cardBindin;
	
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}
	public CardStatus getCardStatus() {
		return cardStatus;
	}
	public int getCardStatusNum() {
		if(cardStatus!=null) {
		return cardStatus.ordinal();
		}
		return 0;
	}
	public String getCardStatusName() {
		if(cardStatus!=null) {
		return cardStatus.getNote();
		}
		return null;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public CardClazz getCardClazz() {
		return cardClazz;
	}

	public void setCardClazz(CardClazz cardClazz) {
		this.cardClazz = cardClazz;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
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
	public JSONArray getCustomizeArray() {
		if(customizeArr!=null)
			try {
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


	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public Date getEndActivateDate() {
		return endActivateDate;
	}

	public void setEndActivateDate(Date endActivateDate) {
		this.endActivateDate = endActivateDate;
	}

	public Date getEndBingDate() {
		return endBingDate;
	}

	public void setEndBingDate(Date endBingDate) {
		this.endBingDate = endBingDate;
	}

	public Date getEndUseDate() {
		return endUseDate;
	}

	public void setEndUseDate(Date endUseDate) {
		this.endUseDate = endUseDate;
	}

	public List<CardBindin> getCardBindin() {
		return cardBindin;
	}

	public void setCardBindin(List<CardBindin> cardBindin) {
		this.cardBindin = cardBindin;
	}

	public List<CardPeriod> getPeriods() {
		return periods;
	}

	public void setPeriods(List<CardPeriod> periods) {
		this.periods = periods;
	}

	public List<CardEquities> getCardEquities() {
		return cardEquities;
	}

	public void setCardEquities(List<CardEquities> cardEquities) {
		this.cardEquities = cardEquities;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	@Transient
	public String getChannelName() {
		if(channel!=null)
		return channel.getChannelName();
		return null;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	
}
