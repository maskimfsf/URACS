package com.tavenli.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

/**
 * 自己实现的过滤用户请求类，也可以直接使用 FilterSecurityInterceptor
 * 
 * AbstractSecurityInterceptor有三个派生类：
 * FilterSecurityInterceptor，负责处理FilterInvocation，实现对URL资源的拦截。
 * MethodSecurityInterceptor，负责处理MethodInvocation，实现对方法调用的拦截。
 * AspectJSecurityInterceptor，负责处理JoinPoint，主要是用于对切面方法(AOP)调用的拦截。
 * 
 * @author Taven
 *
 */
public class WebSecurityFilter extends AbstractSecurityInterceptor implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(WebSecurityFilter.class);
	
	private WebSecurityMetadataSource webSecurityMetadataSource;

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		FilterInvocation fi = new FilterInvocation(request, response, chain);

		logger.debug("requestURL:"+fi.getRequestUrl());
		
		//在执行doFilter之前，进行权限的检查		
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		
		return this.webSecurityMetadataSource;
	}

	public WebSecurityMetadataSource getWebSecurityMetadataSource() {
		return webSecurityMetadataSource;
	}

	public void setWebSecurityMetadataSource(
			WebSecurityMetadataSource webSecurityMetadataSource) {
		this.webSecurityMetadataSource = webSecurityMetadataSource;
	}
	
	

}
