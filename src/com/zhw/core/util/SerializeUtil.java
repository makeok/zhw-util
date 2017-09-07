package com.zhw.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化工具类
 * @date 2013-10-28
 * @author leilz
 */
public class SerializeUtil {
 
	/**
	 * 序列化一个对象
	 * @param obj
	 * @return
	 */
	public static byte[] doSerialize(Serializable obj){
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 反序列化一个对象
	 * @param bytes
	 * @return
	 */
	public static Serializable doUnserialize(byte[] bytes){
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return (Serializable)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bais.close();
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
