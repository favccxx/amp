package com.favccxx.amp.admin.service;

import java.util.List;

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
	
}
