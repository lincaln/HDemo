package com.emt.card.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.emt.card.db.entity.Facilitator.AuditStatus;

import io.swagger.annotations.ApiModelProperty;

/**
 * 服务商审核
 */
@Entity
@Table(name = "facilitator_audit")
public class FacilitatorAudit  extends BaseModel implements Serializable{


	private static final long serialVersionUID = -1011877271236051408L;
	@ApiModelProperty("审核状态")
	private AuditStatus auditStatus;	//审核状态
	@ApiModelProperty("审核意见")
    private String auditOpinion;		//审核意见
	@ApiModelProperty("审核用户")
    private String userId;				//审核用户
	@ApiModelProperty("是否已读")
    private Boolean isRead=false;		//是否已读
	@ApiModelProperty("服务商ID")
    private String facilitatorId;		//服务商ID
    
    
	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFacilitatorId() {
		return facilitatorId;
	}

	public void setFacilitatorId(String facilitatorId) {
		this.facilitatorId = facilitatorId;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
    
    
}
