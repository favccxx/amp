package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpShopUser;

@Repository
public interface ShopUserRepository extends JpaRepository<AmpShopUser, Long> {

	/**
	 * 根据用户查询所有的店铺列表
	 * @param userId
	 * @return
	 */
	List<AmpShopUser> findByUserId(long userId);
}
