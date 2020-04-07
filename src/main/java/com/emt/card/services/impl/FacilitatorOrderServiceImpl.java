package com.emt.card.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.emt.card.base.HosException;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.CardEquities;
import com.emt.card.db.entity.EquitiesBookingQuestion;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.FacilitatorOrder;
import com.emt.card.db.entity.FacilitatorOrder.OrderStatus;
import com.emt.card.db.entity.FacilitatorOrder.OrderType;
import com.emt.card.db.entity.SysUser;
import com.emt.card.services.IFacilitatorOrderService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.CommUtil;
import com.emt.card.utils.StringUtil;

@Service
public class FacilitatorOrderServiceImpl extends BaseSerivce implements IFacilitatorOrderService {

	/**
	 * 查询所有服务权益
	 * @param clientId
	 * @return
	 */
	@Override
	public List<EquitiesProduct> getEquitiesProduct(String facilitatorId) {
		
		List<EquitiesProduct> equitiesProduct=null;
		
		if(StringUtil.isNotNull(facilitatorId)) {
			Facilitator facilitator = new Facilitator();
			facilitator.setId(facilitatorId);
			facilitator = baseDao.findOne(facilitator);
			if(facilitator != null)
			equitiesProduct = facilitator.getEquitiesProducts();
		}else {
			//equitiesProduct = baseDao.findList(new EquitiesProduct(), Sort.by("createDate"));
			String sql="SELECT * FROM `equities_productcard`";
			equitiesProduct = baseDao.forList(sql,EquitiesProduct.class);
		}
		return equitiesProduct;
	}

	/**
	 * 权益id获取权益表单
	 * @param epId
	 * @return
	 */
	@Override
	public List<EquitiesBookingQuestion> getEquitiesBookingQuestion(String epId) {
		
		EquitiesProduct equitiesProduct = new EquitiesProduct();
		equitiesProduct.setId(epId);
		equitiesProduct = baseDao.findOne(equitiesProduct);
		List<EquitiesBookingQuestion> equitiesBookingQuestion = equitiesProduct.getBookingQuestion();
		List<EquitiesBookingQuestion> ebqList = new ArrayList<EquitiesBookingQuestion>();
		for (EquitiesBookingQuestion equitiesBookingQuestion2 : equitiesBookingQuestion) {
			ebqList.add(equitiesBookingQuestion2);
		}
		return ebqList;
	}

	/**
	 * 添加后台订单
	 * @param facilitatorOrder
	 * @return
	 * @throws HosException 
	 */
	@Override
	public FacilitatorOrder saveFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException {
		if(facilitatorOrder.getCardId() != null && facilitatorOrder.getId() == null) {
			Card card=baseDao.get(facilitatorOrder.getCardId(), Card.class);
			if(card!=null&&card.getEndUseDate()!=null&&card.getEndUseDate().before(new Date())) {
				throw new HosException("已经超过使用时间", 220);
			}
		}
		facilitatorOrder=baseDao.save(facilitatorOrder);
		return facilitatorOrder;
	}
	
	/**
	 * 修改订单
	 * @param facilitatorOrder
	 * @return
	 * @throws HosException 
	 */
	@Override
	public FacilitatorOrder updateFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException {
		facilitatorOrder=baseDao.update(facilitatorOrder.getId(), facilitatorOrder);
		return facilitatorOrder;
	}

