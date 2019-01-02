package com.favccxx.amp.admin.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.favccxx.amp.admin.constants.CategoryStatus;
import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.admin.dto.req.CategoryPutReq;
import com.favccxx.amp.admin.dto.req.CategoryUpdateReq;
import com.favccxx.amp.admin.service.CategoryService;
import com.favccxx.amp.admin.util.SortUtil;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpCategory;

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

	@GetMapping("/detail/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "根据产品类别Id查询类别信息")
	public RestResult detail(@PathVariable(value = "id") long id) {
		AmpCategory category = categoryService.findOne(id);
		return RestResult.ok(category);
	}

	
	@GetMapping("/list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "分页查询产品类别信息")
	public RestResult list(
			@RequestParam(value = "categoryCode", required = false) String categoryCode,
			@RequestParam(value = "categoryName", required = false) String categoryName,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "sort", defaultValue = "+id") String sort,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = SysConstants.PAGE_SIZE) int limit) {
		Page<AmpCategory> pageData = null;
		AmpCategory category = new AmpCategory();
		if (StringUtils.isNoneBlank(categoryCode)) {
			category.setCategoryCode(categoryCode);
		}
		if (StringUtils.isNotBlank(categoryName)) {
			category.setCategoryName(categoryName);
		}
		if (StringUtils.isNoneBlank(status)) {
			category.setStatus(Integer.parseInt(status));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = categoryService.pageQuery(category, pageable);

		return RestResult.ok(pageData);
	}
	
	@GetMapping("/listParent")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "查询所有正常的父类别信息")
	public RestResult listParent() {
		List<AmpCategory> list = categoryService.listParent();
		return RestResult.ok(list);
	}

	@GetMapping("/listNomal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "GET", value = "查询所有正常的类别信息")
	public RestResult listNomal() {
		List<AmpCategory> list = categoryService.listNormal();
		return RestResult.ok(list);
	}

	@PutMapping("/create")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "PUT", value = "创建产品类别信息")
	public RestResult create(@RequestBody CategoryPutReq categoryReq) {
		AmpCategory category = new AmpCategory();

		if (StringUtils.isBlank(categoryReq.getCategoryCode()) || StringUtils.isBlank(categoryReq.getCategoryName())) {
			return RestResult.error("categoryCode/categoryName不允许为空");
		}

		try {
			BeanUtils.copyProperties(category, categoryReq);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());

		boolean checkStatus = false;
		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == category.getStatus()) {
				checkStatus = true;
				continue;
			}
		}
		if (!checkStatus) {
			category.setStatus(CategoryStatus.ENABLE.value());
		}

		categoryService.save(category);
		return RestResult.ok(category);
	}

	@PostMapping("/update")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "POST", value = "更新产品类别信息")
	public RestResult update(@RequestBody CategoryUpdateReq categoryReq) {
		AmpCategory existCategory = categoryService.findOne(categoryReq.getId());
		if (existCategory == null) {
			return RestResult.error("参数错误");
		}

		if (StringUtils.isNoneBlank(categoryReq.getCategoryName())) {
			existCategory.setCategoryName(categoryReq.getCategoryName());
		}

		if (StringUtils.isNoneBlank(categoryReq.getDetail())) {
			existCategory.setDetail(categoryReq.getDetail());
		}
		
		existCategory.setLevel(categoryReq.getLevel());

		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == categoryReq.getStatus()) {
				existCategory.setStatus(categoryReq.getStatus());
			}
		}
		existCategory.setUpdateTime(new Date());

		categoryService.save(existCategory);
		return RestResult.ok(existCategory);
	}

	@PostMapping("/modifyStatus")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpCategory.class) })
	@ApiOperation(httpMethod = "POST", value = "更新产品类别状态")
	public RestResult modifyStatus(@RequestBody CategoryUpdateReq categoryReq) {
		AmpCategory existCategory = categoryService.findOne(categoryReq.getId());
		if (existCategory == null) {
			return RestResult.error("参数错误");
		}

		for (CategoryStatus catStatus : CategoryStatus.values()) {
			if (catStatus.value() == categoryReq.getStatus()) {
				existCategory.setStatus(categoryReq.getStatus());
			}
		}
		existCategory.setUpdateTime(new Date());

		categoryService.save(existCategory);
		return RestResult.ok(existCategory);
	}

	@DeleteMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = String.class) })
	@ApiOperation(httpMethod = "DELETE", value = "根据产品类别Id删除产品类别信息")
	public RestResult delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		return RestResult.ok();
	}
	
}
