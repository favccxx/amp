package com.favccxx.amp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpShop;

@Repository
public interface ShopRepository extends JpaRepository<AmpShop, Long> {

	/**
	 * 根据店铺代码查询商城详情
	 * @param mallCode
	 * @return
	 */
	AmpShop findByShopCode(String shopCode);
	
}
