package com.emt.card.services.impl;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.dao.SearchFilter;
import com.emt.card.dao.SearchFilter.MyOperator;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.Facilitator_;
import com.emt.card.db.entity.Role;
import com.emt.card.db.entity.Role_;
import com.emt.card.db.entity.SysUser;
import com.emt.card.db.entity.SysUser_;
import com.emt.card.services.IUserService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.StringUtil;

@Service
public class UserSeriveImpl extends BaseSerivce implements IUserService {

	@Override
	public SysUser getUser(String id) {
		SysUser user=baseDao.find(id, SysUser.class);
		return user;
	}

	@Override
	public Pagination<SysUser> getUserPage(Pagination<SysUser> page) {
		Facilitator Facilitator =page.Entity().getFacilitator();
		page.Entity().setFacilitator(null);
		Set<SearchFilter> sf=SearchFilter.getSearchFilters(page.Entity());
		if(Facilitator!=null) {
			sf.add(new SearchFilter(
SysUser_.FACILITATOR+SearchFilter.spt+Facilitator_.ID,
MyOperator.EQ,Facilitator.getId()));
			sf.add(new SearchFilter(
					SysUser_.ROLE+SearchFilter.spt+Role_.ROLE_TYPE,
					MyOperator.NEQ,Role.RoleType.系统));
		}
		Page<SysUser> p=baseDao.findPage(
				page.getPageIndex(),
				page.getPageSize(),
				page.SortTypes(),
				page.getIsDesc(),
				sf,SysUser.class);
		return toPagination(p);
	}

	@Override
	public SysUser addUser(SysUser user) {
		return baseDao.save(user);
	}

	@Override
	public SysUser getUserInfo(String id) {
		
		return baseDao.find(id, SysUser.class);
	}

	@Override
	public Pagination<SysUser> getUserList(String userName, String roleId,PageReq page) {
		
		String sql="SELECT * FROM sys_user LEFT JOIN `facilitator` ON sys_user.`facilitator_id` = facilitator.`id` "
				+ "WHERE 1=1 ";
		
		if(StringUtil.isNotEmpty(userName)) {
			sql +=" AND sys_user.login_name LIKE '%"+userName+"%' ";
		}
		if(StringUtil.isNotEmpty(roleId)) {
			sql +=" AND sys_user.role_id = "+roleId+" ";
		}
		
		Pagination<SysUser> p=new Pagination<SysUser>(
				page.getIndex(), page.getSize(), "sys_user.create_date", true,SysUser.class);
		
		Pagination<SysUser> pagination = baseDao.forPagination(p,sql);
		return pagination;	
	}

}
