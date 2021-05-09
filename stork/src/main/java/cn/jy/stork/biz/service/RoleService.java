package cn.jy.stork.biz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.dao.Role;
import cn.jy.stork.dao.RoleDao;

@Service
public class RoleService {
	@Autowired
	RoleDao roleDao;

	/**
	 * 根据账号id获取角色
	 * 
	 * @param accountId
	 * @return
	 */
	public List<String> getRolesByAccountId(int accountId) {
		return roleDao.findByAccountId(accountId);
	}

	/**
	 * 添加一个用户的一个角色
	 * 
	 * @param accountId
	 * @param role
	 * @return
	 */
	public Role addRole(int accountId, String role) {
		// TODO 目前只是无脑插入，还需要查重原来的角色，避免角色重复
		final Role entity = new Role();
		entity.setAccountId(accountId);
		entity.setRole(role);
		if (roleDao.add(entity) == 1) {
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 给特定用户添加一批角色
	 * 
	 * @param accountId
	 * @param roles
	 * @return
	 */
	public List<Role> addRoles(int accountId, List<String> roles) {
		// TODO 目前只是无脑插入，还需要查重原来的角色，避免角色重复
		final List<Role> entities = new ArrayList<>(roles.size());
		for (String r : roles) {
			final Role entity = new Role();
			entity.setAccountId(accountId);
			entity.setRole(r);
			entities.add(entity);
		}
		if (roleDao.addBatch(entities) == roles.size()) {
			return entities;
		} else {
			return null;
		}
	}
}
