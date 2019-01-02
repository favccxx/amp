package com.favccxx.amp.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpOrder;

@Repository
public interface OrderRepository extends JpaRepository<AmpOrder, Long> {

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param orderNo
	 * @return
	 */
	AmpOrder findByOrderNo(String orderNo);

	/**
	 * 待支付订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from AmpOrder o where o.userName=?1 and o.status='CREATED' and o.payStatus=0 order by o.createTime desc", countQuery = " select count(o) FROM AmpOrder o where o.userName=?1 and o.status='CREATED' and o.payStatus=0")
	Page<AmpOrder> needPayQuery(String username, Pageable pageable);

	/**
	 * 已取消订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from AmpOrder o where o.userName=?1 and o.status='CANCELD' and o.payStatus=0 order by o.createTime desc", countQuery = " select count(o) FROM AmpOrder o where o.userName=?1 and o.status='CANCELD' and o.payStatus=0")
	Page<AmpOrder> canceledQuery(String username, Pageable pageable);

	/**
	 * 已完成订单
	 * 
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query(value = "from AmpOrder o where o.userName=?1 and o.status='FINISHED' and o.payStatus=1 order by o.createTime desc", countQuery = " select count(o) FROM AmpOrder o where o.userName=?1 and o.status='FINISHED' and o.payStatus=2")
	Page<AmpOrder> finishedQuery(String username, Pageable pageable);

}
