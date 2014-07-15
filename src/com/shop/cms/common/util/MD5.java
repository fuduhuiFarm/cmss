package com.shop.cms.common.util;

import java.security.MessageDigest;
import java.util.Random;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MD5 {
	private static String key = "3dns";

	public String encrypt(String strTxt, String strKey) {
		String strEncypt = "";
		int iRandNum = getRandom();
		String randKey = MD5Encode(Integer.toString(iRandNum));
		int ctr = 0;
		int num = strTxt.length();
		int num1 = randKey.length();
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < num; i++) {
			if (ctr == num1)
				ctr = 0;
			char cc1 = randKey.charAt(ctr);
			temp.append(cc1);
			char cc2 = strTxt.charAt(i);
			int cc4 = cc1 ^ cc2;
			char cc3 = (char) cc4;
			temp.append(cc3);
			ctr++;
		}

		String strEnc = temp.toString();
		strEncypt = getKeyEd(strEnc, strKey);
		strEncypt = setFromBASE64(strEncypt).trim();

		return strEncypt;
	}

	public String decrypt(String strEncTxt, String strKey) {
		String strDecryt = "";
		strEncTxt = getFromBASE64(strEncTxt);
		strEncTxt = getKeyEd(strEncTxt, strKey);

		StringBuffer temp = new StringBuffer();
		char temp1 = '\000';
		int num = strEncTxt.length();
		for (int i = 0; i < num; i++)
			try {
				temp1 = strEncTxt.charAt(i);
				i++;
				if (i >= num)
					break;
				char cc1 = strEncTxt.charAt(i);
				int cc2 = cc1 ^ temp1;
				char cc3 = (char) cc2;
				temp = temp.append(cc3);
			} catch (Exception localException) {
			}
		strDecryt = temp.toString().trim();
		return strDecryt;
	}

	public String getKeyEd(String strTxt, String strKey) {
		strKey = MD5Encode(strKey);
		int m = 0;
		int n = strKey.length();
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < strTxt.length(); i++) {
			if (m == n) {
				m = 0;
			}
			char cc1 = strKey.charAt(m);
			int iAscii = cc1 ^ strTxt.charAt(i);
			char cc2 = (char) iAscii;
			temp.append(cc2);
			m++;
		}
		return String.valueOf(temp.toString());
	}

	public int getRandom() {
		Random generator = new Random();
		int limit = 320000;
		int randomNub = 1;
		boolean j = true;
		while (j) {
			randomNub = (int) (generator.nextDouble() * limit);
			if (randomNub > 10)
				j = false;
		}
		return randomNub;
	}

	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception localException) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public String getFromBASE64(String s) {
		if ((s == null) || (s.length() <= 0))
			return "";
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
		}
		return "";
	}

	public String setFromBASE64(String s) {
		if ((s == null) || (s.length() <= 0))
			return "";
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			String b = encoder.encodeBuffer(s.getBytes());
			return b;
		} catch (Exception e) {
		}
		return "";
	}
	
	// 加密

	public static String md5encode(String str) {
		try {
			MD5 md5 = new MD5();
			return md5.encrypt(str, key);

		} catch (Exception e) {
			e.getStackTrace();
			return str;
		}
	}
	
	// 解密

	public static String md5decode(String str) {
		try {
			MD5 md5 = new MD5();
			return md5.decrypt(str, key);
		} catch (Exception e) {
			e.getStackTrace();
			return str;
		}
	}

	public static void main(String[] args) {
		MD5 m = new MD5();
		String text = "3dns";
		String key = "3dns";
		String encr = m.encrypt(text, key);
		System.out.println("密文�?" + encr);

		String dncr = m.decrypt(encr, key);
		System.out.println("解密�?" + dncr);

	}
}
