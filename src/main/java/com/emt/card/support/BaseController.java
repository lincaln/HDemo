package com.emt.card.support;

import org.springframework.beans.factory.annotation.Autowired;

import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.db.entity.SysUser;
import com.emt.card.services.ICardEquitiesService;

import com.emt.card.services.IChannelService;
import com.emt.card.services.IFacilitatorOrderService;
import com.emt.card.services.IFacilitatorService;
import com.emt.card.services.IUserService;
import com.emt.card.db.entity.Role;
import com.emt.card.services.ISysService;

public class BaseController {

	@Autowired
	protected ICardEquitiesService cardEquitiesService;
	@Autowired
	protected IChannelService channelService;
	@Autowired
	protected IFacilitatorService facilitatorService;
	@Autowired
	protected IUserService userService;
	@Autowired
	protected IFacilitatorOrderService FacilitatorOrderService;
	@Autowired
	protected ISysService sysService;
	
	
	protected RoleType getRoleType(String id) {
		SysUser user=userService.getUser(id);
		if(user!=null&&user.getRole()!=null) {
			return user.getRole().getRoleType();
		}
		return null;
	}
	
	protected boolean isAdmin(String userId) {
  	  SysUser user=userService.getUser(userId);
		return isAdmin(user);	
	}
	
      protected boolean isAdmin(SysUser user) {
		
		if(user!=null&&user.getRole()!=null) {
			if(ISysService.adminRoleId.equals(user.getRole().getId())) {
				return true;
			}
		Role role=user.getRole();
		return role!=null&&RoleType.系统.equals(role.getRoleType());
		}
		return false;
		
	}
}


