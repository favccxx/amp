package com.favccxx.amp.wx.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.db.model.SysUser;
import com.favccxx.amp.wx.constants.SysConstants;
import com.favccxx.amp.wx.service.ShopService;
import com.favccxx.amp.wx.service.UserService;
import com.favccxx.amp.wx.util.SortUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/shop")
@Api(tags="2.0. 店铺管理接口" )
public class ShopController {

	@Autowired
	ShopService shopService;
	@Autowired
	UserService userService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpShop.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询产品信息")
	public RestResult list(			
			@RequestParam(value = "shopCode", required=false) String shopCode,
			@RequestParam(value = "shopName", required=false) String shopName,
			@RequestParam(value = "status", required=false) String status,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<AmpShop> pageData = null;
		AmpShop shop = new AmpShop();
		
		shop.setStatus("");
		if(StringUtils.isNotBlank(shopCode)) {
			shop.setShopCode(shopCode);
		}
		if(StringUtils.isNoneBlank(shopName)) {
			shop.setShopName(shopName);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = shopService.pageQuery(shop, pageable);
		
		return RestResult.ok(pageData);
	}
	
	
	@GetMapping("/list/user/{username}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpShop.class) })
	@ApiOperation(httpMethod = "GET", value = "根据店铺代码查询店铺信息")
	public RestResult listByUsername(@PathVariable(value = "username") String username) {
		SysUser user = userService.findByUsername(username);
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
