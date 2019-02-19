package com.favccxx.amp.wx.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpCategory;
import com.favccxx.amp.wx.dto.resp.CategoryDto;

public interface CategoryService extends BaseService<AmpCategory> {
	
	/**
	 * 查询微信小程序上展示的频道
	 * @return
	 */
	List<AmpCategory> queryChannel();

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
	 * 根据父类别Id查询所有的子类别
	 * @param parentId
	 * @return
	 */
	List<AmpCategory> listByParent(long parentId);
	
	/**
	 * 按层级查询所有的类别信息
	 * @return
	 */
	List<CategoryDto> listByLevel();
}
