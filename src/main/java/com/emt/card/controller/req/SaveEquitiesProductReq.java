package com.emt.card.controller.req;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.base.BaseReq;
import com.emt.card.db.entity.EquitiesBookingQuestion;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.Facilitator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel
public class SaveEquitiesProductReq extends BaseReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5375886543507486020L;

	private static final String bqFormat="[{title:'stirng'(标题),sort:int(排序),isRequired:boolean(是否必填),type:int(0.文本框,1.密码,2.数字,3.单选,4.多选,5.图片,6.日期),option:array(选项数组),description:'string'(描述)}] ";
	@ApiModelProperty(value="id")
	private String id;			
	@ApiModelProperty(value="权益名",required=true)
    private String equitiesName;			//权益名
	@ApiModelProperty(value="连接")
	private String url;						//连接
	@ApiModelProperty(value="权益详情")
	private String equitiesdetails;			//权益详情
	@ApiModelProperty(value="是否上架")
	private Boolean putaway=true;
	@ApiModelProperty(value="服务商数组字符串 [{id:'id1'},{id:'id2'}]",required=true)
	private String facilitatorArray;
	@ApiModelProperty(value="预约问题数组字符串\r\n"+bqFormat,required=true)
	private String bookingQuestionArray;
	
	
	public EquitiesProduct toEquitiesProduct() {
		EquitiesProduct equitiesProduct=new EquitiesProduct();
		BeanUtils.copyProperties(this, equitiesProduct);
		if(bookingQuestionArray!=null) {
		List<EquitiesBookingQuestion> bqlist=JSONArray.parseArray(
				bookingQuestionArray, EquitiesBookingQuestion.class);
		equitiesProduct.setBookingQuestion(bqlist);
		}
		if(facilitatorArray!=null) {
		List<Facilitator> flist=
				JSONArray.parseArray(facilitatorArray, Facilitator.class);		
		equitiesProduct.setFacilitators(flist);
		}
		return equitiesProduct;
	}
	
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
	public String getFacilitatorArray() {
		return facilitatorArray;
	}
	public void setFacilitatorArray(String facilitatorArray) {
		this.facilitatorArray = facilitatorArray;
	}
	public String getBookingQuestionArray() {
		return bookingQuestionArray;
	}
	public void setBookingQuestionArray(String bookingQuestionArray) {
		this.bookingQuestionArray = bookingQuestionArray;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
