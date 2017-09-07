package com.zhw.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class MyMathUtil {
	public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
	public static String getUUID(String name){
        return UUID.fromString(name).toString().replace("-", "");
    }   
	   /**
     * md5加密
     *
     * @param value 要加密的值
     * @return md5加密后的值
     */
    public static String md5Hex(String value) {
        return DigestUtils.md5Hex(value);
    }

    /**
     * sha1加密
     *
     * @param value 要加密的值
     * @return sha1加密后的值
     */
    public static String sha1Hex(String value) {
        return DigestUtils.sha1Hex(value);
    }

    /**
     * sha256加密
     *
     * @param value 要加密的值
     * @return sha256加密后的值
     */
    public static String sha256Hex(String value) {
        return DigestUtils.sha256Hex(value);
    }
	/**
	 * 将BigDecimal转化为字符串
	 * 
	 * @param b
	 * @param scale
	 *            小数位数
	 * @return
	 */
	public static String valueOf(BigDecimal b, int scale) {
		int n = 0;
		String s = b.setScale(scale, RoundingMode.HALF_UP).toString();
		int dotidx = s.indexOf(".");
		if (dotidx == -1) {
			s = s + ".";
			n = scale;
			dotidx = s.length() - 1;
		} else {
			n = scale - (s.length() - dotidx - 1);
		}
		if (scale == 0) {
			return s.substring(0, dotidx);
		}

		return MyStringUtil.append(s, n, '0');
	}
	/**
	 * 将整形数转化为16进制字符串
	 * 
	 * @param n
	 * @return
	 */
	public static String byte2Hex(int n) {
		final char[] HS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			int h = (n >> i * 4) & 0xf;
			sb.append(HS[h]);
		}
		return sb.toString();
	}
}
