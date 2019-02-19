package com.favccxx.amp.wx.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpCategory;
import com.favccxx.amp.db.model.AmpImage;
import com.favccxx.amp.db.model.AmpProduct;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.util.date.DateUtil;
import com.favccxx.amp.wx.constants.ProductStatus;
import com.favccxx.amp.wx.constants.SysConstants;
import com.favccxx.amp.wx.dto.req.ProductReq;
import com.favccxx.amp.wx.service.CategoryService;
import com.favccxx.amp.wx.service.ImageService;
import com.favccxx.amp.wx.service.ProductService;
import com.favccxx.amp.wx.service.ShopService;
import com.favccxx.amp.wx.util.SortUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@Api(tags="2.3 产品管理接口" )
public class ProductController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ShopService shopService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	ImageService imageService;
	
	@GetMapping("/count") 
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = Integer.class) })
    @ApiOperation(httpMethod = "GET", value = "统计产品数量")
	public RestResult count() {				
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("goodsCount", 137);
		return RestResult.ok(data);	
	}
	
	@GetMapping("category")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = Integer.class) })
    @ApiOperation(httpMethod = "GET", value = "根據產品類別查詢產品列表")
	public Object category(@RequestParam(value = "id", defaultValue = "0", required = false) long id) {
		AmpCategory current = categoryService.findOne(id);
		AmpCategory parent = null;
		List<AmpCategory> children = null;
		// 父类别
		if (current.getParentId() == 0) {
			parent = current;
			children = categoryService.listByParent(id);
			current = children.size() > 0 ? children.get(0) : current;
		} else {
			parent = categoryService.findOne(current.getParentId());
			children = categoryService.listByParent(current.getParentId());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("currentCategory", current);
		data.put("parentCategory", parent);
		data.put("brotherCategory", children);
		return RestResult.ok(data);
	}
	
	@GetMapping("/list") 
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询产品信息")
	public RestResult list(		
			@RequestParam(value = "categoryId", required=false) String categoryId,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "size", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<AmpProduct> pageData = null;
		long shopId = 0;
		
		
		AmpProduct product = new AmpProduct();
		product.setShopId(shopId);
		product.setStatus(ProductStatus.PUBLISHED.value());
		
		if(StringUtils.isNoneBlank(categoryId)) {
			product.setCategoryId(Long.valueOf(categoryId));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = productService.pageQuery(product, pageable);
		
		return RestResult.ok(pageData);	
	}
	
	
	@GetMapping("/detail")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品Id查询产品信息")
	public RestResult detail(@RequestParam(value = "id", defaultValue = "0", required = false) long id) {
		AmpProduct product = productService.findOne(id);
		List<AmpImage> images = imageService.findByProductId(id);
		product.setSliders(images);
		
		
		Map<String, Object> data = new HashMap<>();

		
			data.put("info", product);
//			data.put("userHasCollect", userHasCollect);
//			data.put("issue", issueCallableTask.get());
//			data.put("comment", commentsCallableTsk.get());
//			data.put("specificationList", objectCallableTask.get());
//			data.put("productList", productListCallableTask.get());
//			data.put("attribute", goodsAttributeListTask.get());
//			data.put("brand", brandCallableTask.get());
//			data.put("groupon", grouponRulesCallableTask.get());
		
		
		return RestResult.ok(data, "操作成功");
	}
	
	
	@GetMapping("/findByProductNo/{productNo}")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpProduct.class) })
    @ApiOperation(httpMethod = "GET", value = "根据产品编码查询产品信息")
	public RestResult findByProductNo(@PathVariable (value = "productNo") String productNo) {
		AmpProduct product = productService.findByProductNo(productNo);
		return RestResult.ok(product);
	}
	
	
	
}
