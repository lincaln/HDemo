package com.emt.card.db.vo;

import java.util.Date;
import java.util.List;

import com.emt.card.db.entity.Card.CardStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权益
 * @author Administrator
 *
 */
@Data
public class UserEquitiesVo {
	
	private String id;				//卡权益表Id
	@ApiModelProperty("核销次数 剩余次数")
	private int affirmNumber;		//核销次数 剩余次数
	@ApiModelProperty("核销次数 已核销")
	private int affirmFinishNumber;		//核销次数 已核销
	@ApiModelProperty("核销次数 总次数")
	private int affirmTotalNumber;		//核销次数 总次数
	@ApiModelProperty("卡类型id")
	private String cardClazzId;			//卡类型id
	@ApiModelProperty("卡id")
	private String cardId;     			//具体卡的id
	@ApiModelProperty("卡权益id")
	private String equitiesId;			//权益id
	@ApiModelProperty("服务商id")
	private String facilitatorId;		//服务商id	
	
	
	@ApiModelProperty("权益名")
	private String equitiesName;			//权益名
	@ApiModelProperty("权益详情")
	private String equitiesdetails;			//权益详情
	
	@ApiModelProperty("卡号")
	private String cardNum;  		//卡号
	@ApiModelProperty("卡状态")
	private CardStatus cardStatus; //卡状态
	@ApiModelProperty("卡名")
    private String cardName;			//卡名
	@ApiModelProperty("信息")
	private String customizeArr;		//自定义填写项目 数组
	@ApiModelProperty("产品详情")
	private String productDetails;		//产品详情
	@ApiModelProperty("激活时间(有效时间开始)")
	private Date activateDate;//激活时间
	@ApiModelProperty("最后使用时间(有效时间结束)")
	private Date endUseDate;//最后使用时间
	
	@ApiModelProperty("手机")
	private String loginName;
	@ApiModelProperty("用户名")
	private String nickName;
	
	List<UserEquitiesAffirmRecord> affirmRecordList;
	
	
	
	

}
