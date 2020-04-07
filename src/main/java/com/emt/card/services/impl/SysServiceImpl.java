package com.emt.card.services.impl;


import java.io.InputStream;
import java.util.*;

import com.emt.card.db.entity.*;
import com.emt.card.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.db.entity.Registration.RegId;
import com.emt.card.db.repository.PermissionRepository;
import com.emt.card.db.repository.RegistrationRepository;
import com.emt.card.db.repository.RoleMenuRepository;
import com.emt.card.db.repository.RolePermissionRepository;
import com.emt.card.db.repository.RoleRepository;
import com.emt.card.db.entity.Role;
import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.db.entity.RoleMenu;
import com.emt.card.db.entity.RolePermission;
import com.emt.card.db.entity.SysUser;
import com.emt.card.base.HosException;
import com.emt.card.base.JedisBean;
import com.emt.card.base.Pagination;
import com.emt.card.services.ISysService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.CommUtil;



@Service
public class SysServiceImpl extends BaseSerivce implements ISysService{

	@Autowired
	private RegistrationRepository registrationRepository;
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private MyMenuRepository menuRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RedisProperties redisProperties;
	
	private JedisBean<String> getUbean(){
		try {
		String redisIP=redisProperties.getHost();
		JedisBean<String> ubean=new JedisBean<String>(redisIP,6,"roleId",String.class);
		return ubean;
		}catch (Exception e) {}
		return null;
	}

	private JedisBean<MyMenu> getMbean(){
		try {
		String redisIP=redisProperties.getHost();
		JedisBean<MyMenu> mbean=new JedisBean<MyMenu>(redisIP,6,"menu",MyMenu.class);
		return mbean;
		}catch (Exception e) {}
		return null;
	}

	private static final String menusql=
			"SELECT `id`,`code`,`icon`,`title`,`url`,`classname`,"
			+ "GROUP_CONCAT(role_id) AS role_ids FROM `csys_r_menu` ";
	@Override
	public List<MyMenu> getAllMenu(String roleId) {
	if(getMbean().containsKey(roleId)) {
		List<MyMenu> list=getMbean().gets(roleId);
		return list;
	}
		String sql=menusql+
				" WHERE state=1 AND pid is null GROUP BY id ORDER BY sort";
	List<MyMenu> list=baseDao.forList(sql,MyMenu.class);
	/*String psql="SELECT permission_id FROM `role_permission` ";
	if(!adminRoleId.equals(roleId)) {
		psql+= "where role_id='"+roleId+"'";
	}
	List<String> pids=jdbcTemplate.queryForList(psql, String.class);*/
		for (MyMenu myMenu : list) {
			setMenus(myMenu,roleId,null);
		}
		getMbean().puts(roleId, list);
		return list;
	}
	
	@Override
	public List<MyMenu> getMenuList(String pid,String roleId,String rid) {
		String sql=
"SELECT `id`,`code`,`create_date`,`icon`,`title`,`pid`,`sort`,`state`,`url`,`classname` FROM `csys_r_menu` where state=1 ";
		if(StringUtils.isEmpty(pid)) {
			sql+=" and pid is null ";
			//perids=null;
			rmids=null;
			//rpids=null;
		}else {
			sql+=" and pid='"+pid+"' ";
		}

		if(adminRoleId.equals(roleId)) {
			sql+=" GROUP BY id ";
		}else {
			sql+=" and role_id='"+roleId+"' ";

			/*if(perids==null)
				perids=jdbcTemplate.queryForList(
		"SELECT permission_id FROM `role_permission` where role_id='"+roleId+"'", String.class);*/
		}

		sql+=" order by sort";
		List<MyMenu> list=baseDao.getList(sql,MyMenu.class);


		if(!StringUtils.isEmpty(rid)) {
			rmids=jdbcTemplate.queryForList(
	"SELECT menu_id FROM `role_menu` where role_id='"+rid+"'", String.class);
			/*rpids=jdbcTemplate.queryForList(
	"SELECT permission_id FROM `role_permission` where role_id='"+rid+"'", String.class);*/
		}
		for (MyMenu myMenu : list) {
			setMenu(myMenu,roleId,rid);
		}
		return list;
		
	}
	//private List<String> perids;
	private List<String> rmids;
	//private List<String> rpids;

