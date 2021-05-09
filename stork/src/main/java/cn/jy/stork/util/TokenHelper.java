package cn.jy.stork.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenHelper {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static TokenHelper UNDEFINED = new TokenHelper();

	private String username = "";
	private long expired = 0;

	private TokenHelper() {
	}

	/**
	 * 根据username生成Token，默认12小时有效
	 * 
	 * @param username
	 * @return
	 */
	public static String generate(String username) {
		return String.format("%s.%d", username, System.currentTimeMillis() + 1000 * 3600 * 12);
	}

	/**
	 * 创建token helper实例，从request中自动解析token，进一步解析出账号和有效期
	 * 
	 * @param request
	 * @return
	 * @throws IllegalTokenException
	 */
	public static TokenHelper instance(HttpServletRequest request) throws IllegalTokenException {
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if ("x-token".equalsIgnoreCase(name)) {
				return instance(request.getHeader(name));
			}
		}
		throw new IllegalTokenException();
	}

	/**
	 * 创建token helper实例，从token串中解析出账号和有效期
	 * 
	 * @param xtoken
	 * @return
	 * @throws IllegalTokenException
	 */
	public static TokenHelper instance(String xtoken) throws IllegalTokenException {
		TokenHelper th = new TokenHelper();
		String[] tokens = xtoken.split("\\.");
		if (tokens.length != 2) {
			throw new IllegalTokenException();
		}
		try {
			th.expired = Long.parseLong(tokens[1]);
		} catch (NumberFormatException e) {
			throw new IllegalTokenException();
		}
		th.username = tokens[0];
		return th;
	}

	public String getUsername() {
		return username;
	}

	public boolean isExpired() {
		return expired < System.currentTimeMillis();
	}

	public static class IllegalTokenException extends Exception {
		private static final long serialVersionUID = -7381386341852242013L;
	}
}
