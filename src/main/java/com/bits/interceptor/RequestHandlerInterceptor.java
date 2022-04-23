package com.bits.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@SuppressWarnings("deprecation")
@Component
public class RequestHandlerInterceptor extends HandlerInterceptorAdapter{
	
	@Value("${bits.app.jwtSecret}")
	private String jwtSecret;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("HELLO###############################################"+jwtSecret);
		final String authorizationHeaderValue = request.getHeader("Authorization");
	    if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
	      String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
	      System.out.println("###"+token);
	    }
		return super.preHandle(request, response, handler);
	}
	


}