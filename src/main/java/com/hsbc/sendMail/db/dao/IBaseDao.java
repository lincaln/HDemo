package com.hsbc.sendMail.db.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.emt.card.base.Pagination;

public interface IBaseDao {

	public <T> T selectById(Serializable id, Class<T> clazz);

	public <T> Integer updateById(T entity);

	public <T> Integer deleteById(Serializable id, Class<T> clazz);

	public <T> Integer insert(T entity);

	public Pagination<Map<String, Object>> mapPagination(Pagination<Map<String, Object>> pagination, String wsql);

	public <V> Pagination<V> forPagination(Pagination<V> pagination, String wsql);

	public <V> Pagination<V> getPagination(Pagination<V> pagination, String wsql);

	public Map<String, Object> getOne(String sql);

	public <V> V forOne(String sql, Class<V> clazz);

	public <V> V getOne(String sql, Class<V> clazz);

	public <V> List<V> getList(String sql, Class<V> clazz);

	public <V> List<V> forList(String sql, Class<V> clazz);

	public List<Map<String, Object>> getList(String sql);

}
