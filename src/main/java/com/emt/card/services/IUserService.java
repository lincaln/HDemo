package com.emt.card.services;

import java.util.List;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Channel;
import com.emt.card.db.entity.SysUser;

public interface IUserService {

	public SysUser getUser(String id);

	public Pagination<SysUser> getUserPage(Pagination<SysUser> p);
	
	/**
	 * 添加 修改 用户 
	 * @param user
	 * @return
	 */
	SysUser addUser(SysUser user);

	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	Pagination<SysUser> getUserList(String userName,String roleId,PageReq page);

	/**
	 * 获取用户详情
	 * @param id
	 * @return
	 */
	SysUser getUserInfo(String id);


	
}
