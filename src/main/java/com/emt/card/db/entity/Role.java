package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 角色
 * @author 青风侠
 *
 */
@Entity(name="Role")
@Table(name="csys_role")
public class Role  extends BaseModel implements Serializable  {

	public enum RoleType {
		系统,服务商
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7639680153598132266L;

	
	private String name;

	private Long companyid;
	
	@Column(name="role_type")
	private RoleType roleType;
	@Column(columnDefinition="LONGTEXT")
    private String remark;
	@Transient
	private String companyname;
	@Transient
	private Long count=(long) 0;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<SysUser> users;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Boolean noUser() {
		return users==null||users.isEmpty();
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public Role() {
	}

	public Role(String id) {
		this.id = id;
	}
}
