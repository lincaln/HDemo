package com.emt.card.controller;



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
import com.emt.card.controller.req.CreateCardReq;
import com.emt.card.controller.req.GetCardPageReq;
import com.emt.card.controller.req.GetCommodityCardReq;
import com.emt.card.controller.req.SaveCardClazzReq;
import com.emt.card.controller.req.SaveEquitiesProductReq;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.Card.CardStatus;
import com.emt.card.db.entity.Role.RoleType;
import com.emt.card.db.entity.CardBindin;
import com.emt.card.db.entity.CardClazz;
import com.emt.card.db.entity.CommodityCard;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.SysUser;
import com.emt.card.db.vo.UserEquitiesVo;
import com.emt.card.support.BaseController;
import com.emt.card.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api( tags ="卡与权益")
@Scope("prototype")
@RestController
@RequestMapping(value = "/cardEquities/")
public class CardEquitiesController extends BaseController{

	@UserHandler
	@ApiOperation(value = "保存卡类型")
	@RequestMapping(value = "saveCardClazz",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<CardClazz> saveCardClazz(SaveCardClazzReq req){
		CardClazz cardClazz=req.toCardCalzz();
		cardClazz=cardEquitiesService.saveCardClazz(cardClazz);
		return new MyResult<CardClazz>(cardClazz);
	}
	
	@UserHandler
	@ApiOperation(value = "删除卡类型")
	@ApiImplicitParams(value = { 
			@ApiImplicitParam(name="id",value="卡类型id",dataType="String")})
	@RequestMapping(value = "delCardClazz",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Boolean> delCardClazz(BaseReq req,String id){
		Boolean is=cardEquitiesService.delCardClazz(id);
		return new MyResult<Boolean>(is?"":"",is);
	}
	
	@UserHandler
	@ApiOperation(value = "卡类型表单")
	@ApiImplicitParams(value = { 
    @ApiImplicitParam(name="name",value="卡类型名称",dataType="String")})
	@RequestMapping(value = "getCardClazzPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<CardClazz>> getCardClazzPage(
			PageReq pageReq, String name){
		Pagination<CardClazz> p=pageReq.toPagination(CardClazz.class);
		CardClazz cc=new CardClazz();
		cc.setCardName(name);
		p.setEntity(cc);
		if(!StringUtil.isNotEmpty(p.SortType()))
		p.setSortType("createDate");
		p=cardEquitiesService.getCardClazzPage(p);
		return new MyResult<Pagination<CardClazz>>(p);	
	}
	
	@UserHandler
	@ApiOperation(value = "创建卡号")
	@RequestMapping(value = "createCards",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<List<Card>> createCards(CreateCardReq req){
		List<Card> cards=cardEquitiesService.createCards(
				req.getCardClazzId(),
				req.getCardCount(),
				req.getChannelId(),
				req.toCardPeriodList());
		
		return new MyResult<List<Card>>(cards);
	}
	
	@UserHandler
	@ApiOperation(value = "获取卡列表")
	@RequestMapping(value = "getCardPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<Card>> getCardPage(GetCardPageReq req){
		if(!isAdmin(req.getClientId())) {
			req.setUserId(req.getClientId());
		}
		Pagination<Card> cardPage=cardEquitiesService.getCardPage(req);		
		return new MyResult<Pagination<Card>>(cardPage);
	}
	
	@UserHandler
	@ApiOperation(value = "获取卡信息")
	@ApiImplicitParams(value = { 
	@ApiImplicitParam(name="cardId",value="卡id",dataType="String"),
	@ApiImplicitParam(name="cardNum",value="卡号",dataType="String")})
	@RequestMapping(value = "getCard",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Card> getCard(BaseReq req,String cardId,String cardNum){
		if(StringUtils.isEmpty(cardNum)) {
		Card card=cardEquitiesService.getCard(cardId);		
		return new MyResult<Card>(card);
		}else {
			Card card=cardEquitiesService.getCardNum(cardNum);		
			return new MyResult<Card>(card);
		}
	}
	
	@UserHandler
	@ApiOperation(value = "保存权益")
	@RequestMapping(value = "saveEquitiesProduct",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<EquitiesProduct> saveEquitiesProduct(SaveEquitiesProductReq req){
		EquitiesProduct	equitiesProduct=req.toEquitiesProduct();
		equitiesProduct=cardEquitiesService.saveEquitiesProduct(equitiesProduct);
		return new MyResult<EquitiesProduct>(equitiesProduct);	
	}
	
	@UserHandler
	@ApiOperation(value = "权益表单")
	@ApiImplicitParams(value = { 
    @ApiImplicitParam(name="name",value="权益名称",dataType="String")})
	@RequestMapping(value = "getEquitiesProductPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<EquitiesProduct>> getEquitiesProductPage(
			PageReq pageReq, String name){
		Pagination<EquitiesProduct> p=pageReq.toPagination(EquitiesProduct.class);
		EquitiesProduct ep=new EquitiesProduct();
		ep.setEquitiesName(name);
		p.setEntity(ep);
		if(!StringUtil.isNotEmpty(p.SortType()))
			p.setSortType("createDate");
		p=cardEquitiesService.getEquitiesProductPage(p);
		return new MyResult<Pagination<EquitiesProduct>>(p);	
	}
	
	
	@UserHandler
	@ApiOperation(value = "卡激活")
	@ApiImplicitParams(value = { 
    @ApiImplicitParam(name="activateCode",value="激活码",dataType="String"),
    @ApiImplicitParam(name="cardPass",value="卡密码",dataType="String")})
	@RequestMapping(value = "cardActivate",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<String> cardActivate(PageReq pageReq, String activateCode,String cardPass) throws HosException{
		MyResult<String> result = new MyResult<String>();
		Card card = cardEquitiesService.getCardNum(activateCode);
		
		if(card == null || card.getCardStatus() == null) {
			result.setCode(220);
			result.setData("激活码不正确");
			return result;
		}
		
		//校验密码
		if(!cardPass.equals(card.getPassword())) {
			result.setCode(220);
			result.setData("请输入正确的卡密");
			return result;
		}
		switch(card.getCardStatus()){
         case effectOf :
        	 result.setCode(220);
        	 result.setData("此卡已生效并在保质期内，不能重新激活");
        	 return result;
         case expired :
        	 result.setCode(220);
        	 result.setData("此卡已过期，不能重新激活");
        	 return result;
         case Cancellation :
        	 result.setCode(220);
        	 result.setData("此卡已作废");
        	 return result;
		default:
			break;
	   }
		//卡状态是否为未激活并且未绑定用户并且是否需要激活==true
		if(CardStatus.invalid.equals(card.getCardStatus()) || !StringUtil.isNotNull(card.getUserId())){
			if(card.getIsNeedBinding() == true) //判断是否需要绑定
				card.setCardStatus(CardStatus.nobing);
			else
				card.setCardStatus(CardStatus.effectOf);
			
		}else if(card.getCardStatus().equals(CardStatus.nobing)&& StringUtil.isNotNull(card.getUserId())){
			result.setCode(220);
	       	result.setData("此卡已激活，不能重新激活");
	       	return result;
		}else{
			result.setCode(220);
       	 result.setData("卡状态态异常");
       	 return result;
		}
		
		card.setUserId(pageReq.getClientId());
		card = cardEquitiesService.cardActivate(card);
		result.setData(card.getCardName());
		return result;
	}
	
	
	@UserHandler
	@ApiOperation(value = "卡绑定")
	@ApiImplicitParams(value = { 
    @ApiImplicitParam(name="cardId",value="卡ID",dataType="String",required=true),
    @ApiImplicitParam(name="phone",value="电话",dataType="String",required=true),
    @ApiImplicitParam(name="name",value="姓名",dataType="String",required=true),
    @ApiImplicitParam(name="massageArray",value="动态表单（json）[{title:'',value:''}]",dataType="String",required=false),})
	@RequestMapping(value = "cardBinding",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<CardBindin> cardBinding(PageReq pageReq, String cardId,String name,String phone,String massageArray) throws HosException{
		//判断是否需要绑定
		Card card = cardEquitiesService.getCard(cardId);
		if(!StringUtil.isNotNull(card.getUserId())) {
			return new MyResult<CardBindin>("此卡未激活",220);
		}
		if(!card.getIsNeedBinding()) {
			return new MyResult<CardBindin>("此卡不需要绑定",220);
		}
		if(card.getCardBindin().size() == card.getBindingNum() || card.getCardBindin().size() > card.getBindingNum()) {
			return new MyResult<CardBindin>("绑定人数已满",220);
		}
		CardBindin cardBindin = new CardBindin();
		cardBindin.setCardId(cardId);
		cardBindin.setName(name);
		cardBindin.setPhone(phone);
		cardBindin.setMassageArray(massageArray);
		cardBindin = cardEquitiesService.cardBinding(cardBindin);
		return new MyResult<CardBindin>(cardBindin);
	}
	@UserHandler
	@ApiOperation(value = "获取用户权益管理列表")
	@ApiImplicitParams(value = { 
		    @ApiImplicitParam(name="cardNum",value="卡号/订单号",dataType="String",required=false),
		    @ApiImplicitParam(name="equitiesName",value="权益名",dataType="String",required=false),
		    @ApiImplicitParam(name="loginName",value="手机号",dataType="String",required=false),
		    @ApiImplicitParam(name="nickName",value="用户名",dataType="String",required=false),
		    @ApiImplicitParam(name="date",value="日期",dataType="String",required=false),
		    @ApiImplicitParam(name="cardStatus",value="卡状态",dataType="int",required=false),})
	@RequestMapping(value = "getUserEquitiesPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<UserEquitiesVo>> getUserEquitiesPage(GetCardPageReq req,String cardNum, String equitiesName, 
			String loginName, String nickName,String date, Integer cardStatus){
		
		String facilitatorId=null;
		String userId=null;
		//判断用户角色
		if(!RoleType.系统.equals(getRoleType(req.getClientId()))) {
			SysUser user=userService.getUser(req.getClientId());
			if(user.getFacilitator()!=null) {
				facilitatorId = user.getFacilitator().getId();
			}else {
				userId = user.getId();
			}
		}
		Pagination<UserEquitiesVo> cardPage=cardEquitiesService.getUserEquities(req,cardNum, equitiesName, loginName,
				nickName, date, cardStatus,userId,facilitatorId);
		return new MyResult<Pagination<UserEquitiesVo>>(cardPage);
	}
	
	
	@UserHandler
	@ApiOperation(value = "获取用户权益管理详情")
	@ApiImplicitParams(value = { 
	@ApiImplicitParam(name="id",value="卡id",dataType="String"),
	@ApiImplicitParam(name="cardNum",value="卡号",dataType="String")})
	@RequestMapping(value = "getUserEquities",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<UserEquitiesVo> getUserEquities(BaseReq req,String id){

		UserEquitiesVo userEquitiesVo = cardEquitiesService.getUserEquitiesAffirmRecord(id);
		
			return new MyResult<UserEquitiesVo>(userEquitiesVo);
	}
	
	@UserHandler
	@ApiOperation(value = "卡关联商品")
	@ApiImplicitParams(value = { 
	@ApiImplicitParam(name="commodityId",value="商品id",dataType="String"),
	@ApiImplicitParam(name="cardIdArray",value="卡id Json数组字符串：[“1”，“2”]",dataType="String")})
	@RequestMapping(value = "saveCradCommodityLink",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<List<CommodityCard>> saveCradCommodityLink(BaseReq req,String commodityId,String cardIdArray){

		List<CommodityCard> cList = cardEquitiesService.saveCommodityCard(commodityId,cardIdArray);
		
		return new MyResult<List<CommodityCard>>(cList);
	}
	
	@UserHandler
	@ApiOperation(value = "获取卡商品关联列表")
	@RequestMapping(value = "CommodityCardPage",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Pagination<GetCommodityCardReq>> CommodityCardPage(GetCardPageReq req,GetCommodityCardReq cc){
		
		Pagination<GetCommodityCardReq> CommodityCardPage=cardEquitiesService.getCommodityCardPage(cc);	
		
		return new MyResult<Pagination<GetCommodityCardReq>>(CommodityCardPage);
	}
	
	@UserHandler
	@ApiOperation(value = "取消卡商品关联")
	@RequestMapping(value = "cancelCradCommodityLink",
			produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public MyResult<Boolean> cancelCradCommodityLink(GetCardPageReq req,String id){
		Boolean is = cardEquitiesService.delCradCommodityLink(id);	
		return new MyResult<Boolean>(is?"":"",is);
	}
	
	
	
	
	
	
}
