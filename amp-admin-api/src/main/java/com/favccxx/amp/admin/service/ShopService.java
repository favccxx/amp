package com.favccxx.amp.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpShop;

public interface ShopService extends BaseService<AmpShop> {

	/**
	 * 根据用户查询可以访问的店铺列表
	 * @param userId
	 * @return
	 */
	List<AmpShop> findByUserId(long userId);
	
	/**
	 * 根据店铺代码查询店铺详情
	 * @param shopCode
	 * @return
	 */
	AmpShop findByShopCode(String shopCode);
	
	
	/**
	 * 分页查询店铺信息
	 * @param shop
	 * @param pageable
	 * @return
	 */
	Page<AmpShop> pageQuery(AmpShop shop, Pageable pageable);
	
}
