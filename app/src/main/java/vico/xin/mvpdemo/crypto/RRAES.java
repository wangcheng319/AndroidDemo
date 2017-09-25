package vico.xin.mvpdemo.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class RRAES {

	private static final String privateKey = "oaAt#2rnrnr@1#93";

	public static String encryptAES(String value) {
		return encryptAES(value, privateKey);
	}

	public static String encryptAES(String value, String privateKey) {
		try {
			byte[] raw = privateKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return Codec2.byteToHexString(cipher.doFinal(value.getBytes()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String decryptAES(String value) {
		return decryptAES(value, privateKey);
	}

	public static String decryptAES(String value, String privateKey) {
		try {
			byte[] raw = privateKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(Codec2.hexStringToByte(value)));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
