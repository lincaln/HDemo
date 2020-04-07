package com.emt.card.controller.req;

import com.emt.card.base.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AddUserVo extends BaseReq {
	
	private static final long serialVersionUID = -4441010878352999669L;
	
	private String id;
	@ApiModelProperty(value="登录名")
	private String loginName;
	@ApiModelProperty(value="用户名")
    private String userName;
	@ApiModelProperty(value="密码")
    private String password;
	@ApiModelProperty(value="角色iD")
    private String roleId;
	@ApiModelProperty(value="服务商Id")
    private String facilitatorId;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFacilitatorId() {
		return facilitatorId;
	}

	public void setFacilitatorId(String facilitatorId) {
		this.facilitatorId = facilitatorId;
	}



    
    
    
}
