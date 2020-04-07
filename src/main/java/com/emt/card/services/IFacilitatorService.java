package com.emt.card.services;

import java.util.List;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.FacilitatorAudit;
import com.emt.card.db.entity.SysUser;

public interface IFacilitatorService {


	/**
	 * 服务商申请，修改
	 * @param facilitator
	 * @return
	 */
	Facilitator saveFacilitator(Facilitator facilitator);
	
	/**
	 * 查询服务商信息分页
	 * @param companyName
	 * @param auditStatus
	 * @param page
	 * @return
	 */
	Pagination<Facilitator> getFacilitatorPage(String facilitatorId,String companyName,Integer auditStatus, PageReq page);
	
	/**
	 * 查询服务商详情
	 * @param id
	 * @return
	 */
	Facilitator getFacilitator(String id);
	
	/**
	 * 禁用服务商
	 * @param id
	 */
	void banFacilitator(String id);
	
	/**
	 * 服务商审核通知添加 修改
	 * @param facilitatorAudit
	 * @return
	 */
	FacilitatorAudit saveFacilitatorAudit(String facilitatorId,FacilitatorAudit facilitatorAudit);
	

	/**
	 * 获取服务商审核通知
	 * @param clientId
	 * @return
	 */
	List<FacilitatorAudit> getFacilitatorAuditList(String clientId);
	
	/**
	 * 获取选择服务商用户
	 * @param clientId
	 * @return
	 */
	List<SysUser> getFacilitatorBank(String clientId);
	
}
