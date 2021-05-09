package cn.jy.stork.biz.pojo;

import cn.jy.stork.util.APageablePojo;

/**
 * 账号查询的对象，通常用于前端向后台传递用户输入
 * 
 * @author jsh
 * @deprecated 不建议使用了，前端框架不支持get带RequestBody
 */
@Deprecated
//public 
class AccountQueryPojo extends APageablePojo {
	private String name;
	private String role;
	private Integer status;

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getRole() {
		return role;
	}

	public final void setRole(String role) {
		this.role = role;
	}

	public final Integer getStatus() {
		return status;
	}

	public final void setStatus(Integer status) {
		this.status = status;
	}

}
