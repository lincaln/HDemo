package com.emt.card.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;



@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -540362168435648798L;

	@Id
	@GeneratedValue(generator = "custom")
    @GenericGenerator(name = "custom", strategy = "com.emt.card.db.IdGenerator")
	protected String id;
	
	protected Boolean enable=true;
	@CreationTimestamp
	@Column(updatable=false)
	protected Date createDate;
	@UpdateTimestamp
	protected Date updateTime;
	
	private String loginName;

    private String password;

    private String avatarUrl;

    private String nickName;

    @Column(unique=true)
    private String wxMaOpenId;

    @Column(unique=true)
    private String wxMpOpenId;
   
    @Column(unique=true)
    private String unionId;
    
    @Column(name="cardRoleId",insertable=false, updatable=false)
    private String roleId;
    
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="cardRoleId")
    private Role role;

	
	@ManyToOne 
	@NotFound(action=NotFoundAction.IGNORE)
    private Facilitator facilitator;

    private String userName;
    
    private String idCard;
    
    private String idCardPic;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWxMaOpenId() {
		return wxMaOpenId;
	}

	public void setWxMaOpenId(String wxMaOpenId) {
		this.wxMaOpenId = wxMaOpenId;
	}

	public String getWxMpOpenId() {
		return wxMpOpenId;
	}

	public void setWxMpOpenId(String wxMpOpenId) {
		this.wxMpOpenId = wxMpOpenId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardPic() {
		return idCardPic;
	}

	public void setIdCardPic(String idCardPic) {
		this.idCardPic = idCardPic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Facilitator getFacilitator() {
		return facilitator;
	}

	public void setFacilitator(Facilitator facilitator) {
		this.facilitator = facilitator;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Role.RoleType getRoleType() {
		if(role!=null)
			return role.getRoleType();
		return null;
		
	}
    
    
}
