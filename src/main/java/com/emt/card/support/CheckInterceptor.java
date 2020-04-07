package com.emt.card.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.emt.card.base.HosException;
import com.emt.card.base.MyResult;
import com.emt.card.base.UserHandler;
import com.emt.card.utils.Constants;
import com.emt.card.utils.WebUtil;


public class CheckInterceptor implements AsyncHandlerInterceptor{

	private static final Boolean activate=
			"Linux".equals(System.getProperty("os.name"));

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler)throws Exception{

		response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
        response.addHeader("Access-Control-Allow-Headers","authorization");
        response.addHeader("Access-Control-Allow-Headers","Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if (handler == null)
			return true;
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			UserHandler userHandler = method.getAnnotation(UserHandler.class);
				try {

					if(activate && (userHandler!=null && userHandler.tokenCheck())){
						String clientId=WebUtil.getParameter(request, "clientId");
						String platform=WebUtil.getParameter(request, "platform");
						String sign=WebUtil.getParameter(request, "sign");
						String time=WebUtil.getParameter(request, "time");
						TokenUtil.isAccessToken(clientId, platform,time,sign);
					}
				} catch (HosException e) {
					response.setStatus(e.getCode());
					if(e instanceof HosException) {
					returnJson(response, e.getMessage());
					return false;
					}
				}
			}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler,ModelAndView modelAndView) throws Exception {
		System.out.println(handler.toString());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex)
			throws Exception {
		
		if(ex!=null) {
			
			if(ex instanceof RuntimeException) {
				response.setStatus(Constants.ERROR_CODE.DATA_ERROR);
				String em=ex.getMessage();
				if(em!=null) {
					String[] es=em.split("MySQLIntegrityConstraintViolationException:");
					if(es.length>1) {
						String[] ec=es[1].split("\\r\\n\\t");
						returnJson(response, "Exception:"+ec[0]);
						return;
					}
				}
			}
		
			if(ex instanceof HosException) {
				HosException e=(HosException) ex;
				response.setStatus(e.getCode());
				returnJson(response, ex.getMessage());
			}else{
				response.setStatus(500);
				LogUtil.Exception.info(request.getServletPath(),ex);
				returnJson(response, "Exception:"+ex.getMessage());
			}
			
		}
		
	}


	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler)throws Exception {
		System.out.println("WrongCodeInterceptor, afterConcurrentHandlingStarted......");
		
	}
	
	private void returnJson(HttpServletResponse response,String str){
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		MyResult<Object> result = new MyResult<Object>();
		result.setMessage(str);
		result.setCode(response.getStatus());
		try {
		PrintWriter writer = response.getWriter();
		writer.write(result.toString());
		writer.flush();
		writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
