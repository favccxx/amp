package com.favccxx.amp.db.base.service;

import java.util.List;

/**
 * 通用接口
 * 
 * @author favccxx
 *
 * @param <T>
 */
public interface BaseService<T> {

	T findOne(long id);

	T save(T entity);

	void deleteById(long id);

	void delete(T entity);

	List<T> findAll();

	/**
	 * 批量删除
	 * 
	 * @param iterable
	 */
	void deleteInBatch(Iterable<T> iterable);

	/**
	 * 批量查找
	 * 
	 * @param iterable
	 * @return
	 */
	List<T> findAll(Iterable<Long> iterable);

	/**
	 * 批量保存
	 * 
	 * @param iterable
	 * @return
	 */
	List<T> save(Iterable<T> iterable);

}
