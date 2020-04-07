package com.emt.card.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.emt.card.base.HosException;
import com.emt.card.base.Pagination;

import com.emt.card.controller.req.GetCardPageReq;
import com.emt.card.controller.req.GetCommodityCardReq;
import com.emt.card.dao.SearchFilter;
import com.emt.card.dao.SearchFilter.MyOperator;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.Card.CardStatus;
import com.emt.card.db.entity.CardBindin;
import com.emt.card.db.entity.CardClazz;
import com.emt.card.db.entity.CardClazz_;
import com.emt.card.db.entity.CardEquities;
import com.emt.card.db.entity.CardPeriod;
import com.emt.card.db.entity.Card_;
import com.emt.card.db.entity.Channel_;
import com.emt.card.db.entity.CommodityCard;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.FacilitatorOrder;
import com.emt.card.db.entity.FacilitatorOrder.OrderStatus;
import com.emt.card.db.entity.FacilitatorOrder.OrderType;
import com.emt.card.db.vo.UserEquitiesAffirmRecord;
import com.emt.card.db.vo.UserEquitiesVo;
import com.emt.card.services.ICardEquitiesService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.CommUtil;
import com.emt.card.utils.StringUtil;

@Service
public class CardEquitiesServiceImpl extends BaseSerivce implements ICardEquitiesService {

	@Override
	public EquitiesProduct saveEquitiesProduct(EquitiesProduct equitiesProduct) {
		equitiesProduct=baseDao.save(equitiesProduct);
		return equitiesProduct;
	}

	@Override
	public Pagination<EquitiesProduct> getEquitiesProductPage(Pagination<EquitiesProduct> page) {
		Page<EquitiesProduct> p=baseDao.findPage(
				page.getPageIndex(),
				page.getPageSize(),
				page.SortTypes(),
				page.getIsDesc(),
				page.Entity());
		return toPagination(p);
	}

	@Override
	public CardClazz saveCardClazz(CardClazz cardClazz) {
		cardClazz=baseDao.save(cardClazz);
		return cardClazz;
	}

	@Override
	public Pagination<CardClazz> getCardClazzPage(Pagination<CardClazz> page) {
		Set<SearchFilter> sf=SearchFilter.getSearchFilters(page.Entity());
		Page<CardClazz> p=baseDao.findPage(
				page.getPageIndex(),
				page.getPageSize(),
				page.SortTypes(),
				page.getIsDesc(),
				sf,CardClazz.class);
		return toPagination(p);
	}

	@Override
	public List<Card> createCards(String cardClazzId, Integer cardCount, String channelId,
			List<CardPeriod> cardPeriodList) {
		if(cardPeriodList!=null&&!cardPeriodList.isEmpty())
		cardPeriodList=baseDao.save(cardPeriodList);
		CardClazz cc=baseDao.find(cardClazzId, CardClazz.class);
		
		List<CardEquities> ces=cc.getCardEquities();
		
		TimingDate  td=getPeriod(cardPeriodList, new Date(), null, null);
		List<Card> cards=new ArrayList<Card>();
		for (int i = 0; i < cardCount; i++) {
			Card card=new Card();
			card.setCardClazz(cc);
			BeanUtils.copyProperties(cc, card);
			BeanUtils.copyProperties(td, card);
			card.setCardEquities(null);
			card.setId(null);
			card.setCreateDate(null);
			card.setModifiedDate(null);
			card.setCardNum(CommUtil.getOrderNo());
			card.setPassword(CommUtil.getRandomNo(6));
			card.setChannelId(channelId);
			card.setCardStatus(CardStatus.invalid);
			card.setPeriods(cardPeriodList);
			
			List<CardEquities> celists=new ArrayList<CardEquities>();
			for (CardEquities cardEquities : ces) {
				
				Set<Facilitator> facilitators = cardEquities.getFacilitators();
				Set<Facilitator> newfacilitators=new HashSet<Facilitator>();
				for (Facilitator facilitator : facilitators) {
					Facilitator f = new Facilitator();
					f.setId(facilitator.getId());
					newfacilitators.add(f);
				}
				
				CardEquities cq =new CardEquities(
						cardEquities.getEquitiesId(),
						newfacilitators,
						cardEquities.getAffirmNumber());
				celists.add(cq);
			}
			//celists = baseDao.save(celists);
			card.setCardEquities(celists);
			card=baseDao.save(card);
			cards.add(card);
		}
		//cards=baseDao.save(cards);
		return cards;
	}

