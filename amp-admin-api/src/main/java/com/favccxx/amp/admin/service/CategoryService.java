package com.favccxx.amp.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.admin.dto.resp.CategoryDto;
import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpCategory;

public interface CategoryService extends BaseService<AmpCategory> {

	/**
	 * 分页查询产品类别
	 * @param category
	 * @param pageable
	 * @return
	 */
	Page<AmpCategory> pageQuery(AmpCategory category, Pageable pageable);
	
	/**
	 * 查找某类别的父类别
	 * @param categoryId
	 * @return
	 */
	AmpCategory findParent(long categoryId);
	
	/**
	 * 查询所有正常状态的类别信息
	 * @return
	 */
	List<AmpCategory> listNormal();
	
	/**
	 * 查询所有的父类别列表
	 * @return
	 */
	List<AmpCategory> listParent();
	
	/**
	 * 按层级查询所有的类别信息
	 * @return
	 */
	List<CategoryDto> listByLevel();
}
