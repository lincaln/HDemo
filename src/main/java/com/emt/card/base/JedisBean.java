package com.emt.card.base;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;



public class JedisBean<T>{
	

	private String host;
	private Class<T> clazz;
	private Integer index;
	private String table;



	public JedisBean() {
		super();
	}

	private void setDb(Jedis jedis) {
		if(index!=null) {
			jedis.select(index);
		}
	}


	public JedisBean(String host, Integer index,Class<T> clazz) {
		super();
		this.host = host;
		this.index = index;
		this.clazz = (Class<T>) clazz;
	}
	public JedisBean(String host, Integer index, String table,Class<T> clazz) {
		super();
		this.host = host;
		this.index = index;
		this.table = table;
		this.clazz = (Class<T>) clazz;
	}



	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void set(String key,String value) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		jedis.set(key, value);
		jedis.close();
	}

	public void put(String key,T value) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		String vs=JSONObject.toJSONString(value);
		if(table!=null)key=table+":"+key;
		jedis.set(key, vs);
		jedis.close();
	}

	public void puts(String key,List<T> value) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		String vs=JSONArray.toJSONString(value);
		if(table!=null)key=table+":"+key;
		jedis.set(key, vs);
		jedis.close();
	}
	
	public T get(String key) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		String vs=jedis.get(key);
		if(vs!=null) {
		T value=JSONObject.parseObject(vs, clazz);
		jedis.close();
		return value;
		}else {
			jedis.close();
			return null;
		}
	}
	public String value(String key) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		String vs=jedis.get(key);
		if(vs!=null) {
		jedis.close();
		return vs;
		}else {
			jedis.close();
			return null;
		}
	}
	public  List<T> gets(String key) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		String vs=jedis.get(key);
		List<T> value=JSONArray.parseArray(vs, clazz);
		jedis.close();
		return value;
	}
	
	public boolean containsKey(String key) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		Boolean b= jedis.exists(key);
		jedis.close();
		return b;
	}
	
	public Long remove(String key) {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		if(table!=null)key=table+":"+key;
		Long b= jedis.del(key);
		jedis.close();
		return b;
	}
	public Set<String> keys() {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		Set<String> keys=
				jedis.keys(table!=null?table+":*":"*");
		if(table!=null) {
			for (String key : keys) {
				key.replaceAll(table+":*", "");
			}
		}
		jedis.close();
		return keys;
	}
	public Set<T> values() {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		Set<String> keys=
				jedis.keys(table!=null?table+":*":"*");
		Set<T> values=new LinkedHashSet<T>();
		if(values!=null) {
			for (String key : keys) {
				try {
				if(clazz!=null) {
				String vs=jedis.get(key);
				T value=JSONObject.parseObject(vs, clazz);
				values.add(value);
				}
				}catch (Exception e) {}
			}
		}
		jedis.close();
		return values;
	}
	public String flushDB() {
		Jedis jedis=new Jedis(host);
		setDb(jedis);
		String b=jedis.flushDB();
		jedis.close();
		return b;
	}
	public Jedis getJedis(){
		try {
			Jedis jm=new Jedis(host);
		setDb(jm);
		return	jm;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
