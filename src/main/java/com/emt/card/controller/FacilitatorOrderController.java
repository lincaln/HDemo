package com.emt.card.controller;


import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emt.card.base.BaseReq;
import com.emt.card.base.HosException;
import com.emt.card.base.MyResult;
import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.base.UserHandler;
import com.emt.card.controller.req.SaveFacilitatorOrder;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.CardEquities;
import com.emt.card.db.entity.EquitiesBookingQuestion;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.FacilitatorOrder;
import com.emt.card.db.entity.FacilitatorOrder.OrderStatus;
import com.emt.card.db.entity.FacilitatorOrder.OrderType;
import com.emt.card.db.entity.SysUser;
import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.support.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api( tags ="服务商订单")
@Scope("prototype")
@RestController
@RequestMapping(value = "/facilitatorOrder")
public class FacilitatorOrderController extends BaseController{

	
	
	@UserHandler
	@RequestMapping(value = "/getEquitiesProduct",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "查询所有服务权益",response = Facilitator.class)
	public MyResult<List<EquitiesProduct>> getEquitiesProduct(PageReq page) {
		
		//判断用户角色
		String facilitatorId=null;
		if(!RoleType.系统.equals(getRoleType(page.getClientId()))) {
			SysUser user=userService.getUser(page.getClientId());
			if(user.getFacilitator()!=null) {
			facilitatorId=user.getFacilitator().getId();
			}else {
				return new MyResult<>("用户没有服务商",220);
			}
		}
		List<EquitiesProduct> eplist = FacilitatorOrderService.getEquitiesProduct(facilitatorId);

		return new MyResult<List<EquitiesProduct>>(eplist);
	}
	
