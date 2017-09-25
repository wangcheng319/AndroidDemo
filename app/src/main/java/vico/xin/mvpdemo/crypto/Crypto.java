package vico.xin.mvpdemo.crypto;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 可使用configuration.getProperty("application.secret")获取应用密钥作为secret参数
 */
public class Crypto {
    static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

     /**
     * Define a hash type enumeration for strong-typing
     */
    public enum HashType {
        MD5("MD5"),
        SHA1("SHA-1"),
        SHA256("SHA-256"),
        SHA512("SHA-512");
        private String algorithm;
        HashType(String algorithm) { this.algorithm = algorithm; }
        @Override
		public String toString() { return this.algorithm; }
    }

	public static class StringSign {
		private String secretKey;
		private int hashLength;
        
		private StringSign(){}
        
		public String sign(String message) throws UnsupportedEncodingException {
//			String sign = Crypto.sign(message, secretKey.getBytes(Charsets.UTF_8));
			
			String sign = Crypto.sign(message, secretKey.getBytes("UTF-8"));
			
			return hashLength <= 0 ? sign : sign.substring(0, hashLength);
		}

		public boolean verify(String message, String sign) throws UnsupportedEncodingException {
//			if (Strings.isNullOrEmpty(message)) {
//				return false;
//			}
			
			if (TextUtils.isEmpty(message)){
				return false;
			}
			
			return sign.equals(sign(message));
		}
	}

	/**
	 * 
	 * Create a password hash using specific hashing algorithm
	 * 
	 * @param input
	 *            The password
	 * @param hashType
	 *            The hashing algorithm
	 * @return The password hash
	 * @throws UnsupportedEncodingException
	 */
	public static String passwordHash(String input, HashType hashType) throws UnsupportedEncodingException
	{
		try {
			MessageDigest m = MessageDigest.getInstance(hashType.toString());
			//chen56:use UTF-8
			byte[] out = m.digest(input.getBytes("UTF-8"));
			//old
//			byte[] out = m.digest(input.getBytes(Charsets.UTF_8));
//			return BaseEncoding.base64().encode(out);
			return Codec2.encodeBase64(out);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
    /**
     * chen56:copy from playframework
     *
     * Sign a message with a key
     * @param message The message to sign
     * @param secretKey The secretKey to use
     * @return The signed message (in hexadecimal)
     * @throws UnsupportedEncodingException
     */
    public static String sign(String message, String secretKey) throws UnsupportedEncodingException {
    	return sign(message,secretKey.getBytes("UTF-8"));
    }
    /**
     * chen56:copy from playframework
     *
     * Sign a message with a key
     * @param message The message to sign
     * @param key The key to use
     * @return The signed message (in hexadecimal)
     */
    public static String sign(String message, byte[] secretKey) {

        if (secretKey.length == 0) {
            return message;
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA1");
            mac.init(signingKey);
            byte[] messageBytes = message.getBytes("utf-8");
            byte[] result = mac.doFinal(messageBytes);
            int len = result.length;
            char[] hexChars = new char[len * 2];


            for (int charIndex = 0, startIndex = 0; charIndex < hexChars.length;) {
                int bite = result[startIndex++] & 0xff;
                hexChars[charIndex++] = HEX_CHARS[bite >> 4];
                hexChars[charIndex++] = HEX_CHARS[bite & 0xf];
            }
            return new String(hexChars);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
	public static StringSign newSign(String secretKey, int hashLength) {
		StringSign result = new StringSign();
		result.secretKey = secretKey;
		result.hashLength = hashLength;
		return result;
	}
	public static StringSign newSignWithUnlimitLength(String secretKey) {
		return newSign(secretKey, -1);
	}

    /**
     * Encrypt a String with the AES encryption standard. Private key must have a length of 16 bytes
     * @param value The String to encrypt
     * @param privateKey The key used to encrypt
     * @return An hexadecimal encrypted string
     */
    public static String encryptAES(String value, String privateKey) {
        try {
            byte[] raw = privateKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return Codec2.byteToHexString(cipher.doFinal(value.getBytes("UTF-8")));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * Decrypt a String with the AES encryption standard. Private key must have a length of 16 bytes
     * @param value An hexadecimal encrypted string
     * @param privateKey The key used to encrypt
     * @return The decrypted String
     */
    public static String decryptAES(String value, String privateKey) {
        try {
            byte[] raw = privateKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(Codec2.hexStringToByte(value)),"UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
	public static String toMD5(String message) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			byte[] out = m.digest(message.getBytes());
			StringBuffer buffer = new StringBuffer(0);
			for (int i = 0; i < out.length; i++) {
				buffer.append(toHex(out[i]));
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte one) {
		String HEX = "0123456789ABCDEF";
		char[] result = new char[2];
		result[0] = HEX.charAt((one & 0xf0) >> 4);
		result[1] = HEX.charAt(one & 0x0f);
		return new String(result);
	}
}