	private void setMenu(MyMenu myMenu,String roleId,String rid) {
		if(rmids==null) {
		  myMenu.setShow(true);
		}else {
		  myMenu.setShow(rmids.contains(myMenu.getId()));
		}

		List<MyMenu> menus=getMenuList(myMenu.getId(),roleId,null);
		myMenu.setChildren(menus);


		/*try {
		List<Permission> pers=getPermissions(myMenu.getId());
		List<Permission> permissions=new ArrayList<Permission>();
		for (Permission permission : pers) {

			if(perids==null||perids.contains(permission.getId())) {
				   if(rpids==null) {
					    permission.setShow(false);
					}else {
						permission.setShow(rpids.contains(permission.getId()));
					}
				   permissions.add(permission);
			}
		}
		myMenu.setPermissions(permissions);
		}catch (Exception e) {}*/

	}

	@Override
	public String getAllMenuStr(String roleId) {
		String result = "";
		if(getMbean().containsKey(roleId)) {
			return getMbean().value(roleId);
		}
		return result;
	}

	

	
	
	
	private void setMenus(MyMenu myMenu,String roleId,List<String> pids) {
		/*try {
		List<Permission> pers=getPermissions(myMenu.getId());
		myMenu.setPermissions(null);
		for (Permission permission : pers) {
			permission.setShow(pids.contains(permission.getId()));
			if(permission.getShow()||adminRoleId.equals(roleId)) {
				List<String> urls=JSONArray.parseArray(permission.getUrl(), String.class);
				myMenu.setActions(urls);
			}
		}
		}catch (Exception e) {}*/

		if(adminRoleId.equals(roleId)) {
			myMenu.setShow(true);
		}else
		if(!StringUtils.isEmpty(myMenu.getRoleIds())) {
			String[] roleIds=myMenu.getRoleIds().split(",");
			for (int i = 0; i < roleIds.length; i++) {
				if(roleIds[i].equals(roleId)) {
					myMenu.setShow(true);
					continue;
				}
			}
		}

		if(myMenu.getShow()) {
		String sql=menusql+" where state=1 AND  pid='"+myMenu.getId()+"' GROUP BY id order by sort";
	    List<MyMenu> list=baseDao.forList(sql,MyMenu.class);
	    myMenu.setChildren(list);
		for (MyMenu menu : list) {
			setMenus(menu,roleId,pids);
		}
		}
	}






	@Override
	public Permission savePermission(Permission permission) {
		permission=permissionRepository.save(permission);
		getUbean().flushDB();
		return permission;
	}

	@Override
	public Permission delPermission(String id) {
		Permission permission=permissionRepository.getOne(id);
		permissionRepository.delete(permission);
		getUbean().flushDB();
		return permission;
	}

	@Override
	public Permission getPermission(String id) {
		Optional<Permission> permission=permissionRepository.findById(id);
		return permission.get();
	}

	@Override
	public List<Permission> getPermissions(String menuid) {
		String sql="SELECT * FROM `csys_m_permission` ";
		if(!StringUtils.isEmpty(menuid)) {
			sql+=" WHERE menu_id='"+menuid+"' ";
		}
		sql+=" order by create_date desc ";
	List<Permission> list=baseDao.getList(sql,Permission.class);
		return list;
	}

	@Override
	public Role saveRole(Role role, String[] menuIds, String[] permissionIds) {
		role=roleRepository.save(role);
		String roleId=role.getId();

		jdbcTemplate.update("DELETE FROM csys_role_menu WHERE role_id='"+roleId+"'");
		List<RoleMenu> rms=new ArrayList<RoleMenu>();
		for (int i = 0; i < menuIds.length; i++) {
			RoleMenu rm=new RoleMenu(roleId, menuIds[i]);
			rms.add(rm);
		}
		roleMenuRepository.saveAll(rms);

		jdbcTemplate.update(
				"DELETE FROM csys_role_permission WHERE role_id='"+roleId+"'");
		List<RolePermission> rps=new ArrayList<RolePermission>();
		for (int i = 0; i < permissionIds.length; i++) {
			RolePermission rp=new RolePermission(roleId, permissionIds[i]);
			rps.add(rp);
		}
		rolePermissionRepository.saveAll(rps);
		getUbean().remove(role.getId());
		getMbean().remove(role.getId());
		return role;
	}

	@Override
	public void delRole(String id) throws HosException {
		Role role=roleRepository.getOne(id);
		if(role.noUser()) {
		roleRepository.delete(role);
		getUbean().remove(id);
		getMbean().remove(id);
		}else
		throw new HosException("先解绑角色下面用户才可以删除");
	}

	@Override
	public Role getRole(String id) {
		Optional<Role> role=roleRepository.findById(id);
		return role.get();
	}

