package com.favccxx.amp.admin.controller;

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

import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.admin.service.UserService;
import com.favccxx.amp.admin.util.SortUtil;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@Api(tags="1. 用户管理接口" )
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpUser.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询用户信息")
	public RestResult list(			
			@RequestParam(value = "username", required=false) String username,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<AmpUser> pageData = null;
		AmpUser user = new AmpUser();
		
		if(StringUtils.isNotBlank(username)) {
			user.setUsername(username);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = userService.pageQuery(user, pageable);
		
		return RestResult.ok(pageData);	
	}
	
	
	
		
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = AmpUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户Id查询用户信息")
	public RestResult detail(@PathVariable (value = "id") long id) {
		AmpUser user = userService.findOne(id);
		return RestResult.ok(user);
	}
	
	
	@GetMapping("/findByUsername/{username}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = AmpUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户名查询用户信息")
	public RestResult findByUsername(@PathVariable (value = "username") String username) {
		AmpUser user = userService.findByUsername(username);
		return RestResult.ok(user);
	}
}
