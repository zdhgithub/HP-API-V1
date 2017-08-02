package cn.heipiao.api.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSA {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String input_charset) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(input_charset));

			byte[] signed = signature.sign();

			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param ali_public_key
	 *            支付宝公钥
	 * @param input_charset
	 *            编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			byte[] encodedKey = Base64.decodeBase64(ali_public_key);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));

			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * 私钥加密
	 * @param content
	 * @param public_key
	 * @param private_key
	 * @return
	 * @throws Exception
	 */
	public static String privateEncrypt(String content, String private_key, String input_charset) throws Exception {
		PrivateKey privateKey = getPrivateKey(private_key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bs = content.getBytes(input_charset);
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		byte[] b = new byte[MAX_ENCRYPT_BLOCK];
		int count = 0;
		int len = 0;
		while((len = bis.read(b)) > 0){
			bos.write(cipher.doFinal(b, 0, len));
			count = count + len;
		}
		bis.close();
		bos.close();
		return Base64.encodeBase64String(bos.toByteArray());
	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 *            密文
	 * @param private_key
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 解密后的字符串
	 */
	public static String privateDecrypt(String content, String private_key, String input_charset) throws Exception {
		PrivateKey prikey = getPrivateKey(private_key);

		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, prikey);

		InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[MAX_DECRYPT_BLOCK];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}

		return new String(writer.toByteArray(), input_charset);
	}

	/**
	 * 公钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param public_key
	 *            密钥字符串（经过base64编码）
	 * @param input_charset
	 *            编码格式
	 * @return
	 * @throws Exception
	 */
	public static String publicEncrypt(String content, String public_key, String input_charset) throws Exception {
		PublicKey publicKey = getPublicKey(public_key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bs = content.getBytes(input_charset);
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		byte[] b = new byte[MAX_ENCRYPT_BLOCK];
		int count = 0;
		int len = 0;
		while((len = bis.read(b)) > 0){
			bos.write(cipher.doFinal(b, 0, len));
			count = count + len;
		}
		bis.close();
		bos.close();
		return Base64.encodeBase64String(bos.toByteArray());
	}

	/**
	 * 公钥解密
	 * @param content
	 * @param public_key
	 * @param input_charset
	 * @return
	 * @throws Exception
	 */
	public static String publicDecrypt(String content,String public_key,String input_charset) throws Exception{
		PublicKey publicKey = getPublicKey(public_key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(content));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[MAX_DECRYPT_BLOCK];
		int len = 0;
		int count = 0;
		while((len = bis.read(b)) > 0){
			bos.write(cipher.doFinal(b, 0, len));
			count = count + len;
		}
		bos.close();
		bis.close();
		return new String(bos.toByteArray(),input_charset);
	}
	
	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decodeBase64(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}

	/**
	 * 获取公钥
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBtes = Base64.decodeBase64(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBtes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;
	}
}
