package com.zhw.core.feature.license;

import java.io.Console;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zhw.core.net.LocalhostUtil;
import com.zhw.core.util.Base64Utils;
import com.zhw.core.util.FileUtil;
import com.zhw.core.util.IOUtil;
import com.zhw.core.util.MyDateUtil;
import com.zhw.core.util.MyMathUtil;
import com.zhw.core.util.MyPropertyUtil;
import com.zhw.core.util.MyStringUtil;
import com.zhw.core.util.RSAUtil;  

/** 
 * 生成license 
 * @author happyqing 
 * 2014.6.15 
 */  
public class LicenseGenerator {  
      
    /** 
     * serial：由客户提供 
     * timeEnd：过期时间 
     */  
    private static String licensestatic = "serial=568cf3a5cdf1402623bda1d8ab7b7b34;" +  
                                          "timeEnd=1404057600000";  
      
    private static final String publicKey = 
    		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDV4uC5iRNAFc+jKaYMVseS48sqAKOvOHU5fXxl\n"  
            + "/MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANXi4LmJE0AVz6MppgxWx5LjyyoA\n"  
            + "uA6ALe+fh6qcktaI/w9DXy2YWYCotcTwS4edHHormX9TumxQ5ZKUqe5IBQIDAQAB";  
      
    /** 
     * RSA算法 
     * 公钥和私钥是一对，此处只用私钥加密 
     */  
    public static final String privateKey = 
    		"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANXi4LmJE0AVz6MppgxWx5LjyyoA\n"
    		+ "o684dTl9fGX9zkc47VQORTkFgEvYwvHUZMgV9vIBpk4odbRUHSPss0AFfZf2L09ltkGoD0q3aPno\n"
    		+ "mwPAPEKyIxa4DoAt75+HqpyS1oj/D0NfLZhZgKi1xPBLh50ceiuZf1O6bFDlkpSp7kgFAgMBAAEC\n"
    		+ "gYBncWLeWL1GsUXK08nLDe1XzXPkzIpdpCCNvXL8oNOy/oVJsd0oxtBV3m/qJZ7YZkcYpFOaWVcT\n"
    		+ "79poFYJb5Evo39kDL+1D2lF1hm/ZsEQg0kHAscmRWUBShklHFE69FqwHOyUohBGtxj2WsRAwPmsW\n"
    		+ "3eLHqi14QnRniymrcMPCAQJBAPx2czwTO9R8hE8C9bRPaKGNwXiyfs0nqolFiAD2f5b0BMdiHkbD\n"
    		+ "kZ/4rCmBaNYxK+9EjufBg2L7W9BX9+fCgHkCQQDY4g8ip6T5Q9F1Jgw3J+4pYwMR0DJSZkmBzo4t\n"
    		+ "c5HOO1UDkU5BYBL2MLx1GTIFfbyAaZOKHW0VB0j7LNABX2X9i9PZbZBqA9Kt2j56JsDwDxCsiMW\n"
    		+ "TZIAgIHLnidSt/J+AXcYm6uULWkpiKKamlo+VZ7hV3uyGRho1TfkLLs0SucBgQJAHJbYBepxbcZJ\n"
    		+ "0xZ7371swJnjqrJ2H7SKFgljZoT4Y2NGtBRWelzZpycjFD8ej0jTG7Yl7/IFFhxtgyuEtdIEFQJA\n"
    		+ "DapPqXgCfIYeS1p6uvi5NSySrbHgFGyYZbBnOhn4WNU6x9sqatXtMRMYpQYTIBeEn1PU+X047lQN\n"
    		+ "u37JqW5cNQ==";
      
    public static void generator() throws Exception {  
        System.err.println("私钥加密——公钥解密");  
        //String source = "568b8fa5cdfd8a2623bda1d8ab7b7b34";  
        System.out.println("原文字：\r\n" + licensestatic);  
        byte[] data = licensestatic.getBytes();  
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey); 
        String encodeStr = Base64Utils.encode(encodedData);
        System.out.println("加密后：\r\n" + encodeStr); //加密后乱码是正常的  
          
