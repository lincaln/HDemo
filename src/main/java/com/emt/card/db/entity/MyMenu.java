package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 菜单
 * @author 青风侠
 *
 */
@Entity(name="MyMenu")
@Table(name="csys_menu")
public class MyMenu extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2966789456418908062L;

	@Column(unique=true)
	private String code;
	
	private String title;
	
	private String url;
	
	private String icon;
	
	private String classname;
	
	private Integer sort;
	
	private String pid;
	@Transient
	private String roleIds;
	@Transient
	private Boolean show;
	@Transient
	private List<Permission> permissions;
	@Transient
	private Set<String> actions=new HashSet<String>();
	@Transient
	private List<MyMenu> children=new ArrayList<MyMenu>();
	


	public String getCode() {
		return code;
	}

	public void setIndex(String code) {
		this.code = code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Boolean getShow() {
		return show!=null&&show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public List<MyMenu> getChildren() {
		return children;
	}

	public void setChildren(List<MyMenu> children) {
		this.children = children;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions=permissions;
	}

	public Set<String> getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions.add(actions);
	}
	public void setActions(List<String> actions) {
		this.actions.addAll(actions);
	}
	public void setActions(Set<String> actions) {
		this.actions = actions;
	}

	
}
