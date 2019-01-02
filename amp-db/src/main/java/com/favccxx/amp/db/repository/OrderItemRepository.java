package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpOrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<AmpOrderItem, Long> {

	/**
	 * 根据订单Id查询订单产品列表
	 * @param orderId
	 * @return
	 */
	List<AmpOrderItem> findByOrderId(long orderId);
	
}
