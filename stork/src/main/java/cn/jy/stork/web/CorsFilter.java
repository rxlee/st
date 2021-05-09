package cn.jy.stork.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * web过滤器，处理跨域和自定义头部
 * 
 * @author jsh
 */
//@Component
public class CorsFilter implements Filter{// 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:9527");// 测试环境跨域，生产环境不给跨
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		// x-token是项目专用的
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, x-requested-with, X-Custom-Header, Authorization, X-Token");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}
