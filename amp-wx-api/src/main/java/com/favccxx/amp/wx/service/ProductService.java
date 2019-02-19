package com.favccxx.amp.wx.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpProduct;

public interface ProductService extends BaseService<AmpProduct> {
	
	/**
	 * 根据分类查询最近更新的产品列表
	 * categoryId 为0时不过滤分类
	 * @param categoryId 类别Id
	 * @param count 条目
	 * @return
	 */
	List<AmpProduct> queryByNew(long categoryId, int count);
	
	/**
	 * 根据热度查询产品列表
	 * categoryId 为0时，不过滤分类
	 * @param categoryId
	 * @param count
	 * @return
	 */
	List<AmpProduct> queryByHot(long categoryId, int count);
	
	

	/**
	 * 根据产品编码查询产品详情
	 * @param productNo
	 * @return
	 */
	AmpProduct findByProductNo(String productNo);
	
	
	/**
	 * 分页查询产品信息
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<AmpProduct> pageQuery(AmpProduct product, Pageable pageable);
	
	
	
}
