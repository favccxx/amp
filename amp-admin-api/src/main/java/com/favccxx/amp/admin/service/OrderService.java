package com.favccxx.amp.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.admin.dto.resp.OrderResponse;
import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpOrder;

public interface OrderService extends BaseService<AmpOrder> {

	/**
	 * 根据订单编号查询订单信息
	 * @param orderNo
	 * @return
	 */
	AmpOrder findByOrderNo(String orderNo);
	
	/**
	 * 根据订单Id查询订单详情
	 * @param id
	 * @return
	 */
	OrderResponse findOrderDetail(long id);
	
	
	/**
	 * 分页查询用户的订单
	 * @param username 用户名
	 * @param orderStatus 
	 * 	ALL 全部订单
	 *  NEEDPAY 待支付
	 *  FINISHED 已完成
	 *  CANCELD 已取消
	 * @param pageable
	 * @return
	 */
	Page<AmpOrder> pageOrder(String username, String orderStatus, Pageable pageable);
	
	
	/**
	 * 删除订单
	 * @param id
	 */
	void deleteOrder(long id);
	
}
