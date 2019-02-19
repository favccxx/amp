package com.favccxx.amp.wx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpAd;
import com.favccxx.amp.db.model.AmpCategory;
import com.favccxx.amp.db.model.AmpProduct;
import com.favccxx.amp.wx.service.AdService;
import com.favccxx.amp.wx.service.CategoryService;
import com.favccxx.amp.wx.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/home")
@Api(tags = "1.0 首页展示接口")
public class HomeController {
	
	@Autowired
	AdService adService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping("/index")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = Map.class) })
    @ApiOperation(httpMethod = "GET", value = "查询小程序首页需要展示的内容")
	public Object index() {
		// 优先从缓存中读取
//		if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
//			return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
//		}

		Map<String, Object> data = new HashMap<>();

		List<AmpAd> banner = adService.queryIndex();
		data.put("banner", banner);

		List<AmpCategory> channel = categoryService.queryChannel();
		data.put("channel", channel);

//		List<LitemallCoupon> couponList = couponService.queryList(0, 3);
//		data.put("couponList", couponList);
//
		List<AmpProduct> newGoods = productService.queryByNew(0, 10);
		data.put("newGoodsList", newGoods);

		List<AmpProduct> hotGoods = productService.queryByHot(0, 10);
		data.put("hotGoodsList", hotGoods);
//
//		List<LitemallBrand> brandList = brandService.queryVO(0, SystemConfig.getBrandLimit());
//		data.put("brandList", brandList);
//
//		List<LitemallTopic> topicList = topicService.queryList(0, SystemConfig.getTopicLimit());
//		data.put("topicList", topicList);

		// 团购专区
//		List<Map<String, Object>> grouponList = grouponRulesService.queryList(0, 5);
//		data.put("grouponList", grouponList);
//
//		List<Map> categoryList = new ArrayList<>();
//		List<LitemallCategory> catL1List = categoryService.queryL1WithoutRecommend(0,
//				SystemConfig.getCatlogListLimit());
//		for (LitemallCategory catL1 : catL1List) {
//			List<LitemallCategory> catL2List = categoryService.queryByPid(catL1.getId());
//			List<Integer> l2List = new ArrayList<>();
//			for (LitemallCategory catL2 : catL2List) {
//				l2List.add(catL2.getId());
//			}
//
//			List<LitemallGoods> categoryGoods = null;
//			if (l2List.size() == 0) {
//				categoryGoods = new ArrayList<>();
//			} else {
//				categoryGoods = goodsService.queryByCategory(l2List, 0, SystemConfig.getCatlogMoreLimit());
//			}
//
//			Map<String, Object> catGoods = new HashMap<String, Object>();
//			catGoods.put("id", catL1.getId());
//			catGoods.put("name", catL1.getName());
//			catGoods.put("goodsList", categoryGoods);
//			categoryList.add(catGoods);
//		}
//		data.put("floorGoodsList", categoryList);

		// 缓存数据
//		HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
		return RestResult.ok(data);
	}

}
