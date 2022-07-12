package com.hzl.hadoop.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * description，对称加密解密算法,可实现文字和文件加密
 * 可以开发成restfull接口供外部系统调用，解决非java系统的情况
 *
 * @author hzl 2019/08/08 11:17 PM
 */
public class AesUtil {
	private static String key = "test";

	/**
	 * 加密
	 *
	 * @param content 待加密内容
	 * @param key     加密的密钥
	 * @return
	 */
	public static String encrypt(String content, String key) {
		try {
			// 构造密钥生成器，指定为AES算法，不区分大小写
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			// ENCRYPT_MODE指加密操作
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] byteRresult = cipher.doFinal(byteContent);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteRresult.length; i++) {
				String hex = Integer.toHexString(byteRresult[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				sb.append(hex.toUpperCase());
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 解密
	 *
	 * @param content 待解密内容
	 * @param key     解密的密钥
	 * @return
	 */
	public static String decrypt(String content, String key) {
		if (content.length() < 1) {
			return null;
		}
		try {
			byte[] byteRresult = new byte[content.length() / 2];
			for (int i = 0; i < content.length() / 2; i++) {
				int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
				byteRresult[i] = (byte) (high * 16 + low);
			}
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// Decrypt_mode指解密操作
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] result = cipher.doFinal(byteRresult);
			// 不加utf-8，中文时会乱码
			return new String(result, "utf-8");
		} catch (Exception e) {
			return "";
		}
	}

	// 重载的方法，使用默认密钥
	public static String decrypt(String content) {
		return decrypt(content, key);
	}

	public static String encrypt(String content) {
		return encrypt(content, key);
	}


	/**
	 * 文件加密
	 *
	 * @param content 待加密内容
	 * @param key     加密的密钥
	 * @return
	 */
	public static String encryptFile(byte[] content, String key) {


		return null;
	}
	/**
	 * 解密
	 *
	 * @param content 待解密内容
	 * @param key     解密的密钥
	 * @return
	 */
	public static byte[] decryptFile(byte[] content, String key) {
		return null;
	}



}
