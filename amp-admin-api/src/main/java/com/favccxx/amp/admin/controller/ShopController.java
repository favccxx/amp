package com.favccxx.amp.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.admin.service.ShopService;
import com.favccxx.amp.admin.service.UserService;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.db.model.AmpUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/shop")
@Api(tags="1. 店铺管理接口" )
public class ShopController {

	@Autowired
	ShopService shopService;
	@Autowired
	UserService userService;
	
	
	@GetMapping("/list/user/{username}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpShop.class) })
	@ApiOperation(httpMethod = "GET", value = "根据店铺代码查询店铺信息")
	public RestResult listByUsername(@PathVariable(value = "username") String username) {
		AmpUser user = userService.findByUsername(username);
		if(user == null) {
			return RestResult.error("用户不存在！");
		}
		List<AmpShop> shopList = shopService.findByUserId(user.getId());
		return RestResult.ok(shopList);
	}
	
	@GetMapping("/detail/{shopCode}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpShop.class) })
	@ApiOperation(httpMethod = "GET", value = "根据店铺代码查询店铺信息")
	public RestResult detail(@PathVariable(value = "shopCode") String shopCode) {
		AmpShop shop = shopService.findByShopCode(shopCode);
		return RestResult.ok(shop);
	}
	
	
}
