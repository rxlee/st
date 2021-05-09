package cn.jy.stork.biz.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户角色变化的事件，通常由角色设置的模块派发，其他业务模块如果与角色相关，需要监听此事件并做处理
 * 
 * @author jsh
 *
 */
public class RoleUpdateEvent extends ApplicationEvent {
	private static final long serialVersionUID = 3214747773201580772L;

	private int accountId;
	private String roles;

	public RoleUpdateEvent(Object source, int accountId, String roles) {
		super(source);
		this.accountId = accountId;
		this.roles = roles;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
