package com.shop.cms.common.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypt
{
  private static final Key key;
  private static final String DESede = "DESede";

  static
  {
    KeyGenerator keyGen = null;
    try
    {
      keyGen = KeyGenerator.getInstance(DESede);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    key = keyGen.generateKey();
  }

  public static String get(Object o)
  {
    if (o == null) return "";
    String str = o.toString();
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    str = str.replaceAll("\"", "&quot;");
    str = str.replaceAll("=", "");

    return str;
  }

  public static String getNoEqual(Object o)
  {
    if (o == null)
    {
      return "";
    }

    String str = o.toString();
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    str = str.replaceAll("\"", "&quot;");
    str = str.replaceAll("script", "");
    return str;
  }

  public static String get(Object o, boolean isReturnNull)
  {
    if ((o == null) && (!isReturnNull)) return "";
    if ((o == null) && (isReturnNull)) return null;
    String str = o.toString().toLowerCase();
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    str = str.replaceAll("\"", "&quot;");
    str = str.replaceAll("script", "");
    str = str.replaceAll("=", "");

    return str;
  }
  /*�����ַ�*/
  public static String doStrByMD5(String str)
  {
    if (str == null) return null;

    MessageDigest mgd = null;
    try {
      mgd = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    mgd.update(str.getBytes());

    return byte2hex(mgd.digest());
  }
 
   
  /*�Լ����ַ���бȽ�*/
  public static boolean equalsByMD5(String strByMD5, String str)
  {
    if (strByMD5 == null) return false;
    if (str == null) return false;

    MessageDigest mgd = null;
    try {
      mgd = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    mgd.update(str.getBytes());

    return strByMD5.equals(byte2hex(mgd.digest()));
  }

  private static String byte2hex(byte[] b)
  {
    if (b == null) return "";
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++)
    {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1) hs = hs + "0" + stmp; else {
        hs = hs + stmp;
      }
    }
    return hs.toUpperCase();
  }

  public static String getEncrypt(String str)
  {
    if (str == null) return null;

    byte[] encriptText = (byte[])null;
    try
    {
      Cipher cipher = Cipher.getInstance(DESede);
      cipher.init(1, key);
      encriptText = cipher.doFinal(str.getBytes());
    }
    catch (Exception e) {
      System.out.println("Encript fail.");
    }

    return new BASE64Encoder().encode(encriptText);
  }

  public static String getDecrypt(String str)
  {
    if (str == null) return null;

    byte[] decriptText = (byte[])null;
    try
    {
      decriptText = new BASE64Decoder().decodeBuffer(str);
      Cipher cipher = Cipher.getInstance(DESede);
      cipher.init(2, key);
      decriptText = cipher.doFinal(decriptText);
    }
    catch (Exception e) {
      System.out.println("Decript fail.");
    }

    return new String(decriptText);
  }

  public static void main(String[] args)
    throws UnsupportedEncodingException
  {
    String str = "aaaaaa";
    String md5Str = doStrByMD5(str);
    System.out.println(md5Str);
    System.out.println(equalsByMD5(md5Str, "abcdef"));
  }
}
