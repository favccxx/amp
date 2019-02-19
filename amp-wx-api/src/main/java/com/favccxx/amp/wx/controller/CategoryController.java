package com.favccxx.amp.wx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpCategory;
import com.favccxx.amp.wx.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/category")
@Api(tags = "2.1 产品类别管理接口")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/index")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "根据所选类别获取类别列表信息")
	public RestResult detail(@RequestParam(value = "id", defaultValue = "0", required = false) long id) {
		// 查找父类别
		List<AmpCategory> parentList = categoryService.listParent();

		AmpCategory category = null;
		if (id == 0) {
			category = parentList.get(0);
		} else {
			category = categoryService.findOne(id);
		}

		// 查询子类别
		List<AmpCategory> children = null;
		if (category != null) {
			children = categoryService.listByParent(category.getId());
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("categoryList", parentList);
		data.put("currentCategory", category);
		data.put("currentSubCategory", children);

		return RestResult.ok(data);
	}

	@GetMapping("/current")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "根据当前类别Id获取子类别列表信息")
	public RestResult current(@RequestParam(value = "id", defaultValue = "0", required = false) long id) {
		AmpCategory currentCategory = categoryService.findOne(id);
		List<AmpCategory> currentSubCategory = categoryService.listByParent(id);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("currentCategory", currentCategory);
		data.put("currentSubCategory", currentSubCategory);
		return RestResult.ok(data);
	}

	

}