	/**
	 * 查询订单列表分页
	 * @param page
	 * @return
	 */
	@Override
	public Pagination<FacilitatorOrder> getFacilitatorOrderPage(Pagination<FacilitatorOrder> page,
			String beginDate,String overDate,String orderStatus) {

		String sql="SELECT * FROM facilitator_order o LEFT JOIN equities_productcard e ON e.id=o.equities_id WHERE 1=1  ";
		
		if(StringUtil.isCanSql(page.Entity().getFacilitatorId())) {
			sql +=" AND o.facilitator_id = '"+page.Entity().getFacilitatorId()+"' ";
		}
		if(StringUtil.isCanSql(page.Entity().getUserName())) {
			sql +=" AND o.user_name LIKE '%"+page.Entity().getUserName()+"%' ";
		}
		if(StringUtil.isCanSql(page.Entity().getUserPhone())) {
			sql +=" AND o.user_phone LIKE '%"+page.Entity().getUserPhone()+"%' ";
		}
		if(StringUtil.isCanSql(page.Entity().getCardNum())) {
			sql +=" AND o.card_num LIKE '%"+page.Entity().getCardNum()+"%' ";
		}
		if(StringUtil.isCanSql(page.Entity().getEquitiesName())) {
			sql +=" AND e.equities_name LIKE '%"+page.Entity().getEquitiesName()+"%' ";
		}
		if(StringUtil.isCanSql(beginDate)) {
			sql +=" AND o.create_date > '"+beginDate+"' ";
		}
		if(StringUtil.isCanSql(overDate)) {
			sql +=" AND o.create_date < '"+overDate+"'  ";
		}
		//派单状态
		if(StringUtil.isNotEmpty(page.Entity().getSendType())) {
			if(page.Entity().getSendType().equals("1"))//待派单
				sql +=" AND ISNULL(o.facilitator_id)";
			if(page.Entity().getSendType().equals("2"))//已派单
				sql +=" AND o.facilitator_id != null AND o.order_status != 2 ";
			if(page.Entity().getSendType().equals("3"))//已撤销
				sql +=" AND o.order_status = 2 ";
		}
		//订单状态 1.待使用,2.已使用,3.已取消
		if(StringUtil.isNotEmpty(orderStatus)) {
			sql +=" AND o.order_status = " + orderStatus+" ";
		}
		if(StringUtil.isNotEmpty(page.Entity().getUserId())) {
			sql +=" AND o.user_id = '" + page.Entity().getUserId()+"' ";
		}

		Pagination<FacilitatorOrder> p=new Pagination<FacilitatorOrder>(
				page.getPageIndex(), page.getPageSize(), "o.create_date", true,FacilitatorOrder.class);
		p = baseDao.forPagination(p,sql);
		//获取订单 信息
		List<FacilitatorOrder> fList = p.getList();
		for (int i = 0; i < fList.size(); i++) {
			 //获取 服务商信息
			if(StringUtil.isNotNull(fList.get(i).getFacilitatorId())) {
				Facilitator f = baseDao.find(fList.get(i).getFacilitatorId(), Facilitator.class);
				fList.get(i).setFacilitator(f);
			}
			if(StringUtil.isNotNull(fList.get(i).getEquitiesId())) {
				EquitiesProduct equities = baseDao.find(
						fList.get(i).getEquitiesId(), EquitiesProduct.class);
				fList.get(i).setEquities(equities);;
			}
			//获取派单状态
			/*
			 * if(!StringUtil.isNotNull(fList.get(i).getFacilitatorId()))
			 * fList.get(i).setSendType("待派单");
			 * if(StringUtil.isNotNull(fList.get(i).getFacilitatorId()) &&
			 * !fList.get(i).getOrderStatus().equals(OrderStatus.已取消))
			 * fList.get(i).setSendType("已派单"); else fList.get(i).setSendType("已撤销回");
			 */
		}
		p.setList(fList);
		
		return p;
	}

	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	@Override
	public FacilitatorOrder getFacilitatorOrderInfo(String id) {
		FacilitatorOrder facilitatorOrder = baseDao.find(id,FacilitatorOrder.class);
		//获取派单状态
		if(!StringUtil.isNotNull(facilitatorOrder.getFacilitatorId()))
			facilitatorOrder.setSendType("待派单");
		if(StringUtil.isNotNull(facilitatorOrder.getFacilitatorId()) 
				&& !facilitatorOrder.getOrderStatus().equals(OrderStatus.已取消))
			facilitatorOrder.setSendType("已派单");
		else
			facilitatorOrder.setSendType("已撤销回");
		
		return facilitatorOrder;
	}

