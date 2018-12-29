package com.favccxx.amp.db.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.amp.db.base.service.BaseService;

public class BaseServiceImpl<E extends JpaRepository<T, Long>, T> implements BaseService<T> {

	@Autowired
	protected E repository;

	@Override
	public T findOne(long id) {
		return (T) repository.findById(id).get();
	}

	@Override
	public T save(T entity) {
		return (T) repository.save(entity);
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(Iterable<T> iterable) {
		repository.deleteInBatch(iterable);
	}

	@Override
	public List<T> findAll(Iterable<Long> iterable) {
		return repository.findAllById(iterable);
	}

	@Override
	public List<T> save(Iterable<T> iterable) {
		return repository.saveAll(iterable);
	}

}
