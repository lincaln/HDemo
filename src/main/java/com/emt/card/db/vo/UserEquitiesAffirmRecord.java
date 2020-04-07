package com.emt.card.db.vo;

import java.util.Date;

import com.emt.card.db.entity.FacilitatorOrder.OrderStatus;
import com.emt.card.db.entity.FacilitatorOrder.OrderType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权益核销记录
 * @author Administrator
 *
 */
@Data
public class UserEquitiesAffirmRecord {
	
	@ApiModelProperty("核销人")
	private String affirmName;
	@ApiModelProperty("核销人手机")
	private String affirmPhone;
	@ApiModelProperty("核销时间")
	private Date affirmDate;
	@ApiModelProperty("剩余次数")
	private int affirmNumber;	
	@ApiModelProperty("核销次数")
	private int affirmFinishNumber;
	@ApiModelProperty("订单状态")
	private OrderStatus orderStatus;		//订单状态
	@ApiModelProperty("订单类型")
	private OrderType orderType;
	

}
