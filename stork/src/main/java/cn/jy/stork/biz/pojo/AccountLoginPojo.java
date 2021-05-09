package cn.jy.stork.biz.pojo;

import cn.jy.stork.util.AJsonedObject;

/**
 * 用户登录的信息
 * 
 * @author jsh
 *
 */
public class AccountLoginPojo extends AJsonedObject {
	/**
	 * 登录名，按业务功能设计，实际是用手机号码登录。但在这里并不声明为telephone，它合理的语义不是手机号码而是登录名。
	 */
	private String username;
	/**
	 * 登录密码
	 */
	private String password;

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

}
