package com.emt.card.services.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.db.entity.Channel;
import com.emt.card.services.IChannelService;
import com.emt.card.support.BaseSerivce;
import com.emt.card.utils.StringUtil;


@Service
public class ChannelServiceImpl extends BaseSerivce implements IChannelService {


	/**
	 * 渠道添加、修改
	 */
	@Override
	public Channel saveChannel(Channel channel) {
		//if(channel.getId()!=null)channel.setModifiedDate(new Date());
		channel=baseDao.save(channel);
		return channel;
	}

	@Override
	public Pagination<Channel> getChannelPage(String channelName, PageReq page) {
		
		String sql="SELECT * FROM `channel` ";
		if(StringUtil.isNotEmpty(channelName)) {
			sql +=" WHERE channel_name LIKE '%"+channelName+"%' ";
		}
		Pagination<Channel> p=new Pagination<Channel>(
				page.getIndex(), 20, "create_date", true,Channel.class);
		Pagination<Channel> pagination = baseDao.forPagination(p,sql);
		return pagination;
	}

	@Override
	public List<Channel> getChannelList() {

		//baseDao.findList(Channel.class, "create_date");
		
		return null;
	}

	@Override
	public boolean deleteChannelId(String id) {
		Channel channel=baseDao.get(id, Channel.class);
		if(channel!=null&&
				(channel.getCards()==null||channel.getCards().isEmpty())) {
		baseDao.delete(id,Channel.class);
		return true;
		}
		return false;
	}
	
	
}