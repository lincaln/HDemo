package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

/**
 * 角色权限
 * @author 青风侠
 *
 */
@Entity(name="RolePermission")
@Table(name="csys_role_permission")
public class RolePermission extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9179560040954149176L;

	@Column(name="role_id")
	private String roleId;
	@Column(name="permission_id")
	private String permissionId;

	
	public RolePermission() {
		super();
	}
	
	public RolePermission(String roleId, String permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
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
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	
	
}