	@Override
	public Pagination<Card> getCardPage(GetCardPageReq req) {
		Set<SearchFilter> filters=new HashSet<SearchFilter>();
		if(!StringUtils.isEmpty(req.getCardClazzId())) {
			filters.add(new SearchFilter(
					Card_.CARD_CLAZZ+SearchFilter.spt+CardClazz_.ID, 
					MyOperator.EQ, req.getCardClazzId()));
		}
		if(!StringUtils.isEmpty(req.getChannelName())) {
			filters.add(new SearchFilter(
					Card_.CHANNEL+SearchFilter.spt+Channel_.CHANNEL_NAME, 
					MyOperator.LIKE, req.getChannelName()));
		}
		if(!StringUtils.isEmpty(req.getCardNum())) {
			filters.add(new SearchFilter(
					Card_.CARD_NUM,MyOperator.LIKE, req.getCardNum()));
		}

		if(!StringUtils.isEmpty(req.getUserId())) {
			filters.add(new SearchFilter(
					Card_.USER_ID, 
					MyOperator.EQ, req.getUserId()));
		}
		if(req.getBiginDate()!=null) {
			filters.add(new SearchFilter(
					Card_.CREATE_DATE, 
					MyOperator.GT, req.getBiginDate()));
		}
		if(req.getEndDate()!=null) {
			filters.add(new SearchFilter(
					Card_.CREATE_DATE, 
					MyOperator.LT,DateUtils.addDays(req.getEndDate(), 1)));
		}
		if(req.getStauts()!=null) {
			filters.add(new SearchFilter(
					Card_.CARD_STATUS, 
					MyOperator.EQ,Card.CardStatus.values()[req.getStauts()]));
		}
		if(req.getSorts()==null) {
			req.addSort(Card_.CREATE_DATE);
			req.setIsDesc(true);
		}
		Page<Card> page=baseDao.findPage(
				req.getIndex(), req.getSize(),
				req.getSorts(), req.getIsDesc(),
				filters, Card.class);
		return toPagination(page);
	}

	@Override
	public Card getCard(String cardId) {
		Card card=baseDao.find(cardId, Card.class);
		return card;
	}


	/**
	 * 卡激活
	 * @param clientId 
	 * @param activateCode 激活码
	 * @return
	 * @throws HosException 
	 */
	@Override
	public Card cardActivate(Card card) throws HosException {
		if(card.getEndActivateDate()!=null&&
				card.getEndActivateDate().before(new Date())) {
			throw new HosException("超出激活限制时间", 220);
		}
			
		card =baseDao.save(card);
		TimingDate td=getPeriod(
				card.getPeriods(), 
				card.getCreateDate(), 
				card.getModifiedDate(), 
				null);
		card.setActivateDate(card.getModifiedDate());
		BeanUtils.copyProperties(td, card);
		card =baseDao.save(card);
		return card;
	}

	/**
	 * 卡号获取卡信息
	 * @param cardNum
	 * @return
	 */
	@Override
	public Card getCardNum(String cardNum) {
		Card card=new Card();
		card.setCardNum(cardNum);
		card = baseDao.findOne(card);
		return card;
	}