        FileUtil.writeFile(FileUtil.getBasePath()+File.separator+"license.dat", encodeStr);
//        Base64Utils.byteArrayToFile(encodeStr, FileUtil.getBasePath()+File.separator+"license.dat");  
        System.out.println("license.dat：\r\n" + FileUtil.getBasePath()+File.separator+"license.dat");  
          
        //解密  
        byte[] decodedData = RSAUtil.decryptByPublicKey(Base64Utils.decode(encodeStr), publicKey);  
        String target = new String(decodedData);  
        System.out.println("解密后: \r\n" + target);
    } 
    
    public static String encodeLicense(String info) throws Exception {
    	byte[] data = info.getBytes();  
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey); 
        String encodeStr = Base64Utils.encode(encodedData);
        return encodeStr;
    }
    public static String decodeLicense(String encodeStr) throws Exception {
    	byte[] decodedData = RSAUtil.decryptByPublicKey(Base64Utils.decode(encodeStr), publicKey);  
        String target = new String(decodedData);
        return target;
    }
    public static String createLicense(String user,String year,String mac,
    		String ip ,String serial){
    	try {
    		Map<String, Object> data = new HashMap<>();
        	data.put("user", user);
        	data.put("mac", mac);
        	data.put("ip", ip);
        	data.put("serial", serial);
			data.put("timeEnd", MyDateUtil.parse_df10(year).getTime());
			String info = MyPropertyUtil.createProperty(data );
			System.out.println(info);
			return encodeLicense(info);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	return null;
    }
    public static String createLicense(){
    	String passwd = null;
    	Console console = System.console();
    	if(console!=null){
    		char[] password = console.readPassword("Please input password:");
        	passwd = String.valueOf(password);
    	}else{
    		passwd = IOUtil.readDataFromConsole("Please input password：");
    	}    	
    	if(MyStringUtil.isEmpty(passwd)
    		|| !MyMathUtil.sha256Hex(passwd).equals("9dbbce59fcbd1583e6f650ebd90db3f4f5b599774e78b1ccf57669ce77c677c7") ){
    		System.out.println("密码不正确");
    		return null;
    	}
    	
    	String user = IOUtil.readDataFromConsole("Please input username you want to registe ：");
    	String year = IOUtil.readDataFromConsole("Please input experise yyyy-mm-dd ：");
    	String mac = LocalhostUtil.getLocalMacAddr();
    	String ip = LocalhostUtil.getLocal1stIP();
    	String serial = LocalhostUtil.getCpuSerialId();    	
    	
    	return createLicense(user,year,mac,ip,serial);
    }
    public static void showLocalInfo(){
    	String mac = LocalhostUtil.getLocalMacAddr();
    	String ip = LocalhostUtil.getLocal1stIP();
    	String serial = LocalhostUtil.getCpuSerialId(); 
    	System.out.println("mac="+mac);
    	System.out.println("ip="+ip);
    	System.out.println("serial="+serial);
    }
    public static boolean verifyLicense(String encodeStr){
		try {
			String target = decodeLicense(encodeStr);
	        Properties pro = MyPropertyUtil.parseArgs(target);
	        String user = pro.getProperty("user","");
	        String timeEnd = pro.getProperty("timeEnd","");
	        String mac = pro.getProperty("mac","");
	        String ip = pro.getProperty("ip","");
	        String serial = pro.getProperty("serial","");
	        
	    	String mac_local = LocalhostUtil.getLocalMacAddr();
	    	String ip_local = LocalhostUtil.getLocal1stIP();
	    	String serial_local = LocalhostUtil.getCpuSerialId(); 
	    	
	    	if(mac.equals(mac_local) && ip.equals(ip_local) && serial.equals(serial_local)){
	    		return true;
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return false;
    }
    public static void main(String[] args) throws Exception {  
        //generator();  
//    	String[] ips = LocalhostUtil.getAllLocalHostIP();
//    	String[] macs = LocalhostUtil.getAllLocalMac();
    	String license = createLicense();
    	System.out.println(verifyLicense(license));
    	
    }  
}  