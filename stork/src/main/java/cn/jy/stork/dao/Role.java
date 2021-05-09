package cn.jy.stork.dao;

import cn.jy.stork.util.AJsonedObject;

/**
 * 用户的角色，每个用户accountId可以有多个角色
 * 
 * @author jsh
 *
 */
public class Role extends AJsonedObject {

	private Integer id;
	private Integer accountId;
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
