package com.bits.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebAppConfigAdapter implements WebMvcConfigurer {

	@Autowired
	RequestHandlerInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		System.out.println("this method will get invoked by container while deployment");
		System.out.println("value of interceptor is " + interceptor);
		// adding custom interceptor
		interceptorRegistry.addInterceptor(interceptor);
	}
}