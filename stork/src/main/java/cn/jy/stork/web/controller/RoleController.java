package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.service.RoleService;
import cn.jy.stork.dao.Role;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("role")
public class RoleController {
	@Autowired
	RoleService roleService;

	/**
	 * 根据账号id获取角色列表
	 * 
	 * @param accountId
	 * @return
	 */
	@GetMapping(value = "{accountId}", params = { "by=account" })
	Resp getRolesByAccountId(@PathVariable int accountId) {
		return Resp.okWithData(roleService.getRolesByAccountId(accountId));
	}

	/**
	 * 给特定账号填加一个或多个角色（原有角色会保留）
	 * 
	 * @param accountId
	 * @param roles
	 * @return
	 */
	@PostMapping(value = "{accountId}", params = { "by=account" })
	Resp addRolesToAccount(@PathVariable int accountId, @RequestBody List<String> roles) {
		if (roles.size() == 1) {
			// 单个填加
			final Role ret = roleService.addRole(accountId, roles.get(0));
			if (ret != null) {
				return Resp.okWithData(ret);
			} else {
				return Resp.fail("未知错误");
			}
		} else {
			// 批量填加
			final List<Role> ret = roleService.addRoles(accountId, roles);
			if (ret != null && ret.size() == roles.size()) {
				return Resp.okWithData(ret);
			} else {
				return Resp.fail("未知错误");
			}
		}
	}

	/**
	 * 删除指定账号的角色。如果不传递RequestBody，则删除所有（但会默认设置为guest游客）；如果传递了RequestBody，那么就只删除RequestBody中列出的角色
	 * 
	 * @param accountId
	 * @param roles
	 * @return
	 */
	@PostMapping(value = "{accountId}", params = { "op=remove", "by=account" })
	Resp removeRoles(@PathVariable int accountId, @RequestBody(required = false) List<String> roles) {
		if (roles != null && !roles.isEmpty()) {
			// 指定删除
			// TODO
		} else {
			// 全部删除
			// TODO
		}
		// TODO
		throw new UnsupportedOperationException();
	}

}
