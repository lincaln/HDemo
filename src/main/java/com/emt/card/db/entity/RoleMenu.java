package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

/**
 * 角色菜单
 * @author 青风侠
 *
 */
@Entity(name="RoleMenu")
@Table(name="csys_role_menu")
public class RoleMenu  extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8760228759058628520L;

	@Column(name="role_id")
	private String roleId;
	@Column(name="menu_id")
	private String menuId;
	
	
	public RoleMenu() {
		super();
	}
	
	public RoleMenu(String roleId, String menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	
}
