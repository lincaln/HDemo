package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.emt.card.db.entity.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "facilitator")
public class Facilitator extends BaseModel implements Serializable{

	/**
	 * 服务商
	 */
	private static final long serialVersionUID = -540361598435648895L;
	
	//审核状态 ，待审核，审核通过，审核不通过
	public enum AuditStatus{
		toAudit,auditPass,auditnoPass;
	}
	@ApiModelProperty("公司名")
	private String companyName;		//公司名
	
	@ApiModelProperty("商户名称")
    private String merchantName;	//商户简称
	
	@ApiModelProperty("公司简称")
    private String companyBrief;	//公司简称
	
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
    private String	intro;	//公司简介

	@ApiModelProperty("审核状态")
    private AuditStatus auditStatus;	//审核状态
    
	@Transient
    private String auditOpinion;		//审核意见
    
    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "facilitatorId")
    @OrderBy("createDate DESC")
    private List<FacilitatorAudit> audits;//审核记录
    
    @JsonIgnore
	@OneToMany(mappedBy="facilitator")
	@OrderBy("createDate DESC")
	private List<FacilitatorOrder> facilitatorOrder;//服务订单

    @JsonIgnore
	@ManyToMany(mappedBy="facilitators")
	@OrderBy("createDate")
	private List<EquitiesProduct> equitiesProducts;
	
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


	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCompanyMail() {
		return companyMail;
	}

	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public List<FacilitatorOrder> getFacilitatorOrder() {
		return facilitatorOrder;
	}

	public void setFacilitatorOrder(List<FacilitatorOrder> facilitatorOrder) {
		this.facilitatorOrder = facilitatorOrder;
	}

	public List<FacilitatorAudit> getAudits() {
		return audits;
	}


	public void setAudits(List<FacilitatorAudit> audits) {
		this.audits = audits;
	}

	public List<EquitiesProduct> getEquitiesProducts() {
		return equitiesProducts;
	}

	public void setEquitiesProducts(List<EquitiesProduct> equitiesProducts) {
		this.equitiesProducts = equitiesProducts;
	}
    public String getAuditOpinion() {
    	if(audits!=null&&!audits.isEmpty()) {
    		auditOpinion=audits.get(0).getAuditOpinion();
    	}
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
    
    
    
    
}
