package cn.jy.stork.web.controller;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.AccountCreatePojo;
import cn.jy.stork.biz.pojo.AccountLoginPojo;
import cn.jy.stork.biz.service.AccountService;
import cn.jy.stork.dao.Account;
import cn.jy.stork.exception.DataDuplicatedException;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.util.AJsonedObject;
import cn.jy.stork.util.TokenHelper;
import cn.jy.stork.util.TokenHelper.IllegalTokenException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("account")
public class AccountController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	AccountService accountService;

	/**
	 * 填加一个新用户
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("")
	public Resp add(@RequestBody AccountCreatePojo pojo) {
		try {
			return Resp.okWithData(accountService.add(pojo.getName(), pojo.getTelephone(), pojo.getPassword()));
		} catch (DataDuplicatedException e) {
			return Resp.fail("电话号码已被使用");
		}
	}

	/**
	 * 获取一个用户的信息
	 * 
	 * @return
	 */
	@GetMapping("{id}")
	public Resp getSingle(@PathVariable Integer id) {
		try {
			return Resp.okWithData(accountService.findById(id));
		} catch (DataNotFoundException e) {
			return Resp.fail();
		}
	}

	/**
	 * 多条件分页查询
	 * 
	 * @param name
	 * @param role
	 * @param status
	 * @param pageIndex 页码。缺省为0
	 * @param pageSize  每页条数。可以为null，表示不分页、取所有
	 * @return
	 */
	@GetMapping("")
	public Resp query(@RequestParam(required = false) String name, @RequestParam(required = false) String role,
			@RequestParam(required = false) Integer status,
			@RequestParam(required = false, defaultValue = "0") int pageIndex,
			@RequestParam(required = false) Integer pageSize) {
		return Resp.okWithData(accountService.find(name, role, status, pageIndex, pageSize));
	}

	/**
	 * 根据角色查找
	 * 
	 * @param role      角色，输入null则取所有
	 * @param pageIndex 页码。缺省为0
	 * @param pageSize  每页条数。可以为null，表示不分页、取所有
	 * @return
	 */
	@GetMapping(value = "", params = { "by=role" })
	public Resp queryByRole(@RequestParam(required = false) String role,
			@RequestParam(required = false, defaultValue = "0") int pageIndex,
			@RequestParam(required = false) Integer pageSize) {
		return Resp.okWithData(accountService.findByRole(role, pageIndex, pageSize));
	}

	/**
	 * 刷新一个账号的角色
	 * 
	 * @param id
	 * @param roles
	 * @return
	 */
	@PostMapping(value = "{id}", params = "op=set-role")
	public Resp setRole(@PathVariable int id, @RequestParam String roles) {
		accountService.updateRole(id, roles);
		return Resp.ok();
	}

	/**
	 * 登录
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("login")
	Resp login(@RequestBody AccountLoginPojo pojo) {
		final int login = accountService.login(pojo);
		switch (login) {
		case 1:
			// 登录成功
			return Resp.okWithData(new AJsonedObject() {
				private String token = TokenHelper.generate(pojo.getUsername());

				@SuppressWarnings("unused")
				public final String getToken() {
					return token;
				}
			});
		case 0:
			// 密码错误
			return Resp.fail("密码错误，请重试");
		case -2:
			return Resp.fail("账号被禁用");
		case -1:
			// 账号不存在
			if ("admin".equals(pojo.getUsername())) {
				// 如果admin不存在，则表示整个系统首次使用，为admin自动生成账号
				try {
					accountService.add("超级管理员", "admin", "zkcy2020", "admin");
				} catch (DataDuplicatedException e1) {
				}
				return Resp.fail("系统中未找到超级管理员账号，现已创建，请用默认密码 zkcy2020 登录，登录后尽快修改密码");
			} else {
				return Resp.fail("账号未注册");
			}
		default:
			return Resp.fail("未知错误");
		}

	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@PostMapping("logout")
	Resp logout() {
		// TODO 需要做适当处理，有待实现
		logger.debug("退出登录需要做适当处理，有待实现");
		return Resp.ok();
	}

	/**
	 * 获取用户信息。vue前端需要（登录后获取角色等）。
	 * 
	 * @param token 前端token，目前是用户名加有效期
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("info")
	Resp info(@RequestParam String token) {
		try {
			final TokenHelper tokenHelper = TokenHelper.instance(token);
			final String username = tokenHelper.getUsername();
			try {
				final Account a = accountService.findByTelephone(username);
				HashMap<String, Object> info = new HashMap<>();
				info.put("uid", a.getId());
				info.put("name", a.getName());
				info.put("telephone", a.getTelephone());
				info.put("avatar", a.getAvatar());
				info.put("roles", Arrays.asList(a.getRole().split(",")));
				return Resp.okWithData(info);
			} catch (DataNotFoundException e) {
				return Resp.fail(String.format("账号 %s 未在系统中注册", username));// 账号未注册，通常不会发生
			}
		} catch (IllegalTokenException e1) {
			return Resp.fail(String.format("登录凭据无效"));// token缺失或格式错误，通常不会发生
		}
	}
}