	/**
	 * 卡绑定
	 * @param cardBindin
	 * @return
	 * @throws HosException 
	 */
	@Override
	public CardBindin cardBinding(CardBindin cardBindin) throws HosException {
		Card card=baseDao.find(cardBindin.getCardId(), Card.class);
		if(card.getEndBingDate()!=null&&
				card.getEndBingDate().before(new Date())){
			throw new HosException("已经超过绑定时间", 220);
		}
		if(card.getCardBindin()==null||card.getCardBindin().isEmpty()) {
		TimingDate td=getPeriod(card.getPeriods(), 
				card.getCreateDate(), 
				card.getActivateDate(),
				new Date());
		BeanUtils.copyProperties(td, card);
		baseDao.save(card);
		}
		CardBindin cb = new CardBindin();
		cb.setCardId(cardBindin.getCardId());
		List<CardBindin> list =baseDao.findList(cb, null);
		if(list.size() == 0 && card.getCardStatus().equals(CardStatus.nobing)) {
			card.setCardStatus(CardStatus.effectOf);
			baseDao.save(card);
		}
		
		cardBindin =baseDao.save(cardBindin);
		
		return cardBindin;
	}

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
	@Override
	public Pagination<UserEquitiesVo> getUserEquities(GetCardPageReq req,String cardNum, String equitiesName, String loginName, String nickName,
			String date, Integer cardStatus,String userId,String facilitatorId) {

		String sql="SELECT * "
				+ "FROM `card_equities` cq LEFT JOIN `card` c ON cq.card_id = c.id  LEFT JOIN sys_user u "
				+ "ON c.user_id = u.id LEFT JOIN `equities_productcard` ep ON cq.equities_id = ep.id WHERE 1=1 ";
		
		if(StringUtil.isNotEmpty(facilitatorId)) {
			sql +=" AND cq.`facilitator_id` = '"+facilitatorId+"' ";
		}
		if(StringUtil.isNotEmpty(userId)) {
			sql +=" AND c.`user_id` = '"+userId+"' ";
		}
		if(StringUtil.isNotEmpty(cardNum)) {
			sql +=" AND c.`card_num` LIKE '%"+cardNum+"%' ";
		}
		if(StringUtil.isNotEmpty(equitiesName)) {
			sql +=" AND ep.`equities_name` LIKE '%"+equitiesName+"%' ";
		}
		if(StringUtil.isNotEmpty(loginName)) {
			sql +=" AND u.`login_name` LIKE '%"+loginName+"%' ";
		}
		if(StringUtil.isNotEmpty(nickName)) {
			sql +=" AND u.nick_name LIKE '%"+nickName+"%' ";
		}
		
		if(StringUtil.isNotEmpty(date)) {
			sql += "AND c.`activate_date` <= '"+date+"%'  AND  c.`end_use_date` >= '"+date+"%' "; 
		}
		if(cardStatus != null) {
			sql += "AND c.`card_status` = "+ cardStatus;
		}
		
		Pagination<UserEquitiesVo> p=new Pagination<UserEquitiesVo>(
				req.getIndex(), 20, "ep.create_date", true,UserEquitiesVo.class);
		p = baseDao.forPagination(p,sql);
		
		List<UserEquitiesVo> list = p.getList();
		//统计核销总数 已使用数量
		for (int i = 0; i < list.size(); i++) {
			FacilitatorOrder facilitatorOrder = new FacilitatorOrder();
			facilitatorOrder.setEquitiesId(list.get(i).getEquitiesId());
			facilitatorOrder.setOrderType(OrderType.预约订单);
			Long affirmFinishNumber = baseDao.count(facilitatorOrder);		//统计预约订单数量
			facilitatorOrder.setOrderType(OrderType.核销订单);
			List<FacilitatorOrder> foList = baseDao.findList(facilitatorOrder,null);	//获取全部核销订单
			//统计已使用数量 总数量
			for (FacilitatorOrder facilitatorOrder2 : foList) {
				affirmFinishNumber += facilitatorOrder2.getServiceTimes();
			}
			list.get(i).setAffirmFinishNumber(affirmFinishNumber.intValue());
			list.get(i).setAffirmTotalNumber(affirmFinishNumber.intValue()+list.get(i).getAffirmNumber());
		}
		p.setList(list);
		
		return p;
	}

