package com.emt.card.services;

import java.util.List;

import com.emt.card.base.HosException;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.CardBindin;

import com.emt.card.controller.req.GetCardPageReq;
import com.emt.card.controller.req.GetCommodityCardReq;
import com.emt.card.db.entity.CardClazz;
import com.emt.card.db.entity.CardPeriod;
import com.emt.card.db.entity.CommodityCard;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.vo.UserEquitiesVo;

public interface ICardEquitiesService {


	/**
	 * 保存权益
	 * @param equitiesProduct
	 * @return
	 */
	public EquitiesProduct saveEquitiesProduct(EquitiesProduct equitiesProduct);
	
	/**
	 * 获取权益列表
	 * @param page
	 * @return
	 */
	public Pagination<EquitiesProduct> getEquitiesProductPage(Pagination<EquitiesProduct> page);

	/**
	 * 保存卡类型
	 * @param cardClazz
	 * @return
	 */
	public CardClazz saveCardClazz(CardClazz cardClazz);

	/**
	 * 获取卡类型列表
	 * @param page
	 * @return
	 */
	public Pagination<CardClazz> getCardClazzPage(Pagination<CardClazz> page);

	
	/**
	 * 卡激活
	 * @param clientId 
	 * @param activateCode 激活码
	 * @return
	 * @throws HosException 
	 */
	Card cardActivate(Card card) throws HosException;
	
	/**
	 * 卡号获取卡信息
	 * @param cardNum
	 * @return
	 */
	Card getCardNum(String cardNum);
	
	/**
	 * 卡绑定
	 * @param cardBindin
	 * @return
	 * @throws HosException 
	 */
	CardBindin cardBinding(CardBindin cardBindin) throws HosException;
	
	/**
	 * 获取用户权益管理列表
	 * @param cardNum 订单号（卡号）
	 * @param equitiesName 权益名
	 * @param loginName 手机号（登录账号）
	 * @param nickName 用户名
	 * @param date 日期
	 * @param cardStatus 卡状态
	 * @return
	 */
	Pagination<UserEquitiesVo> getUserEquities(GetCardPageReq req,String cardNum,String equitiesName,String loginName,
			String nickName,String date,Integer cardStatus,String userId,String facilitator);
	
	/**
	 * 获取用户权益管理详情
	 * @param id
	 * @return
	 */
	UserEquitiesVo getUserEquitiesAffirmRecord(String id);


	/**
	 * 批量创建卡
	 * @param cardClazzId 卡类型ID
	 * @param cardCount 卡数量
	 * @param channelId 渠道商ID
	 * @param cardPeriodList 时间限制集合
	 * @return
	 */
	public List<Card> createCards(
			String cardClazzId, Integer cardCount, String channelId, List<CardPeriod> cardPeriodList);

	/**
	 * 获取卡列表
	 * @param req
	 * @return
	 */
	public Pagination<Card> getCardPage(GetCardPageReq req);

	/**
	 * 获取卡
	 * @param cardId
	 * @return
	 */
	public Card getCard(String cardId);
	
	/**
	 * 添加修改卡商品关系
	 * @param commodityCard
	 * @return
	 */
	List<CommodityCard> saveCommodityCard(String commodityId,String cardIdArray);
	
	/**
	 * 查询卡商品关联信息
	 * @param cc
	 * @return
	 */
	Pagination<GetCommodityCardReq> getCommodityCardPage(GetCommodityCardReq cc);
	
	/**
	 * 卡商品取消关联
	 * @param id
	 * @return
	 */
	Boolean delCradCommodityLink(String id);

	public Boolean delCardClazz(String id);


	
	
	
	
	
	
}
