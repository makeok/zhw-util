package com.zhw.core.db;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.zhw.core.util.SerializeUtil;

/**
 * REDIS数据访问
 * @date 2013-10-28
 * @author leilz
 */
public class RedisSupport {
	 
	private RedisTemplate<String, Serializable> template;
	
	//缓存entry过期时间(单位s)
	private static long expire = Long.parseLong("86400");
	
	public void setTemplate(RedisTemplate<String, Serializable> template) {
		this.template = template;
	}
	/**
	 * 永久保存
	 * @return
	 */
	public boolean setWithDB(final String key, Object value){

		if(key == null){
			return false;
		}
		final long expireTime = 0;
		try {
			final Serializable sv = (Serializable)value;
			return (Boolean)template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					ValueOperations<String, Serializable> ops = template.opsForValue();
					ops.set(key,sv,expireTime,TimeUnit.SECONDS);
					return true;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	
	}
	/**
	 * 以字符串键-序列化值存储
	 * @param key
	 * @param value
	 * @param time 单位s
	 * @return
	 */
	public boolean set(final String key, Object value,long time) {
		if(key == null){
			return false;
		}
		final long expireTime = time > 0 ? time : expire;
		try {
			final Serializable sv = (Serializable)value;
			return (Boolean)template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					ValueOperations<String, Serializable> ops = template.opsForValue();
					ops.set(key,sv,expireTime,TimeUnit.SECONDS);
					return true;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 以字符串键查询
	 * @param key
	 * @return
	 */
	public Serializable get(final String key) {
		try {
			return template.execute(new RedisCallback<Serializable>() {
				@Override
				public Serializable doInRedis(RedisConnection connection)
						throws DataAccessException {
					if(exists(key)){
						ValueOperations<String, Serializable> ops = template.opsForValue();
						Serializable oval = ops.get(key);
						return oval;
					}
					return null;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 是否存在指定的字符串类型的key
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		if(key == null){
			return false;
		}
		try {
			return template.hasKey(key);	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除字符串类型的key对应的数据
	 * @param key
	 */
	public void delete(String key){
		if(key == null){
			return;
		}
		try {
			template.delete(key);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存：以序列化键-序列化值存储
	 * @param key
	 * @param value
	 */
	public boolean set(final Serializable key, final Serializable value,int time) {
		if(key == null){
			return false;
		}
		final long expireTime = time > 0 ? time : expire;
		try {
			return (Boolean)template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] skey = SerializeUtil.doSerialize(key);
					byte[] sval = SerializeUtil.doSerialize(value);
					//connection.set(skey, sval);
					connection.setEx(skey, expireTime, sval);
					return true;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取：以序列化键查询
	 * @param key
	 * @return
	 */
	public Object get(final Serializable key) {
		if(key == null){
			return null;
		}
		try {
			return template.execute(new RedisCallback<Serializable>() {
				@Override
				public Serializable doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] skey = SerializeUtil.doSerialize(key);
					if(connection.exists(skey)){
						byte[] value = connection.get(skey);
						Serializable sval = SerializeUtil.doUnserialize(value);
						return sval;
					}
					return null;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除序列化的key对应的数据
	 * @param key
	 */
	public void delete(final Serializable key){
		if(key == null){
			return;
		}
		try {
			template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] skey = SerializeUtil.doSerialize(key);
					return connection.del(skey);
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否存在指定的序列化的key
	 * @param key
	 * @return
	 */
	public boolean exists(final Serializable key){
		if(key == null){
			return false;
		}
		try {
			return (Boolean)template.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] skey = SerializeUtil.doSerialize(key);
					return connection.exists(skey);
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}
	
}