package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 权限
 * @author 青风侠
 *
 */
@Entity(name="Permission")
@Table(name="csys_permission")
public class Permission  extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3210709452156482932L;

	
	private String url;
	private String name;
	@Column(name="menu_id")
	private String menuId;
	@Transient
	private Boolean show;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	
}
