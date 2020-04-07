package com.emt.card.services;

import java.util.Collection;
import java.util.List;

import com.emt.card.base.HosException;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.EquitiesBookingQuestion;
import com.emt.card.db.entity.EquitiesProduct;
import com.emt.card.db.entity.Facilitator;
import com.emt.card.db.entity.FacilitatorOrder;

public interface IFacilitatorOrderService {

	/**
	 * 查询所有服务权益
	 * @param clientId
	 * @return
	 */
	List<EquitiesProduct> getEquitiesProduct(String clientId);
	
	/**
	 * 权益id获取权益表单
	 * @param epId
	 * @return
	 */
	List<EquitiesBookingQuestion> getEquitiesBookingQuestion(String epId);
	
	/**
	 * 添加后台订单
	 * @param string
	 * @return
	 * @throws HosException 
	 */
	FacilitatorOrder saveFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException;
	
	/**
	 * 修改订单
	 * @param facilitatorOrder
	 * @return
	 * @throws HosException 
	 */
	FacilitatorOrder updateFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException;
	
	/**
	 * 查询订单列表分页
	 * @param page
	 * @return
	 */
	Pagination<FacilitatorOrder> getFacilitatorOrderPage(Pagination<FacilitatorOrder> page,String beginDate,
			String overDate,String orderStatus);
	
	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	FacilitatorOrder getFacilitatorOrderInfo(String id);

	/**
	 * 获取可用派发的服务商
	 * @param id
	 * @return
	 */
	Collection<Facilitator> getOrderCanUserFacilitator(String id);

	FacilitatorOrder addFacilitatorOrder(FacilitatorOrder facilitatorOrder) throws HosException;
	
	
	
	
}
