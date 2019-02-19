package com.favccxx.amp.wx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.constants.OrderStatus;
import com.favccxx.amp.db.model.AmpOrder;
import com.favccxx.amp.db.model.AmpOrderItem;
import com.favccxx.amp.db.repository.OrderItemRepository;
import com.favccxx.amp.db.repository.OrderRepository;
import com.favccxx.amp.wx.dto.resp.OrderResponse;
import com.favccxx.amp.wx.service.OrderService;

@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderRepository, AmpOrder> implements OrderService {

	@Autowired
	OrderItemRepository orderItemRepository;

	@Override
	public AmpOrder findByOrderNo(String orderNo) {
		return repository.findByOrderNo(orderNo);
	}

	@Override
	public OrderResponse findOrderDetail(long id) {
		OrderResponse response = new OrderResponse();
		AmpOrder order = repository.findById(id).get();
		if (order != null) {
			try {
				BeanUtils.copyProperties(order, response);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<AmpOrderItem> list = orderItemRepository.findByOrderId(order.getId());
			response.setProductList(list);
		}
		return response;
	}

	

	@Override
	public Page<AmpOrder> pageOrder(String username, String orderStatus, Pageable pageable) {
		Page<AmpOrder> page = null;

		if ("ALL".equals(orderStatus)) {

		} else if ("NEEDPAY".equals(orderStatus)) {
			page = repository.needPayQuery(username, pageable);
		} else if ("FINISHED".equals(orderStatus)) {
			page = repository.finishedQuery(username, pageable);
		} else if ("CANCELD".equals(orderStatus)) {
			page = repository.canceledQuery(username, pageable);
		}
		return page;
	}


	@Override
	public void deleteOrder(long id) {
		AmpOrder order = findOne(id);
		if(order != null) {
			order.setStatus(OrderStatus.DELETED);
			repository.save(order);
		}
		
	}

}
