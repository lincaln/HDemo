package com.emt.card.controller;



import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.base.BaseReq;
import com.emt.card.base.HosException;
import com.emt.card.base.MyResult;
import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.base.UserHandler;
import com.emt.card.controller.req.AddUserVo;
import com.emt.card.db.entity.*;
import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.support.BaseController;
import com.emt.card.utils.Constants;
import com.emt.card.utils.StringUtil;
import com.emt.card.utils.WebUtil;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;




@Api(value = "系统配置",tags="系统配置")
@Scope("prototype")
@RestController
@RequestMapping(value = "/sys/")
public class SysController extends BaseController {




	@RequestMapping(value = "getAllUrl",method = RequestMethod.POST)
	public MyResult<Set<String>> getAllUrl(HttpServletRequest request) {
		Set<String> uresult = new LinkedHashSet<String>();
		WebApplicationContext wc = (WebApplicationContext)
				request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		RequestMappingHandlerMapping bean = wc.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
		for (RequestMappingInfo rmi : handlerMethods.keySet()) {
			PatternsRequestCondition pc = rmi.getPatternsCondition();
			Set<String> pSet = pc.getPatterns();
			uresult.addAll(pSet);
		}
		MyResult<Set<String>> result=new MyResult<Set<String>>(uresult);
		return result;
	}

	@ApiOperation(value = "获取所有菜单")
	@RequestMapping(value = "getAllMenu",method = RequestMethod.POST)
	@UserHandler(perCheck=false)
	public MyResult<List<MyMenu>> getAllMenu(String clientId) {
		SysUser user = userService.getUser(clientId);
		if(user.getRole()!=null) {
		List<MyMenu> menus = sysService.getAllMenu(user.getRole().getId());
		return new MyResult<List<MyMenu>>(menus);
		}
		return new MyResult<List<MyMenu>>(new ArrayList<>());
	}

