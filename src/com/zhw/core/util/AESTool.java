package com.zhw.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准 
 * @author stone
 * @date 2014-03-10 06:49:19
 */
public class AESTool {
	public static final String KEY_ALGORITHM = "AES";
	public static final String CIPHER_AES = "AES";
	public static final String CIPHER_ECB = "AES/ECB/PKCS5Padding";
	public static final String CIPHER_CBC = "AES/CBC/PKCS5Padding";
	/* 
	 * AES/CBC/NoPadding 要求
	 * 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 * 
	 *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 *  
	 *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	public static final String CIPHER_NOPADDING = "AES/CBC/NoPadding"; 
	
    /*
     * aes加密
     */
    public static String encrypt(String sSrc, String cipher_type, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(cipher_type);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.encodeBase64String(encrypted);
    }
    /*
     * aes解密
     */
	public static String decrypt(String sSrc, String cipher_type,String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(cipher_type);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = sSrc.getBytes("utf-8");
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted1));
            return new String(original,"utf-8");
//            try {
//                
//            } catch (Exception e) {
//            	e.printStackTrace();
//                System.out.println(e.toString());
//                return null;
//            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    public static String encryptAES(String sSrc, String sKey) throws Exception {
    	return encrypt(sSrc, CIPHER_AES, sKey);
    }
    public static String decryptAES(String sSrc, String sKey) throws Exception {
    	return decrypt(sSrc, CIPHER_AES, sKey);
    }
    public static String encryptECB(String sSrc, String sKey) throws Exception {
    	return encrypt(sSrc, CIPHER_ECB, sKey);
    }
    public static String decryptECB(String sSrc, String sKey) throws Exception {
    	return decrypt(sSrc, CIPHER_ECB, sKey);
    }
    public static String encryptCBC(String sSrc, String sKey) throws Exception {
    	return encrypt(sSrc, CIPHER_CBC, sKey);
    }
    public static String decryptCBC(String sSrc, String sKey) throws Exception {
    	return decrypt(sSrc, CIPHER_CBC, sKey);
    }
    public static String encryptNOPADDING(String sSrc, String sKey) throws Exception {
    	return encrypt(sSrc, CIPHER_NOPADDING, sKey);
    }
    public static String decryptNOPADDING(String sSrc, String sKey) throws Exception {
    	return decrypt(sSrc, CIPHER_NOPADDING, sKey);
    }
    
    public static byte[] HexString2Bytes(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }
 
    public static String Bytes2HexString(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }


    
    
    
	/**
	 * 使用AES 算法 加密，默认模式  AES/ECB
	 */
    public static void method1(String str) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_AES);
		//KeyGenerator 生成aes算法密钥
		SecretKey secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes("utf-8")); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		
		System.out.println("method1-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		System.out.println("method1-解密后：" + new String(decrypt));
		
	}
	
	/**
	 * 使用AES 算法 加密，默认模式 AES/ECB/PKCS5Padding
	 */
    public static void method2(String str) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ECB);
		//KeyGenerator 生成aes算法密钥
		SecretKey secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes("utf-8")); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		
		System.out.println("method2-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		System.out.println("method2-解密后：" + new String(decrypt));
		
	}
	
	static byte[] getIV() throws UnsupportedEncodingException {
		String iv = "1234567812345678"; //IV length: must be 16 bytes long
		return iv.getBytes("utf-8");
	}
	
	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	static void method3(String str) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_CBC);
		//KeyGenerator 生成aes算法密钥
		SecretKey secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes("utf-8")); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		
		System.out.println("method3-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		System.out.println("method3-解密后：" + new String(decrypt));
		
	}
	
	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/NoPadding  参见上面对于这种mode的数据限制
	 */
	static void method4(String str) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_NOPADDING);
		//KeyGenerator 生成aes算法密钥
		SecretKey secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
		System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes("utf-8"), 0, str.length()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		
		System.out.println("method4-加密：" + Arrays.toString(encrypt));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(encrypt);
		
		System.out.println("method4-解密后：" + new String(decrypt));
		
	}
	
	
	public static void main(String[] args) throws Exception {
        String source = "a*jal)k32J8czx囙国为国宽";
        System.out.println("原文: " + source);
        String key = "A1B2C3D4E5F60708";
        String encryptData = encrypt(source,CIPHER_AES, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData,CIPHER_AES, key);
        System.out.println("解密后: " + decryptData);
		
		
		method1(source);
		method2("a*jal)k32J8czx囙国为国宽");
		method3("a*jal)k32J8czx囙国为国宽");
		
		method4("123456781234囙为国宽");// length = 16
		method4("12345678abcdefgh");// length = 16
		
	}
}