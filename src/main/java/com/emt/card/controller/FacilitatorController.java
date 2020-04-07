package com.emt.card.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emt.card.base.BaseReq;
import com.emt.card.base.MyResult;
import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.base.UserHandler;
import com.emt.card.controller.req.SaveFacilitator;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.Facilitator.AuditStatus;
import com.emt.card.db.entity.FacilitatorAudit;
import com.emt.card.db.entity.Registration.RegId;
import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.db.entity.SysUser;
import com.emt.card.support.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="服务商管理",tags ="服务商管理")
@RestController
@RequestMapping(value = "/facilitator")
public class FacilitatorController extends BaseController{
	
	@UserHandler
	@RequestMapping(value = "/saveFacilitator",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "服务商申请",response = Facilitator.class)
	public MyResult<Facilitator> saveFacilitator(PageReq page,SaveFacilitator sFacilitator) {
		
		Facilitator facilitator = sFacilitator.toFacilitator();
		//默认审核状态
		facilitator.setAuditStatus(AuditStatus.toAudit);
		if(isAdmin(page.getClientId())) {
		facilitator=facilitatorService.saveFacilitator(facilitator);
		//String str = sFacilitator.getFacilitatorBank().toString();
		//服务商对应的默认角色
		//sysService.updateUserRole(sFacilitator.getFacilitatorBank().toString(), RegId.facilitator);
		}else {
			facilitator=facilitatorService.saveFacilitator(facilitator);
			//服务商对应的默认角色
			sysService.updateUserRole(page.getClientId(), RegId.facilitator);
		}
		return new MyResult<Facilitator>(facilitator);
	}
	
	@UserHandler
	@RequestMapping(value = "/getFacilitatorPage",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取服务商列表分页",response = Facilitator.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="index",value="当前页",required=true,dataType="int"),
    		@ApiImplicitParam(name="companyName",value="公司名",required=false,dataType="String"),
    		@ApiImplicitParam(name="auditStatus",value="审核状态:0:未审核，1:审核通过，2，审核不通过:",required=false,dataType="int"),
	})
	public MyResult<Pagination<Facilitator>> getFacilitatorPage(PageReq page,String companyName, Integer auditStatus) {
		
		//判断用户角色
		String facilitatorId=null;
		if(!RoleType.系统.equals(getRoleType(page.getClientId()))) {
			SysUser user=userService.getUser(page.getClientId());
			if(user.getFacilitator()!=null) {
			facilitatorId=user.getFacilitator().getId();
			}else {
				return new MyResult<>("用户没有服务商",220);
			}
		}
		Pagination<Facilitator> pagination = facilitatorService.getFacilitatorPage(facilitatorId,companyName, auditStatus, page);

		return new MyResult<Pagination<Facilitator>>(pagination);
	}
	
	@UserHandler
	@RequestMapping(value = "/getFacilitatorId",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取服务商详情",response = Facilitator.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="id",value="服务商id",required=true,dataType="String")
	})
	public MyResult<Facilitator> getFacilitatorId(BaseReq baseReq,String id) {		
		Facilitator pagination = facilitatorService.getFacilitator(id);
		return new MyResult<Facilitator>(pagination);
	}
	
	@UserHandler
	@RequestMapping(value = "/stopFacilitator",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "禁用服务商/取消禁用",response = Facilitator.class)
	@ApiImplicitParams(value = { 
    @ApiImplicitParam(name="id",value="服务商id",required=true,dataType="String")})
	public MyResult<Object> stopFacilitator(BaseReq baseReq,String id) {
		
		facilitatorService.banFacilitator(id);
		
		return new MyResult<>();
	}
	
	@UserHandler
	@RequestMapping(value = "/saveFacilitatorAudit",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "审核服务商",response = FacilitatorAudit.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="facilitatorId",value="服务商id",required=true,dataType="String"),
    		@ApiImplicitParam(name="auditOpinion",value="审核意见",required=false,dataType="String"),
    		@ApiImplicitParam(name="auditStatus",value="审核状态",required=true,dataType="int"),
	})
	public MyResult<FacilitatorAudit> saveFacilitatorAudit(BaseReq req ,String facilitatorId,
			Integer auditStatus,String auditOpinion) {
		FacilitatorAudit facilitatorAudit = new FacilitatorAudit();
		AuditStatus as= AuditStatus.values()[auditStatus];
		facilitatorAudit.setFacilitatorId(facilitatorId);
		facilitatorAudit.setAuditStatus(as);
		facilitatorAudit.setAuditOpinion(auditOpinion);
		facilitatorAudit.setUserId(req.getClientId());
		facilitatorAudit = facilitatorService.saveFacilitatorAudit(facilitatorId,facilitatorAudit);
		return new MyResult<FacilitatorAudit>(facilitatorAudit);
	}

	
	@PostMapping("/getFacilitatorBank")
	@ApiOperation("获取选择服务商用户")
	public List<SysUser> getFacilitatorBank(String clientId) {
		List<SysUser> userList = new ArrayList<SysUser>();
		//判断用户角色
		if(RoleType.系统.equals(getRoleType(clientId))) {
			clientId = null;
		}
		userList = facilitatorService.getFacilitatorBank(clientId);
		return userList;
	}


	

}
