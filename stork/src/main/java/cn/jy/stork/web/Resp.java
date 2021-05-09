package cn.jy.stork.web;

import cn.jy.stork.util.AJsonedObject;

/**
 * 专门定制的响应封装对象，要返回给前端的数据对象都用本类对象封装，数据对象放在data字段内。
 * 
 * 关于code，操作成功OK=20000，操作失败FAIL=0，token错误（格式错误或用户不存在等）ILLEGAL_TOKEN =
 * 50008，token过期超时TOKEN_EXPIRED = 50014
 * 
 * @author jsh
 *
 */
public class Resp<T> extends AJsonedObject {
	public static final int OK = 20000, FAIL = 0, ILLEGAL_TOKEN = 50008, OTHER_CLIENT_LOGGED_IN = 50012,
			TOKEN_EXPIRED = 50014;

	private int code;
	private String message;
	private T data;

	/**
	 * 生成一个简易的表示“请求成功”的对象
	 * 
	 * @return
	 */
	public static Resp<Object> ok() {
		return ok(null, (Object) null);
	}

	/**
	 * 生成一个简易的表示“请求成功”的对象，可设置“消息”message字段，数据字段data为空，代码code字段为OK=20000
	 * 
	 * @param message
	 * @return
	 */
	public static Resp<Object> okWithMessage(String message) {
		return ok(message, (Object) null);
	}

	/**
	 * 生成一个简易的表示“请求成功”的对象，可设置“数据”字段，“消息”message字段默认为“成功”
	 * 
	 * @param data
	 * @return
	 */
	public static <T> Resp<T> okWithData(T data) {
		return ok("成功", data);
	}

	/**
	 * 生成一个完整的表示“请求成功”的对象
	 * 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Resp<T> ok(String message, T data) {
		Resp<T> resp = new Resp<>();
		resp.setCode(OK);
		resp.setData(data);
		resp.setMessage(message);
		return resp;
	}

	/**
	 * 未知失败原因
	 * 
	 * @return
	 */
	public static <T> Resp<T> fail() {
		Resp<T> resp = new Resp<>();
		resp.setCode(FAIL);
		return resp;
	}

	/**
	 * 失败且已知原因
	 * 
	 * @param message
	 * @return
	 */
	public static <T> Resp<T> fail(String message) {
		Resp<T> resp = new Resp<>();
		resp.setCode(FAIL);
		resp.setMessage(message);
		return resp;
	}

	public static <T> Resp<T> illegalToken() {
		Resp<T> resp = new Resp<>();
		resp.setCode(ILLEGAL_TOKEN);
		return resp;
	}

	public static <T> Resp<T> tokenExpired() {
		Resp<T> resp = new Resp<>();
		resp.setCode(TOKEN_EXPIRED);
		return resp;
	}

	public final int getCode() {
		return code;
	}

	public final void setCode(int code) {
		this.code = code;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final T getData() {
		return data;
	}

	public final void setData(T data) {
		this.data = data;
	}

}
