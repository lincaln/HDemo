package com.emt.card.services;

import java.util.List;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Channel;

public interface IChannelService {

	/**
	 * 添加修改渠道
	 * @param channel
	 * @return
	 */
	Channel saveChannel(Channel channel);							
	
	/**
	 * 分页查询
	 * @param channelName
	 * @param page
	 * @return
	 */
	Pagination<Channel> getChannelPage(String channelName,PageReq page);		
	
	/**
	 * 查询所有渠道
	 * @return
	 */
	List<Channel> getChannelList();
	
	/**
	 * 渠道删除
	 * @param id
	 * @return
	 */
	boolean deleteChannelId(String id);
}
