package cn.jy.stork.biz.pojo;

import java.util.List;

import cn.jy.stork.util.AJsonedObject;

public class RoleCreatePojo extends AJsonedObject{
	private int accountId;
	private List<String> roles;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
