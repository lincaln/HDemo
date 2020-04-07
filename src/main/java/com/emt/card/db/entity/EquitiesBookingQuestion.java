package com.emt.card.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "equities_booking_question")
public class EquitiesBookingQuestion extends BaseModel implements Serializable{

	/**
	 * 权益动态组件
	 */
	private static final long serialVersionUID = -5403615954687855553L;
	
	/**
	 * 
	 * @author 青风侠
	 *0.文本框
	 *1.密码
	 *2.数字
	 *3.单选
	 *4.多选
	 *5.图片
	 *6.日期
	 */
	public enum QuestionType{
		text,password,mun,radio,multiple,picture,date
	}
	@ApiModelProperty("标题")
	private String title;		//标题
	@ApiModelProperty("排序")
	private Integer sort;			//排序
	@ApiModelProperty("组件类型")
	private QuestionType questionType;		//组件类型
	@ApiModelProperty("是否必填")
	private Boolean isRequired;		//是否必填
	@ApiModelProperty("选项数素组字符串")
	private String optionArray; //选项数素组字符串
	@ApiModelProperty("描述")
	private String description; //描述
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public void setType(Integer type) {
		this.questionType = QuestionType.values()[type];
	}
	@Transient
	public Integer getType() {
		if(this.questionType!=null) {
			return questionType.ordinal();
		}
		return null;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getOptionArray() {
		return optionArray;
	}
	
	public void setOptionArray(String optionArray) {
		this.optionArray = optionArray;
	}
	
	@Transient
	public JSONArray getOption() {
		try {
			if(optionArray!=null) {
			return JSONArray.parseArray(optionArray);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setOption(JSONArray array) {
		try {
			if(array!=null)
			this.optionArray=array.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
