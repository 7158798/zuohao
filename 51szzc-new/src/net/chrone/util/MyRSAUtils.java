package net.chrone.util;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;




/**
 * 
 * @author zj
 * 
 * @date:2016年4月8日上午11:51:36
 */
public final class MyRSAUtils {
	
	private static String RSA = "RSA";
	
	public static final String  MD5_SIGN_ALGORITHM = "MD5withRSA";
	
	public static final String  SHA1_SIGN_ALGORITHM = "SHA1withRSA";

	/**
	 * RSA签名
	 * @param privateKey:私钥
	 * @param plainText:待签名明文串
	 * @param algorithm:签名算法,默认MD5withRSA
	 * @return
	 */
	public static String sign(String  privateKey, String plainText, String algorithm) {
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyf = KeyFactory.getInstance(RSA);
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			Signature signet = java.security.Signature.getInstance(algorithm);
			signet.initSign(prikey);
			signet.update(plainText.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(signet.sign()),"UTF-8");
			
		} catch (java.lang.Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA公钥验证
	 * @param publickey:公钥
	 * @param hexSigned：签名信息
	 * @param plainText：待签名明文
	 * @param algorithm:签名算法,默认MD5withRSA
	 * @return
	 */
	public static boolean verifySignature(String publickey, String hexSigned, String plainText,String algorithm) {
		try {
			PublicKey publicKey = loadPublicKey(publickey);
			Signature signetCheck = Signature.getInstance(algorithm);
			signetCheck.initVerify(publicKey);
			signetCheck.update(plainText.getBytes());
			if (signetCheck.verify(Base64.decodeBase64(hexSigned.getBytes()))) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr.getBytes());
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	public static PrivateKey loadPrivateKey(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(privateKeyStr.getBytes());
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	
	
	public static void main(String[] args) {
		String testPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCllWbHrHxDet2PpY/RP3EH7U4c+N0YozsX/T43hdrH4LuM4T8K+aUlCGpqfRAxZdlHjaXbZycPA/ZQl69YGoWDCzc0db/ZcmIPCR2VGhDvxOQmycQoCRx69Z9lBIzbfzugYOgHx5DyCl9pO3seKpGTcXl9aH1zem16izKvn4vjHbeBhD0Nvzrs8Rq8ZUex/rh+EpJt4ruAYm2giC1H+5g7BP8+ndZ8S/ZtabIy4qppe4Yk0trBgQlgkWtx5Z/wna4AeqKcjTg9K1DCJpmmtSa9tyT9+ZgsTDawmHt7EuAFT7VXYjp5AdLqTWxZoYEE5r3r7RII80b1zPdt2QJPUJo/AgMBAAECggEBAJoE/jV9G5kpEC4tsjjQ6jKVabNJSV9BdhY6WUUMHRUO1WWVny52SEbKLTIgYo+QTSthdoU+zfYJmXQdo9wvz0C1jgQeAvYgj1WtBPdL57UfT5lE1FDpKLS3BCzSrrL9BlmHykxyNUDupREdYsgMkJy1UGx2ZTqlBnCWSe9SWKxH2OuUOg6qn4by8gkcKO4kQP3iNfXwlF9HeBO+PrcWLxtgd3HQ2IscMdhGluKW3yYjyou6hHlAyPxExVJEdgq2g70XPsFdITQgN245GGq823zV80ZE8Ess/PP8Uaur72C18IEJAVT1Xxozu+eWBykNWX8ctoEWdT7F373PMrEe9wECgYEA0oWlSJSwrSHOA0B2DEcq+BhHTmX6vggT4MpNKWg3WRrw5xjZJfFFjdwOJiWt/YjoevGCedNQcHe63AtzWZeGNI2SQsMxbYV4otG8e1oj0TOhSwS/ZjayMvH/pYEKG4fGbEpu5XMSKh/7K8vwukkwr0uXiBur3xFNgNXuaWo+GrsCgYEAyVqOTSdVC6MDaCFvW6BBnQu+an9YbPEAG02ae+xPk3G31BKmrbztsdrloRx3uaf8mVAM/A9cQy93RpokkQBYlu5K5Q3JCN1RvZnvzT/7EUyZWE9D6Q5W7FuVuKXJOZ6SfEnadhcKuHKf1TNxsEuMqAoihErBVP1hGVDmOkmmsE0CgYBB9ETX/F1toE2ejy9soU9A8rpEQYbQyk8P+dE6y7+rVtlqTUqarIAR9YMpSFy+NYpAGjG6YQ2ubpRII/47b1FIXaIY8HYnzc0BlZvrOU5HWFmL2yzrpO8nLtHc1BSKk3sCqj8b+3URZXuXOQluE0gBYzlSFvk9pXoWhu9uby6NyQKBgDrzEyRNdp2gjRfIaiTGJ0+GJ6pgPBAxApn7v1W2mpmNOoeRKlFFNcXTU1U202p8Xvy3rgWBrb5RwDbgXAJDuqv9ednTjl7VBOBgmA9cQvIOnfp3wmcR9qreKVhU2TPQVAylRps6Jb5YeKjfldJKXBS6Wt6mUDHEuM/DmpPIClAlAoGBAJkbLj9G/Au4JbWZogbF9DIHKGCYHVDsHQI6ppx7tGxb3RU9RynchtOQiveYJdBuRgeNy3lqmkCSCJu9L1nV1uZXaFm7GJtPKRuJFhsZ+r9wqU6LxzwfWN0U/yzHUohpCKWKo/tXI9aKw6HiNdcpzhYf3H5PvODggO286da9c/Bs";
//		String testPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApZVmx6x8Q3rdj6WP0T9xB+1OHPjdGKM7F/0+N4Xax+C7jOE/CvmlJQhqan0QMWXZR42l22cnDwP2UJevWBqFgws3NHW/2XJiDwkdlRoQ78TkJsnEKAkcevWfZQSM2387oGDoB8eQ8gpfaTt7HiqRk3F5fWh9c3pteosyr5+L4x23gYQ9Db867PEavGVHsf64fhKSbeK7gGJtoIgtR/uYOwT/Pp3WfEv2bWmyMuKqaXuGJNLawYEJYJFrceWf8J2uAHqinI04PStQwiaZprUmvbck/fmYLEw2sJh7exLgBU+1V2I6eQHS6k1sWaGBBOa96+0SCPNG9cz3bdkCT1CaPwIDAQAB";
		String sign = sign(testPrivateKey, "account=12345678909&cardNo=6212260200081292837&cardType=1&certNo=220625199111221511&certType=00&mchntName=北京神马科技有限公司5&mobile=15011126537&orgId=000005&password=123456&pmsBankNo=102100023657&realName=张萌鑫", "MD5withRSA");
		System.out.println(sign);
//		System.out.println(verifySignature(testPublicKey, sign, "hello",MyRSAUtils.MD5_SIGN_ALGORITHM));
//		System.out.println(Base64Utils.encodeToString("123".getBytes()));
	}
}
