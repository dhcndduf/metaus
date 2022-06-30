package com.metaus.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
//public class LoginInterceptor implements HandlerInterceptor{
public class AdminLoginInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger
	=LoggerFactory.getLogger(AdminLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle() 실행!- 컨트롤러 실행 전");
		/*
		- 컨트롤러 수행이전에 먼저 수행되는 메서드
		- 클라이언트의 요청을 컨트롤러에 전달하기 전에 호출됨*/
		
		String isLogin=(String) request.getSession().getAttribute("isLogin");
		logger.info("isLogin = {}", isLogin);
		
		//로그인 안된 경우
		if(!("admin").equals(isLogin)) {
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('로그인이 필요한 서비스입니다.');");
			out.print("location.href='"+request.getContextPath()
				+ "/admin/login/adminLogin';");			
			out.print("</script>");
			
			return false;
		}
		
		return true; //다음 컨트롤러가 수행됨
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle() 실행! - 컨트롤러 실행 후");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("afterCompletion() 실행! - 뷰 생성 후");
	}
	
}