	/**
	 * 获取用户权益管理详情
	 * @param id
	 * @return
	 */
	@Override
	public UserEquitiesVo getUserEquitiesAffirmRecord(String id) {
		String sql="SELECT cq.*,ep.equities_name,ep.equitiesdetails, c.card_num,c.card_status,c.card_name,"
				+ "c.customize_arr,c.product_details,c.activate_date,c.end_use_date,u.login_name,u.nick_name "
				+ "FROM `card_equities` cq LEFT JOIN `card` c ON cq.card_id = c.id  LEFT JOIN sys_user u "
				+ "ON c.user_id = u.id LEFT JOIN `equities_productcard` ep ON cq.equities_id = ep.id WHERE cq.id = "+id;
		
		UserEquitiesVo userEquitiesVo = baseDao.getOne(sql, UserEquitiesVo.class);
		
		//计算卡核销总数
		FacilitatorOrder facilitatorOrder = new FacilitatorOrder();
		facilitatorOrder.setEquitiesId(userEquitiesVo.getEquitiesId());
		facilitatorOrder.setOrderType(OrderType.预约订单);
		Long affirmFinishNumber = baseDao.count(facilitatorOrder);		//统计预约订单数量
		facilitatorOrder.setOrderType(OrderType.核销订单);
		List<FacilitatorOrder> foList = baseDao.findList(facilitatorOrder,null);	//获取全部核销订单
		//统计已使用数量 
		for (FacilitatorOrder facilitatorOrder2 : foList) {
			affirmFinishNumber += facilitatorOrder2.getServiceTimes();
		}
		String sql2 = "SELECT  o.`modified_date` AS affirm_date,o.`service_times` AS affirm_finish_number,"
				+ "o.`order_status`,o.order_type,u.`nick_name` AS affirm_name ,u.`login_name` AS affirm_phone "
				+ "FROM `facilitator_order` o LEFT JOIN sys_user u ON o.facilitator_id = u.id "
				+ "WHERE o.`equities_id` = '"+ userEquitiesVo.getEquitiesId()+"' AND o.order_type IN(1,2) ";
		
		//统计 获取核销记录
		List<UserEquitiesAffirmRecord> uaList = baseDao.forList(sql2, UserEquitiesAffirmRecord.class);
		for (int i = 0; i < uaList.size(); i++) {
			uaList.get(i).setAffirmNumber(affirmFinishNumber.intValue());
			
			if(uaList.get(i).getOrderType().equals(OrderType.预约订单) 
					&& !uaList.get(i).getOrderStatus().equals(OrderStatus.已使用)) 
				uaList.remove(i);
		}
		
				userEquitiesVo.setAffirmRecordList(uaList);
		return userEquitiesVo;
	}
	
	@Override
	public List<CommodityCard> saveCommodityCard(String commodityId,String cardIdArray) {
		
		List<CommodityCard> ccList=new ArrayList<CommodityCard>();
		List<String> cardId=JSONArray.parseArray(cardIdArray, String.class);
		if(StringUtil.isNotEmpty(cardIdArray) && StringUtil.isNotEmpty(commodityId)) {
			for (String cid : cardId) {
				CommodityCard cc=new CommodityCard();
				cc.setCardId(cid);
				cc.setCommodityId(commodityId);
				cc = baseDao.save(cc);
				ccList.add(cc);
			}
		}
		return ccList;
	}
	
	@Override
	public Pagination<GetCommodityCardReq> getCommodityCardPage(GetCommodityCardReq cc) {
		
    	String sql = "SELECT cc.*,co.`name` AS commodity_name,c.`card_name` FROM `commodity_card` cc  "
    			+ "LEFT JOIN `em_commodity` co ON co.`id` = cc.`commodity_id` LEFT JOIN card_clazz c "
    			+ "ON c.`id` = cc.`card_id` WHERE 1=1 ";
		if(StringUtil.isNotEmpty(cc.getCardName()))
			sql += "AND c.card_name LIKE '%"+cc.getCardName()+"%' ";		
		if(StringUtil.isNotEmpty(cc.getCommodityName()))
			sql += "AND co.`name` LIKE '%"+cc.getCommodityName()+"%' ";
		
		Pagination<GetCommodityCardReq> p=new Pagination<GetCommodityCardReq>(
				cc.getIndex(), cc.getSize(), "cc.`create_date`", true,GetCommodityCardReq.class);
		p = baseDao.forPagination(p,sql);
		 
		return p;
	}

	@Override
	public Boolean delCardClazz(String id) {
		CardClazz cc=baseDao.get(id, CardClazz.class);
		if(cc.getCardEquities()==null||cc.getCardEquities().isEmpty()) {
			baseDao.delete(cc);
			return true;
		}else {
			cc.setState(false);
			baseDao.save(cc);
		}
		return false;
	}
	
	/**
	 * 卡商品取消关联
	 * @param id
	 * @return
	 */
	@Override
	public Boolean delCradCommodityLink(String id) {
		CommodityCard cc=baseDao.get(id, CommodityCard.class);
		if(cc.getId() != null) {
			baseDao.delete(cc);
			return true;
		}
		return false;
	}
	
	

}
