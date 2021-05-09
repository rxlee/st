package cn.jy.stork.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.event.RoleUpdateEvent;
import cn.jy.stork.biz.pojo.AccountLoginPojo;
import cn.jy.stork.dao.Account;
import cn.jy.stork.dao.AccountDao;
import cn.jy.stork.exception.DataDuplicatedException;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.util.MD5;
import cn.jy.stork.util.StorkConst;

/**
 * 用户账号service
 * 
 * @author jsh
 *
 */
@Service
public class AccountService {
	@Autowired
	AccountDao accountDao;

	@Autowired
	private ApplicationContext dispatcher;

	/**
	 * 通用条件查询
	 * 
	 * @param name
	 * @param role
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Account> find(String name, String role, Integer status, int pageIndex, Integer pageSize) {
		return accountDao.find(name, role, status, pageIndex, pageSize);
	}

	/**
	 * 根据角色查询
	 * 
	 * @param role
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Account> findByRole(String role, int pageIndex, Integer pageSize) {
		return accountDao.findByRole(role, pageIndex, pageSize);
	}

	/**
	 * 刷新一个账号的role
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	public int updateRole(int id, String role) {
		if (role == null || role.isEmpty()) {
			role = StorkConst.CommonRole.GUEST;
		}
		dispatcher.publishEvent(new RoleUpdateEvent(this, id, role));
		return accountDao.updateRole(id, role);
	}

	/**
	 * 新增一个账号
	 * 
	 * @param name      姓名
	 * @param telephone 电话，电话号码是作为登录依据的，不可重复
	 * @param password  原始密码
	 * @return
	 */
	public Account add(String name, String telephone, String password) throws DataDuplicatedException {
		return this.add(name, telephone, password, "guest");
	}

	public Account add(String name, String telephone, String password, String role) throws DataDuplicatedException {
		try {
			// 手机号码不允许重复
			findByTelephone(telephone);
			throw new DataDuplicatedException("手机号码", telephone);
		} catch (DataNotFoundException e) {
			final String salt = String.format("%08X", (long) (Math.random() * 0x100000000L));
			final String encrypted = MD5.encrypt(password, salt);
			final Account a = new Account(name, telephone, encrypted, salt);
			a.setRole(role);
			accountDao.add(a);
			return a;
		}
	}

	/**
	 * @param pojo
	 * @return 1：登录成功；0：密码不对；-1：账号不存在；-2：账号被禁用
	 */
	public int login(AccountLoginPojo pojo) {
		final String username = pojo.getUsername();
		try {
			final Account u = this.findByTelephone(username);
			if (u.getStatus() == Account.STATUS_BANNED) {
				return -2;
			}
			final String password = pojo.getPassword();
			final String salt = u.getSalt();
			final String inputEncrypted = MD5.encrypt(password, salt);
			return u.getPassword().equalsIgnoreCase(inputEncrypted) ? 1 : 0;
		} catch (DataNotFoundException e) {
			return -1;
		}
	}

	/**
	 * 用电话号码查找用户
	 * 
	 * @param telephone
	 * @return
	 */
	public Account findByTelephone(String telephone) throws DataNotFoundException {
		Account a = accountDao.findByTelephone(telephone);
		if (a == null)
			throw new DataNotFoundException("手机号码", telephone);
		return a;
	}

	/**
	 * 用id查找用户
	 * 
	 * @param telephone
	 * @return
	 * @throws DataNotFoundException
	 */
	public Account findById(int id) throws DataNotFoundException {
		Account a = accountDao.findById(id);
		if (a == null)
			throw new DataNotFoundException("id", id);
		return a;
	}
}
