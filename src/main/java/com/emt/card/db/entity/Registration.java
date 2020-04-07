package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * 默认角色设置
 * @author 青风侠
 *
 */
@Entity
@Table(name = "csys_registration")
public class Registration  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2330878614736080953L;
	public enum RegId {
		facilitator;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	@UpdateTimestamp
	private Date updateDate;
	
	private String name;
	@Column(name="role_id")
	private String roleId;
	@OneToOne(cascade = {CascadeType.REFRESH}, optional = true)  // 指定多对一关系 
	@JoinColumn(name="role_id",insertable=false,updatable=false)
	private Role role;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
	
}
