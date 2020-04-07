package com.emt.card.controller.req;


import org.springframework.beans.BeanUtils;

import com.emt.card.base.BaseReq;
import com.emt.card.db.entity.Facilitator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 添加服务商
 * @author Administrator
 *
 */
@ApiModel
public class SaveFacilitator extends BaseReq {


	private static final long serialVersionUID = 3936799651464787965L;
	

	@ApiModelProperty("id")
	private String id;		//id
	@ApiModelProperty("公司名")
	private String companyName;		//公司名
	@ApiModelProperty("公司简称")
    private String companyBrief;	//公司简称
	
	@ApiModelProperty("商户名称")
    private String merchantName;	//商户简称
	
	@ApiModelProperty("公司电话")
    private String companyPhone;	//公司电话
	
	@ApiModelProperty("地址")
    private String	address;	//地址
	
	@ApiModelProperty("公司邮箱")
	private String	companyMail;
    
	@ApiModelProperty("公司logo图片")
    private String	logoUrl;			//公司logo图片
	
	@ApiModelProperty("营业执照图片")
    private String	businessLicense;	//营业执照图片
	@ApiModelProperty("公司简介")
    private String	intro;				//公司简介

	
	public Facilitator toFacilitator() {
		Facilitator f=new Facilitator();
		BeanUtils.copyProperties(this, f);
		return f;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyBrief() {
		return companyBrief;
	}
	public void setCompanyBrief(String companyBrief) {
		this.companyBrief = companyBrief;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getCompanyMail() {
		return companyMail;
	}

	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}



	
	
	
}
