package com.emt.card.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emt.card.base.MyResult;
import com.emt.card.base.PageReq;
import com.emt.card.base.Pagination;
import com.emt.card.base.UserHandler;
import com.emt.card.db.entity.Channel;
import com.emt.card.support.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="渠道管理", tags ="渠道管理")
@RestController
@RequestMapping(value = "/channel")
public class ChannelController extends BaseController{
	
	@UserHandler
	@RequestMapping(value = "/addChannel",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "渠道添加",response = Channel.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="channelName",value="渠道名",required=true,dataType="String")
	})
	public MyResult<Channel> addChannel(PageReq Page,Channel channel) {		
		channel=channelService.saveChannel(channel);		
		return new MyResult<Channel>(channel);
	}
	
	@UserHandler
	@RequestMapping(value = "/getChannelPage",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "获取渠道列表分页",response = Channel.class)
	@ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="index",value="当前页",required=true,dataType="int"),
    		@ApiImplicitParam(name="channelName",value="渠道名",required=false,dataType="String")
	})
	public MyResult<Pagination<Channel>> getChannelPage(PageReq Page,String channelName) {
		Pagination<Channel> pagination = channelService.getChannelPage(channelName, Page);
		return new MyResult<Pagination<Channel>>(pagination);
	}
	
	@UserHandler
	@RequestMapping(value = "delChannel",
	produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ApiOperation(value = "渠道删除",response = Channel.class)
    @ApiImplicitParams(value = { 
    		@ApiImplicitParam(name="id",value="渠道Id",required=true,dataType="String"),})
	public MyResult<Boolean> delChannel(PageReq Page,String id) {
		Boolean is=channelService.deleteChannelId(id);
		return new MyResult<Boolean>(is?"":"",is);
	}
	

}
