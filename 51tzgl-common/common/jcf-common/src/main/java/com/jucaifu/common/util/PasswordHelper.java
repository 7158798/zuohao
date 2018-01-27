package com.jucaifu.common.util;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	public static String encryString(String pwd, String salt) {
	    String newPassword = new SimpleHash(algorithmName, PwdMd5Helper.getMD5_32_xx(pwd+"hello, 51SZZC"), ByteSource.Util.bytes(salt), hashIterations).toHex();

	    return newPassword;
	}
	
	public static void main(String args[]) throws Exception {
		String str = encryString("123","TEST");
		System.out.println(str);
		AesCipherService aesCipherService = new AesCipherService();
		//decrypt(Hex.decode(encrptText),key.getEncoded()).getBytes()
		// aesCipherService.decrypt(Hex.decode(str),"TEST")
		DefaultHashService hashService = new DefaultHashService();
		//hashService.
	}
}