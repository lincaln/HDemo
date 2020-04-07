package com.emt.card.support;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.emt.card.base.HosException;
import com.emt.card.base.JedisBean;
import com.emt.card.base.Token;
import com.emt.card.utils.Constants;
import com.emt.card.utils.DateUtil;
import com.emt.card.utils.MD5Util;

@Component
public class TokenUtil {

	public enum Platform{
		pc,WechatApplet
	}
	
	@Value("${spring.redis.host}")
	private String redisHost;
	private static JedisBean<Token> tokenMap;
	
	@PostConstruct
	private void getUserToken() {
		tokenMap=new JedisBean<Token>(this.redisHost,null,Token.class);
    }

	/**
	 * 检查秘钥
	 * @param clientId 用户id
	 * @param platform 平台类型
	 * @param time 时间戳
	 * @param sign 签名
	 * @throws HosException
	 */
	public static Token isAccessToken(String clientId, String platform,String time,String sign ) throws HosException{

		if (tokenMap.containsKey(clientId + "," + platform)) {
			//判断Redis中token是否存在，存在的话就获取
			Token token =  tokenMap.get(clientId + "," + platform);

			StringBuffer stringBuffer = new StringBuffer("clientId=").append(clientId)
					.append("&platform=").append(platform).append("&time=").append(time)
					.append("&token=").append(token.getValue());
			String check = MD5Util.MD5(stringBuffer.toString()).toLowerCase();

			if (check.equals(sign)){
				Long now =new Date().getTime();
				if(now<token.getTime()) {//token尚未过期，更新token
					Long at=DateUtil.getAfterDateByDays(1).getTime();
					token.setTime(at);
					tokenMap.put(clientId+","+platform, token);
					return token;
				}else {
					throw new HosException("token过期",Constants.ERROR_CODE.TOKEN_ERROR) ;
				}
			}else {
				throw new HosException("token不正确",Constants.ERROR_CODE.TOKEN_ERROR) ;
			}

		} else {
			throw new HosException("用户没登录",Constants.ERROR_CODE.TOKEN_ERROR) ;
		}

	}
	
	/**
	 * 获取秘钥
	 * @return
	 * @throws Exception
	 */
	public static String getClientAccessToken(String clientId,Platform platform) throws HosException{
		
		if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(platform)) {
			throw new HosException("参数缺失,clientId:"+clientId+",platform:"+platform
					,Constants.ERROR_CODE.HAD_ERROR);
		}
	
			String token=UUID.randomUUID().toString();
			Long time=DateUtil.getAfterDateByDays(1).getTime();
			Token t=new Token(token, time);
			tokenMap.put(clientId+","+platform.name(), t);
			return token;


	}
}
