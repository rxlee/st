package cn.jy.stork.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类，内置了加盐操作
 * 
 * @author jsh
 *
 */
public final class MD5 {

	private MD5() {
	}

	public static String encrypt(String str, String salt) {
		byte[] digest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			digest = md5.digest((str + salt).getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return new BigInteger(1, digest).toString(16);
	}

}
