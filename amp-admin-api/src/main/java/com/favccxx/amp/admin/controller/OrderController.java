package com.favccxx.amp.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.admin.dto.resp.OrderResponse;
import com.favccxx.amp.admin.service.OrderService;
import com.favccxx.amp.admin.service.ProductService;
import com.favccxx.amp.db.constants.OrderStatus;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/order")
@Api(tags="2.4 订单管理接口" )
public class OrderController {

Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpOrder.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询订单信息")
	public RestResult list(			
			@RequestParam(value = "orderNo", required=false) String orderNo,
			@RequestParam(value = "status", required=false) String status,
			@RequestParam(value="sortField", defaultValue="id") String sortField,
			@RequestParam(value = "direction", defaultValue="DESC") String direction,
			@RequestParam(value = "pageIndex", defaultValue="1")  int pageIndex,
			@RequestParam(value = "pageSize", defaultValue=SysConstants.PAGE_SIZE)  int pageSize) {
		Page<AmpOrder> pageData = null;
		AmpOrder order = new AmpOrder();
		if(StringUtils.isNotBlank(status)) {
			order.setStatus(OrderStatus.CREATED);
		}
		
		if(StringUtils.isNotBlank(orderNo)) {
			order.setOrderNo(orderNo);
		}
		
		Direction directionField = Direction.DESC;
		if (direction.toUpperCase().equals("ASC")) {
			directionField = Direction.ASC;
		}

		Sort sort = new Sort(directionField, sortField);
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
		pageData = orderService.pageOrder(null, null, pageable);
		
		return RestResult.ok(pageData);	
	}

	
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpOrder.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品Id查询产品信息")
	public RestResult detail(@PathVariable (value = "id") long id) {
		OrderResponse order = orderService.findOrderDetail(id);
		return RestResult.ok(order);
	}
	
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = String.class) })
    @ApiOperation(httpMethod = "DELETE", value = "删除订单")
	public RestResult delete(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return RestResult.ok();		
	}
	
}
