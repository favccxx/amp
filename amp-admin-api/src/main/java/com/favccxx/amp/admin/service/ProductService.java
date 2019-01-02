package com.favccxx.amp.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpProduct;

public interface ProductService extends BaseService<AmpProduct> {

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
