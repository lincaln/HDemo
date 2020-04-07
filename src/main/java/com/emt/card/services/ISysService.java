package com.emt.card.services;

import java.util.List;
import java.util.Set;

import com.emt.card.db.entity.*;
import com.emt.card.db.entity.Registration.RegId;
import com.emt.card.base.HosException;
import com.emt.card.base.Pagination;

public interface ISysService {
	public static final String adminRoleId="administrator";

	/**
	 * 获取菜单队列带树形
	 * @param pid 某个菜单下的菜单列,null为顶端
	 * @param roleId 拥有菜单的角色
	 * @param rid 指定菜单范围下用show:true标记拥有菜单的角色
	 * @return
	 */
	public List<MyMenu> getMenuList(String pid, String roleId,String rid);

	/**
	 * 查找全部菜单,用show标记角色拥有菜单
	 * @param roleId
	 * @return
	 */
	public List<MyMenu> getAllMenu(String roleId);
	
	/**
	 * 保存操作权限
	 * @param permission
	 * @return
	 */
	public Permission savePermission(Permission permission);
	/**
	 * 删除操作权限
	 * @param id
	 * @return
	 */
	public Permission delPermission(String id);
	/**
	 * 获取操作权限
	 * @param id
	 * @return
	 */
	public Permission getPermission(String id);
	
	/**
	 * 获取权限列表
	 * @param menuid
	 * @return
	 */
	public List<Permission> getPermissions(String menuid);
	
	/**
	 * 更新用户角色
	 * @param userId
	 * @param regId
	 */
	public void updateUserRole(String userId,RegId regId);
	/**
	 * 保存角色
	 * @param role 角色对象
	 * @param menuIds 角色管理菜单
	 * @param permissionIds 角色管理权限
	 * @return
	 */
	public Role saveRole(Role role,String[] menuIds,String[] permissionIds);
	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws HosException 
	 */
	public void delRole(String id) throws HosException;
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public Role getRole(String id);
	/**
	 * 获取用户可用的角色
	 * @param companyId
	 * @return
	 */
	public List<Role> getRoles(String userId);
	
	/**
	 * 角色列表
	 * @param pageIndex
	 * @param size
	 * @param companyid
	 * @return
	 */
	public Pagination<Role> getRolePage(Integer pageIndex, Integer size,Long companyid);
	

	
	/**
	 * 获取默认角色配置
	 * @return
	 */
	public List<Registration> getAllRegistration();
	/**
	 * 更细默认角色配置
	 * @param registration
	 * @return
	 */
	public Registration updateRegistration(Registration registration);
	/**
	 * 获取默认角色id
	 * @param rid
	 * @return
	 */
	public String getRegRoleId(RegId rid);

	/**
	 * 保存菜单列表
	 * @param array 要保存的菜单列表
	 * @param pid
	 */
	public void saveList(List<MyMenu> array, String pid);

	public List<Role> getRoles(Role role);
	
	public Set<String> getPerUrl();

	public String getAllMenuStr(String roleId);



}
