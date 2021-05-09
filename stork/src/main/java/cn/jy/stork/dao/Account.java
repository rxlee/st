package cn.jy.stork.dao;

import cn.jy.stork.util.AJsonedObject;

public class Account extends AJsonedObject {

	/**
	 * 账号状态：正常
	 */
	public static final int STATUS_OK = 1;
	/**
	 * 账号状态：暂时禁用
	 */
	public static final int STATUS_BANNED = 0;

	private Integer id;
	private String telephone;
	private String name;
	private String password;
	private String salt;
	private int status;
	private String role;
	private String avatar;

	/**
	 * 默认构造器
	 */
	public Account() {
	}

	/**
	 * 构造器，带有若干初始字段
	 * 
	 * @param name      姓名
	 * @param telephone 电话号码
	 * @param password  密码，加密后的（不要传入明文）
	 * @param salt      密码加盐
	 */
	public Account(String name, String telephone, String password, String salt) {
		this.name = name;
		this.telephone = telephone;
		this.password = password;
		this.salt = salt;
		this.status = STATUS_OK;
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getTelephone() {
		return telephone;
	}

	public final void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public final int getStatus() {
		return status;
	}

	public final void setStatus(int status) {
		this.status = status;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final String getSalt() {
		return salt;
	}

	public final void setSalt(String salt) {
		this.salt = salt;
	}

	public final String getRole() {
		return role;
	}

	public final void setRole(String role) {
		this.role = role;
	}

	public final String getAvatar() {
		return avatar;
	}

	public final void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
