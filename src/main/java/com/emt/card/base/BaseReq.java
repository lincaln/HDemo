package com.emt.card.base;

import lombok.Data;

import java.io.Serializable;

import com.emt.card.support.TokenUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Data
@ApiModel
public class BaseReq implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8402467035754775300L;
	
	@ApiModelProperty(value="客户端ID",required=true)
	private String clientId;
	@ApiModelProperty(value="客户端平台",required=true)
	private TokenUtil.Platform platform;
}