	@ApiOperation(value = "角色列表")
	@UserHandler
	@RequestMapping(value = "getRolePage",
			produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	public MyResult<Pagination<Role>> getRolePage(HttpServletRequest request,Integer pageIndex, Integer size) throws Exception{
		//Long companyid=getCompanyId(request);
		Pagination<Role> rp=sysService.getRolePage(pageIndex, size,null);
		return new MyResult<Pagination<Role>>(rp);
	}
	
	@ApiOperation(value = "保存角色")
	@UserHandler
	@RequestMapping(value = "saveRole",method = RequestMethod.POST)
	public MyResult<Role> saveRole(HttpServletRequest request,Role role,String clientId) throws Exception {
		String[] mArray = WebUtil.getStringArrayAttr(request, "menuIds");
		String[] pArray =  WebUtil.getStringArrayAttr(request, "permissionIds");
		//Long companyid=getCompanyId(request);

		//SysUser user=userService.getUser(clientId);

		/*if(role.getId()==null) {
			role.setCompanyid(companyid);
		}else {
			if(!isAdmin(user)&& (companyid==null||companyid.equals(role.getCompanyid()))) {
				throw new HosException("没权处理该数据",Constants.ERROR_CODE.HAD_ERROR);
			}
		}*/
		if(!isAdmin(clientId)) {
			throw new HosException("没权处理该数据",Constants.ERROR_CODE.HAD_ERROR);
		}
		role=sysService.saveRole(role, mArray, pArray);

		return new MyResult<Role>(role);
	}
	
	@ApiOperation(value = "删除角色")
	@UserHandler
	@RequestMapping(value = "delRole",method = RequestMethod.POST)
	public MyResult<Boolean> delRole(String id) throws Exception {
		sysService.delRole(id);
		return new MyResult<Boolean>(true);
	}
	
	@ApiOperation(value = "获取角色类型")
	@UserHandler
	@RequestMapping(value = "getRoleType",method = RequestMethod.POST)
	public MyResult<RoleType[]> getRoleType() throws Exception {
		return new MyResult<Role.RoleType[]>(RoleType.values());
	}
	
	@ApiOperation(value = "获取角色")
	@UserHandler
	@RequestMapping(value = "getRole",method = RequestMethod.POST)
	public MyResult<Role> getRole(String clientId,String id) throws Exception {
		Role role=sysService.getRole(id);
		SysUser user = userService.getUser(clientId);
		//Long companyid=user.getParentMerchant().getId();
		if(!isAdmin(user)) {
			throw new HosException("没权查看该数据",Constants.ERROR_CODE.HAD_ERROR);
		};
		return new MyResult<Role>(role);
	}
	
	@ApiOperation(value = "获取用户可用角色")
	@UserHandler
	@RequestMapping(value = "getRoles",method = RequestMethod.POST)
	public MyResult<List<Role>> getRoles(HttpServletRequest request,String clientId) throws Exception{
		List<Role> roles=sysService.getRoles(clientId);;
		return new MyResult<List<Role>>(roles);
	}
	/*@UserHandler
	@RequestMapping(value = "savePermission",method = RequestMethod.POST)
	public MyResult savePermission(Permission permission) {
		permission= sysService.savePermission(permission);
		result.setData(permission);
		return result;
	}
	@UserHandler
	@RequestMapping(value = "delPermission",method = RequestMethod.POST)
	public MyResult delPermission(String id) {
		Permission permission=sysService.delPermission(id);
		result.setData(permission);
		return result;
	}
	@UserHandler
	@RequestMapping(value = "getPermission",method = RequestMethod.POST)
	public MyResult getPermission(String id) {
		Permission permission=sysService.getPermission(id);
		result.setData(permission);
		return result;
	}
	@UserHandler
	@RequestMapping(value = "getPermissions",
			produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	public String getPermissions(String menuid) {
		List<Permission> permission=sysService.getPermissions(menuid);
		result.setData(permission);
		return result.toString();
	}*/

	@ApiOperation(value = "获取默认角色配置")
	@UserHandler
	@RequestMapping(value = "getAllRegistration",method = RequestMethod.POST)
	public MyResult<List<Registration>> getAllRegistration() {
		List<Registration> list=sysService.getAllRegistration();
		return new MyResult<List<Registration>>(list);
	}

	@ApiOperation(value = "修改默认角色配置")
	@UserHandler
	@RequestMapping(value = "updateRegistration",method = RequestMethod.POST)
	public MyResult<Registration> updateRegistration(Registration registration) {
		registration=sysService.updateRegistration(registration);
		return new MyResult<Registration>(registration);
	}
	
	@ApiOperation(value = "获保存菜单")
	@ApiImplicitParams(value = { 
			@ApiImplicitParam(name="menuList",value="菜单json字符串",dataType="String")})
	@UserHandler
	@RequestMapping(value = "saveMenu",method = RequestMethod.POST)
	public MyResult<Boolean> saveMenu(String menuList){
		List<MyMenu> array=JSONArray.parseArray(menuList,MyMenu.class);
		sysService.saveList(array, null);
		return new MyResult<Boolean>(true);
	}

	@UserHandler
	@ApiOperation(value = "人员列表")
	@RequestMapping(value = "getUserPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<SysUser>> getCardClazzPage(
			PageReq pageReq, SysUser user){
		if(!isAdmin(pageReq.getClientId())) {
			SysUser Client=userService.getUser(pageReq.getClientId());
			user.setFacilitator(Client.getFacilitator());
		}
		Pagination<SysUser> p=pageReq.toPagination(SysUser.class);
		p.setEntity(user);
		if(!StringUtil.isNotEmpty(p.SortType()))
		p.setSortType("createDate");
		p=userService.getUserPage(p);
		return new MyResult<Pagination<SysUser>>(p);	
	}
	
	@UserHandler
	@RequestMapping(value = "/saveUser",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "添加/修改用户",response = SysUser.class)

	public MyResult<SysUser> saveUser(BaseReq Page,AddUserVo addUserVo) {	

		SysUser user = new SysUser();
		if(StringUtil.isNotNull(addUserVo.getId())) {
			user = userService.getUserInfo(addUserVo.getId());
		}
		if(StringUtil.isNotNull(addUserVo.getPassword())) {
			user.setPassword(addUserVo.getPassword());
		}
		user.setLoginName(addUserVo.getLoginName());
		user.setUserName(addUserVo.getUserName());
		
		if(StringUtil.isNotNull(addUserVo.getRoleId())) {
			Role role = new Role();
			role.setId(addUserVo.getRoleId());
			user.setRole(role);
		}
		
		if(StringUtil.isNotNull(addUserVo.getFacilitatorId())) {
			Facilitator facilitator = new Facilitator();
			facilitator.setId(addUserVo.getFacilitatorId());
			user.setFacilitator(facilitator);
		}
		user = userService.addUser(user);
		return new MyResult<SysUser>(user);
	}
	
	
	@UserHandler
	@RequestMapping(value = "/getUser",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取用户详情",response = SysUser.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String")
	})
	public MyResult<SysUser> getUser(BaseReq baseReq,String userId) {	
		
		SysUser user = userService.getUser(userId);
/*		
		AddUserVo userVo = new AddUserVo();
		if(user.getRole() != null)
			userVo.setRoleId(user.getRole().getId());
		if(user.getFacilitator() != null)
			userVo.setFacilitatorId(user.getFacilitator().getId());
		BeanUtils.copyProperties(user, userVo);
		*/
		return new MyResult<SysUser>(user);
	}
}
