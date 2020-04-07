package com.emt.card.services.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.dao.SearchFilter;
import com.emt.card.dao.SearchFilter.MyOperator;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.Facilitator.AuditStatus;
import com.emt.card.db.entity.FacilitatorAudit;
import com.emt.card.db.entity.Facilitator_;
import com.emt.card.db.entity.SysUser;
import com.emt.card.db.repository.FacilitatorRepository;
import com.emt.card.services.IFacilitatorService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.StringUtil;


@Service
public class FacilitatorServiceImpl extends BaseSerivce implements IFacilitatorService {
	
	@Autowired
	private FacilitatorRepository facilitatorRepository;

	/**
	 * 服务商申请，修改
	 * @param facilitator
	 * @return
	 */
	@Override
	public Facilitator saveFacilitator(Facilitator facilitator) {
		facilitator =baseDao.save(facilitator);
		
//		SysUser user = baseDao.find(clientId.toString(), SysUser.class);
//		user.setFacilitator(facilitator);
//		user = baseDao.save(user);
		return facilitator;
	}

	/**
	 * 查询服务商信息分页
	 * @param companyName
	 * @param auditStatus
	 * @param page
	 * @return
	 */
	@Override
	public Pagination<Facilitator> getFacilitatorPage(String facilitatorId,String companyName, Integer auditStatus, PageReq page) {
		
		/*String sql="SELECT f.*,(SELECT fa.audit_opinion FROM `facilitator_audit`  fa WHERE f.id=fa.facilitator_id "
				+ "ORDER BY fa.create_date DESC LIMIT 1) AS audit_opinion FROM `facilitator` f ";
		String countSql="SELECT COUNT(1) FROM `facilitator` where 1=1 ";
		
		if(StringUtil.isNotEmpty(facilitatorId)) {
			sql +=" and facilitator_id LIKE '%"+facilitatorId+"%' ";
			countSql +=" and facilitator_id LIKE '%"+facilitatorId+"%' ";
		}
		if(StringUtil.isNotEmpty(companyName)) {
			sql +=" and company_name LIKE '%"+companyName+"%' ";
			countSql +=" and company_name LIKE '%"+companyName+"%' ";
		}
		if(auditStatus != null) {
			sql +=" and audit_status = "+auditStatus+" ";
			countSql +=" and audit_status = "+auditStatus+" ";
		}
		Pagination<Facilitator> p=new Pagination<Facilitator>(
				page.getIndex(), 20, "f.create_date", true,Facilitator.class);
		Pagination<Facilitator> pagination = baseDao.forPagination(p,sql);		
		pagination.setTotal(baseDao.forOne(countSql, Long.class));*/
		
//		Facilitator facilitator=new Facilitator();
//		if(StringUtil.isNotEmpty(facilitatorId)) {
//			facilitator.setId(facilitatorId);
//		}
//		if(StringUtil.isNotEmpty(companyName)) {
//			facilitator.setCompanyName(companyName);
//		}
//		if(auditStatus != null) {
//			facilitator.setAuditStatus(AuditStatus.values()[auditStatus]);
//		}
//		facilitator.setState(null);
//		Page<Facilitator> p=baseDao.findPage(
//				page.getIndex(),
//				page.getSize(),
//				new String[] {"createDate"},
//				true,
//				facilitator);
//		
//		Pagination<Facilitator> pagination=new Pagination<Facilitator>(
//				p.getNumber(), p.getSize(), p.getTotalElements(), p.getContent());
//		return pagination;
		Set<SearchFilter> filters=new HashSet<SearchFilter>();
		
		Facilitator facilitator=new Facilitator();
		if(StringUtil.isNotEmpty(facilitatorId)) {
			filters.add(new SearchFilter(
					Facilitator_.ID,MyOperator.LIKE, facilitatorId));
		}
		if(StringUtil.isNotEmpty(companyName)) {
			filters.add(new SearchFilter(
					Facilitator_.COMPANY_NAME,MyOperator.LIKE, companyName));
		}
		if(auditStatus != null) {
			//facilitator.setAuditStatus(AuditStatus.values()[auditStatus]);
			filters.add(new SearchFilter(
					Facilitator_.AUDIT_STATUS, 
					MyOperator.EQ,AuditStatus.values()[auditStatus]));
		}
		facilitator.setState(null);
		
		Page<Facilitator> pages=baseDao.findPage(
				page.getIndex(), page.getSize(),
				new String[] {"createDate"}, true,
				filters, Facilitator.class);
		
		return toPagination(pages);
	}
	
	/**
	 * 查询服务商详情
	 * @param id
	 * @return
	 */
	@Override
	public Facilitator getFacilitator(String id) {
		Optional<Facilitator> facilitator =facilitatorRepository.findById(id);
		return facilitator.get();
	}

	/**
	 * 禁用服务商/取消禁用
	 * @param id
	 */
	@Override
	public void banFacilitator(String id) {
		boolean state=false;
		Facilitator f = baseDao.find(id, Facilitator.class);
		if(f.getState()==false)
			state=true;
		String sql ="UPDATE  `facilitator`  SET `modified_date` = NOW(), `state` =  "+state
				+ " WHERE `id` = '" + id +"' ";
		
		baseDao.update(sql);
	}

	/**
	 * 服务商审核通知添加 修改
	 * @param facilitatorAudit
	 * @return
	 */
	@Override
	public FacilitatorAudit saveFacilitatorAudit(String facilitatorId, FacilitatorAudit facilitatorAudit) {

		//修改服务商审核状态
		String sql ="UPDATE  `facilitator`  SET `modified_date` = NOW(), "
				+ "`audit_status` = " +facilitatorAudit.getAuditStatus().ordinal()+" "
				+ "WHERE `id` = '" + facilitatorId +"' ";
			baseDao.update(sql);
		
		//facilitatorAudit.getAuditStatus()
		//添加审核记录
		FacilitatorAudit fa = baseDao.save(facilitatorAudit);
		return fa;
	}

	/**
	 * 获取服务商审核通知
	 * @param clientId
	 * @return
	 */
	@Override
	public List<FacilitatorAudit> getFacilitatorAuditList(String clientId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取选择服务商用户
	 * @param clientId
	 * @return
	 */
    @Override
    public List<SysUser> getFacilitatorBank(String clientId) {
    	List<SysUser> userList=null;
    	
    	if(StringUtil.isNotNull(clientId)){
    		userList=baseDao.getList(
    				"SELECT * FROM sys_user WHERE id = "+clientId,SysUser.class);
    	}else {
    		userList=baseDao.getList(
    				"SELECT * FROM sys_user LEFT JOIN `role` ON sys_user.`role_id` = role.`id` "
    				+ "WHERE role.`role_type` != 0 AND ISNULL(sys_user.`facilitator_id`)",SysUser.class);
    	}
    	return userList;
    }


	
	
}