	@UserHandler
	@RequestMapping(value = "/getEquitiesBookingQuestion",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "权益id获取权益表单",response = EquitiesProduct.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="epId",value="权益id",required=true,dataType="String")
	})
	public MyResult<List<EquitiesBookingQuestion>> getEquitiesBookingQuestion(PageReq page,String epId) {
		
		List<EquitiesBookingQuestion> ebqlist = FacilitatorOrderService.getEquitiesBookingQuestion(epId);
		
		return new MyResult<List<EquitiesBookingQuestion>>(ebqlist);
	}
	
	@UserHandler
	@ApiOperation(value = "添加订单(服务商订单,预约订单,核销订单)")
	@RequestMapping(value = "addFacilitatorOrder",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<FacilitatorOrder> addFacilitatorOrder(SaveFacilitatorOrder fo) throws HosException{
		
		FacilitatorOrder facilitatorOrder=fo.toFacilitatorOrder();
		//facilitator如果用户非系统用当前用户,如果是系统需要传
		//cardBindinId如果要绑定要传, userName userPhone有绑定人用绑定人否则用当前用户,
		//facilitator通过cardId和equitiesId获得CardEquities的facilitatorId的对象,CardEquities的affirmNumber减1,如果已经是少于0返回错误 .		
		facilitatorOrder.setOrderType(OrderType.values()[fo.getOrderType()]);
		
		facilitatorOrder=FacilitatorOrderService.addFacilitatorOrder(facilitatorOrder);
		return new MyResult<FacilitatorOrder>(facilitatorOrder);
	}
	
	@UserHandler
	@ApiOperation(value = "查询订单列表分页")
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="userName",value="下单人",required=false,dataType="String"),
    		@ApiImplicitParam(name="userPhone",value="下单人电话",required=false,dataType="String"),
    		@ApiImplicitParam(name="cardNum",value="卡号",required=false,dataType="String"),
    		@ApiImplicitParam(name="equitiesId",value="权益名",required=false,dataType="String"),
    		@ApiImplicitParam(name="beginDate",value="下单开始时间",required=false,dataType="String"),
    		@ApiImplicitParam(name="overDate",value="下单结束时间",required=false,dataType="String"),
    		@ApiImplicitParam(name="sendType",value="派单状态：1 待派单，2 已派单，3 已撤回",required=false,dataType="String"),
    		@ApiImplicitParam(name="orderStatus",value="0.待使用,1.已使用,2.已取消",required=false,dataType="String"),
	})
	@RequestMapping(value = "getFacilitatorOrderPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<FacilitatorOrder>> getFacilitatorOrderPage(
			PageReq pageReq, String userName,String userPhone,String cardNum,String equitiesName,
			String beginDate,String overDate,String sendType,String orderStatus){
		Pagination<FacilitatorOrder> p=pageReq.toPagination(FacilitatorOrder.class);
		FacilitatorOrder fo=new FacilitatorOrder();
		//判断用户角色
		if(!RoleType.系统.equals(getRoleType(pageReq.getClientId()))) {
			SysUser user=userService.getUser(pageReq.getClientId());
			if(user.getFacilitator()!=null) {
				fo.setFacilitatorId(user.getFacilitator().getId());
			}else {
				fo.setUserId(user.getId());
			}
		}

		fo.setUserName(userName);
		fo.setUserPhone(userPhone);
		fo.setCardNum(cardNum);
		fo.setEquitiesName(equitiesName);
		fo.setSendType(sendType);
		p.setEntity(fo);
		p=FacilitatorOrderService.getFacilitatorOrderPage(p,beginDate,overDate,orderStatus);
		return new MyResult<Pagination<FacilitatorOrder>>(p);	
	}
	
	@UserHandler
	@RequestMapping(value = "/getFacilitatorOrderInfo",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取订单详情",response = FacilitatorOrder.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="id",value="服务订单id",required=true,dataType="String")
	})
	public MyResult<FacilitatorOrder> getFacilitatorOrderInfo(BaseReq baseReq,String id) {		
		FacilitatorOrder facilitatorOrder = FacilitatorOrderService.getFacilitatorOrderInfo(id);
		
		return new MyResult<FacilitatorOrder>(facilitatorOrder);
	}
	
	@UserHandler
	@RequestMapping(value = "/getOrderCanUserFacilitator",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取订单可用服务商")
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="id",value="服务订单id",required=true,dataType="String")
	})
	public MyResult<Collection<Facilitator>> getOrderCanUserFacilitator(BaseReq baseReq,String id) {		
		Collection<Facilitator> facilitators = 
				FacilitatorOrderService.getOrderCanUserFacilitator(id);
		return new MyResult<Collection<Facilitator>>(facilitators);
	}
	
	
	@UserHandler
	@RequestMapping(value = "/facilitatorConfirmOrder",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "服务商确认订单",response = FacilitatorOrder.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="id",value="服务订单id",required=true,dataType="String"),
    		@ApiImplicitParam(name="confirm",value="是否确认订单 确认:true,取消:false",required=true,dataType="boolean")
	})
	public MyResult<FacilitatorOrder> facilitatorConfirmOrder(BaseReq baseReq,String id,Boolean confirm) throws HosException {	
		
		FacilitatorOrder facilitatorOrder = FacilitatorOrderService.getFacilitatorOrderInfo(id);
		if(OrderStatus.已使用.equals(facilitatorOrder.getOrderStatus())) {
			return new MyResult<FacilitatorOrder>("订单状态已完成不能重复确认",202);
		}
		if(OrderStatus.已取消.equals(facilitatorOrder.getOrderStatus())) {
			return new MyResult<FacilitatorOrder>("订单状态已取消不能重复取消",202);
		}

			
		//确认订单
		if(confirm) {
			if(facilitatorOrder.getFacilitator()==null) {
				return new MyResult<FacilitatorOrder>("订单没派单不能完成",202);
			}
			facilitatorOrder.setOrderStatus(OrderStatus.已使用);
		}else {
			//取消订单
			facilitatorOrder.setOrderStatus(OrderStatus.已取消);
			if(!facilitatorOrder.getOrderType().equals(OrderType.后台订单)) {
			Card card = cardEquitiesService.getCard(facilitatorOrder.getCardId());	//获取卡信息
			List<CardEquities> cardEquities = card.getCardEquities();				//获取权益信息
				for (int i = 0; i < cardEquities.size(); i++) {
					if(cardEquities.get(i).getId().equals(facilitatorOrder.getEquitiesId())) {
							cardEquities.get(i).setAffirmNumber(cardEquities.get(i).getAffirmNumber()+1);//卡权益次数加1
					}
				}
				card.setCardEquities(cardEquities);
			}
			
		}
		FacilitatorOrderService.saveFacilitatorOrder(facilitatorOrder);
		return new MyResult<FacilitatorOrder>(facilitatorOrder);
	}
	
	@UserHandler
	@RequestMapping(value = "/updateOrderFacilitator",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "修改订单服务商（派单）",response = FacilitatorOrder.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="orderId",value="服务订单id",required=true,dataType="String"),
    		@ApiImplicitParam(name="facilitatorId",value="服务商id",required=true,dataType="String"),
	})
	public MyResult<FacilitatorOrder> updateOrderFacilitator(BaseReq baseReq,String orderId,String facilitatorId) throws HosException {		
		
		FacilitatorOrder facilitatorOrder = FacilitatorOrderService.getFacilitatorOrderInfo(orderId);
		if(StringUtils.isNotEmpty(facilitatorId))
		facilitatorOrder.setFacilitatorId(facilitatorId);
		else {
			if(!OrderStatus.待使用.equals(facilitatorOrder.getOrderStatus())){
				return new MyResult<FacilitatorOrder>("订单已经处理不能撤回",202);
			}
			facilitatorOrder.setFacilitatorId(null);
		}
		facilitatorOrder = FacilitatorOrderService.saveFacilitatorOrder(facilitatorOrder);
		
		return new MyResult<FacilitatorOrder>(facilitatorOrder);
	}
	
	
	
	
	
	
}