	@Override
	public List<Role> getRoles(String userId) {
		
		String sql=" FROM  `csys_role_veiw` ";
		SysUser user=baseDao.get(userId, SysUser.class);
		if(!user.getRole().getRoleType().equals(RoleType.系统)) {
			sql+=" where role_type<>"+RoleType.系统.ordinal();
		}
		
		List<Role> list=baseDao.getList(
		"SELECT * "+sql+" order by create_date desc",Role.class);
		
		if(!adminRoleId.equals(user.getRole().getId())) {
		List<MyMenu> mymenus=getAllMenu(user.getRole().getId());
		Set<String> menuIds=new HashSet<String>();
		for (MyMenu myMenu : mymenus) {
			menuIds.add(myMenu.getId());
		}
		
		for (int i = 0; i < list.size(); i++) {
			Role role=list.get(i);
			List<MyMenu> menus=getAllMenu(role.getId());
			
            if(menuIds.size()<=menus.size()) {
            	list.remove(i);
				i--;
				break;
			}
			for (MyMenu myMenu : menus) {
				if(!menuIds.contains(myMenu.getId())) {
					list.remove(i);
					i--;
					break;
				}
			}
			
		}
		}
		
		return list;
	}
	@Override
	public List<Role> getRoles(Role role) {
		List<Role> list=baseDao.findList(role,null);
		return list;
	}
	@Override
	public Pagination<Role> getRolePage(
			Integer pageIndex, Integer size,Long companyid) {
		String sql=" FROM  `csys_role_veiw` ";
		if(!StringUtils.isEmpty(companyid)) {
			sql+=" where companyid="+companyid+" ";
		}
		Pagination<Role> p=
				new Pagination<Role>(pageIndex, size, "create_date", true,Role.class);
		p=baseDao.getPagination(p, "SELECT * "+sql);
		/*List<Role> list=baseDao.getList("SELECT * "+p.toPageSql(sql),
				Role.class);
		Integer total=jdbcTemplate.queryForObject(
				"SELECT COUNT(0) "+sql, Integer.class);
		p.setList(list);
		p.setTotal(total);*/
		return p;
	}

	@Override
	public Set<String> getPerUrl() {
		Set<String> urls=new LinkedHashSet<String>();
		if(getUbean().containsKey(adminRoleId)) {
			List<String> urllist=getUbean().gets(adminRoleId);
			urls.addAll(urllist);
		}else {
			List<Permission> list=getPermissions(null);

			for (Permission rp : list) {
				String urljson=rp.getUrl();
				JSONArray urlArray=JSONArray.parseArray(urljson);
				for (int i = 0; i < urlArray.size(); i++) {
					String surl=urlArray.getString(i);
					urls.add(surl);
				}
			}
			List<String> urllist=new ArrayList<String>(urls);
			getUbean().puts(adminRoleId,urllist);
		}
		return urls;
	}




	@Override
	public List<Registration> getAllRegistration() {
		return registrationRepository.findAll();
	}


	@Override
	public Registration updateRegistration(Registration registration) {
		RegId.valueOf(registration.getId());
		/*Registration r=registrationRepository.findById(registration.getId()).get();
		Role role=roleRepository.findById(registration.getRoleId()).get();
		role.setCompanyid(null);
		roleRepository.save(role);
		r.setRole(role);*/
		registration=registrationRepository.save(registration);
		return registration;
	}


	@Override
	public String getRegRoleId(RegId rid) {
		Registration r=registrationRepository.findById(rid.name()).get();
		return r.getRoleId();
	}


     public void initMenu(){

		try {
		InputStream in=SysServiceImpl.class
				.getResource("menu.json").openStream();
	    byte[] b=CommUtil.input2byte(in, 1024);
	    List<MyMenu> array=JSONArray.parseArray(new String(b),MyMenu.class);
		System.out.println(array);
		saveList(array, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
     public void saveList(List<MyMenu> array,String pid) {
    	 if(pid==null) {
    		 getUbean().flushDB();
    		 baseDao.update("UPDATE csys_menu SET state=0");
    	 }
		for (int i = 0; i < array.size(); i++) {
			try {
			MyMenu myMenu = array.get(i);
			List<MyMenu> list=myMenu.getChildren();
			MyMenu m=new MyMenu();
			m.setCode(myMenu.getCode());
			m =baseDao.findOne(m);
			if(m!=null) {
				myMenu.setId(m.getId());
			}
			myMenu.setState(true);
			myMenu.setPid(pid);
			myMenu.setSort(i);
			myMenu=menuRepository.saveAndFlush(myMenu);
			saveList(list, myMenu.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateUserRole(String userId, RegId regId) {
		Registration  reg=
				registrationRepository.findById(regId.name())
				.orElse(new Registration());
		SysUser user=baseDao.find(userId, SysUser.class);
		if(user!=null)
		if(user.getRole()==null||
				!Role.RoleType.系统.equals(
						user.getRole().getRoleType())) {
		user.setRole(reg.getRole());
		baseDao.save(user);
		}
	}




}
