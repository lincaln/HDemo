package com.emt.card.automaticTask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emt.card.dao.SearchFilter;
import com.emt.card.dao.SearchFilter.MyOperator;
import com.emt.card.db.entity.BaseModel_;
import com.emt.card.db.entity.Card;
import com.emt.card.db.entity.Card.CardStatus;
import com.emt.card.db.entity.Card_;
import com.emt.card.support.BaseSerivce;
import com.emt.card.support.LogUtil;

@Component
public class GetCard extends BaseSerivce{

	@Value("${spring.hasMall}")
	private Boolean hasMall;
	
	private static final String sql=
			"SELECT" + 
			"	o.user_id," + 
			"	item.count," + 
			"	item.id," + 
			"	cc.card_id" + 
			" FROM" + 
			"	mall_item item" + 
			" JOIN mall_order o ON o.id = item.order_id" + 
			" JOIN commodity_card cc ON item.commodity_id = cc.commodity_id" + 
			" LEFT JOIN (" + 
			"	SELECT" + 
			"		order_item_id FROM card" + 
			"	WHERE" + 
			"		order_item_id IS NOT NULL GROUP BY order_item_id" + 
			") cg ON " + 
			"	item.id = cg.order_item_id" + 
			" WHERE" + 
			"	((" + 
			"		item.condition_type = 2" + 
			"		AND item.is_affirm = 1" + 
			"	)" + 
			" OR (" + 
			"	item.condition_type = 1" + 
			"	AND o.order_status = 2" + 
			"))" + 
			" AND cg.order_item_id IS NULL";
	@Scheduled(cron = "0 0/10 * * * ? ")
	public void getCard() {
		if(hasMall) {
			List<Map<String, Object>> list=baseDao.getList(sql);
			for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, Object> map = iterator.next();
				try {
				Integer count=(Integer) map.get("count");
				String user_id=map.get("user_id")!=null?map.get("user_id").toString():null;
				String card_id=(String) map.get("card_id");
				String orderItemId=(String) map.get("id");
				Set<SearchFilter> sf=new HashSet<SearchFilter>();
				sf.add(new SearchFilter(Card_.CARD_CLAZZ+SearchFilter.spt+BaseModel_.ID, MyOperator.EQ, card_id));
				sf.add(new SearchFilter(Card_.USER_ID,MyOperator.isNull,null));
				List<Card> cards=baseDao.findList( sf,Sort.by(BaseModel_.CREATE_DATE),Card.class);
				
				
				if(count!=null&&cards.size()>=count.intValue()) {
					for (int i = 0; i < count; i++) {
						Card card=cards.get(i);
						card.setUserId(user_id);
						card.setOrderItemId(orderItemId);
						
						if(card.getIsNeedBinding()!=null&&card.getIsNeedBinding()) //判断是否需要绑定
							card.setCardStatus(CardStatus.nobing);
						else
							card.setCardStatus(CardStatus.effectOf);
						baseDao.save(card);
					}
				}

				}catch (Exception e) {
					LogUtil.timeEx.info("订单发卡报错id:"+map.get("id"), e);
				}
			}
		}
	}
}