	@Override
	public Collection<Facilitator> getOrderCanUserFacilitator(String id) {
		FacilitatorOrder facilitatorOrder = baseDao.find(id,FacilitatorOrder.class);
		if(facilitatorOrder.getEquities()!=null) {
			if(facilitatorOrder.getCardId()==null) {
			return facilitatorOrder.getEquities().getFacilitators();
			}else {
				CardEquities ce=new CardEquities();
				ce.setEquitiesId(facilitatorOrder.getEquitiesId());
				ce.setCardId(facilitatorOrder.getCardId());
				ce=baseDao.findOne(ce);
				return ce.getFacilitators();
			}
		}
		return null;
	}

	@Override
	public FacilitatorOrder addFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException {
		//订单号生成
		String orderNo="ono";
	    String uuid= DateFormatUtils.format(new Date(), "yyMMddHHmm")+CommUtil.getRandomNo(4);
		facilitatorOrder.setOrderNo(orderNo+uuid);
		if(!StringUtils.isEmpty(facilitatorOrder.getUserId())) {
			SysUser user=baseDao.find(facilitatorOrder.getUserId(), SysUser.class);
			if(facilitatorOrder.getUserName()==null)
			if(user.getUserName()!=null)
				facilitatorOrder.setUserName(user.getUserName());
			else
				facilitatorOrder.setUserName(user.getNickName());
			if(facilitatorOrder.getUserPhone()==null)
			facilitatorOrder.setUserPhone(user.getLoginName());
		}
		if(facilitatorOrder.getOrderType().equals(OrderType.后台订单)){
			facilitatorOrder.setServiceTimes(1);
			facilitatorOrder.setOrderStatus(OrderStatus.待使用);
			//用户预约下单
		}else if(facilitatorOrder.getOrderType().equals(OrderType.预约订单)){
			facilitatorOrder.setServiceTimes(1);
			facilitatorOrder.setOrderStatus(OrderStatus.待使用);

			CardEquities ce=baseDao.findOne(
				new CardEquities(facilitatorOrder.getCardId(),
						facilitatorOrder.getEquitiesId()));//获取权益信息

					Set<Facilitator> fs=ce.getFacilitators();
					if(fs!=null&&fs.size()==1) {
					facilitatorOrder.setFacilitatorId(fs.iterator().next().getId());
					}
					if(ce.getAffirmNumber() > 0) { 
						ce.setAffirmNumber(ce.getAffirmNumber()-
								facilitatorOrder.getServiceTimes());
					baseDao.save(ce);
					}else {
						throw new HosException("服务次数已用完",220);
					}

		}else if(facilitatorOrder.getOrderType().equals(OrderType.核销订单)) {
			/**
			 * 核销生成的订单:CardEquities的affirmNumber减serviceTimes,如果已经是少于0返回错误 .
			 */
			facilitatorOrder.setOrderStatus(OrderStatus.已使用);
			if(facilitatorOrder.getServiceTimes() == null || facilitatorOrder.getServiceTimes() == 0)
				throw new HosException("请填写核销次数",220);
			if(facilitatorOrder.getFacilitatorId()==null)
				throw new HosException("请填输入服务商id",220);
			CardEquities ce=baseDao.findOne(
					new CardEquities(facilitatorOrder.getCardId(),
							facilitatorOrder.getEquitiesId()));//获取权益信息
					
					/*Set<Facilitator> fs=ce.getFacilitators();
					if(fs!=null&&fs.size()==1) {
						facilitatorOrder.setFacilitatorId(fs.iterator().next().getId());
						}*/
					if(ce.getAffirmNumber() >= facilitatorOrder.getServiceTimes()) { 
						ce.setAffirmNumber(ce.getAffirmNumber()-
								facilitatorOrder.getServiceTimes());
						baseDao.save(ce);
					}
					else if(ce.getAffirmNumber() > 0)
						throw new HosException("服务次数不够,剩余"+ce.getAffirmNumber(),220);
					else 
						throw new HosException("服务次数已用完",220);
		
		}
		facilitatorOrder=saveFacilitatorOrder(facilitatorOrder);
		return facilitatorOrder;
	}



}
