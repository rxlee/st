package cn.jy.stork.hardware.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 字节处理的若干工具
 * 
 * @author jsh
 *
 */
public class BytesUtil {

	/**
	 * 字节数组呈现为十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String barr2Hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(String.format("%02X ", b[i]));
		}
		return sb.toString();
	}

	/**
	 * 从数组指定位置读出指定长度的数据，以原始整数表达（无小数修约）
	 * 
	 * @param b
	 * @param len  注意是字节数，以1字节为单位
	 * @param from 起始下标
	 * @return
	 */
	public static BigInteger decInBytes(byte[] b, int from, int len) {
		if (b == null || from < 0 || len < 1 || from + len > b.length) {
			throw new IllegalArgumentException(String.format("from=%d, len=%d, arr.len=%d", from, len, b.length));
		}
		int to = from + len;
		return new BigInteger(Arrays.copyOfRange(b, from, to));
	}

	/**
	 * 从指定索引处取出一个int（4字节）
	 * @param b
	 * @param from
	 * @return
	 */
	public static int intInBytes(byte[] b, int from) {
		return 	(0xff000000 & (b[from + 3] << 24)) |
				(0x00ff0000 & (b[from + 2] << 16)) |
				(0x0000ff00 & (b[from + 1] << 8))  |
				(0x000000ff &  b[from + 0]);
	}
	
	/**
	 * 从指定索引处取出一个float（4字节）
	 * @param b
	 * @param from
	 * @return
	 */
	public static float floatInBytes(byte[] b, int from) {
		return Float.intBitsToFloat(intInBytes(b, from));
	}

	/**
	 * 从数组指定位置读出指定长度的数据，并做修约
	 * 
	 * @param b
	 * @param from 起始下标
	 * @param len  注意是字节数，以1字节为单位
	 * @param exp  修约指数
	 * @return
	 */
	public static BigDecimal decInBytes(byte[] b, int from, int len, int exp) {
		return new BigDecimal(decInBytes(b, from, len)).scaleByPowerOfTen(exp);
	}

}
