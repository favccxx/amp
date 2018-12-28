package com.favccxx.amp.admin.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
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
import com.favccxx.amp.admin.dto.resp.UserResponse;
import com.favccxx.amp.admin.service.ShopService;
import com.favccxx.amp.admin.service.UserService;
import com.favccxx.amp.admin.util.SortUtil;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.db.model.SysUser;
import com.favccxx.amp.util.jwt.JWTUtil;

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
	@Autowired
	ShopService shopService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = SysUser.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询用户信息")
	public RestResult list(			
			@RequestParam(value = "username", required=false) String username,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		Page<SysUser> pageData = null;
		SysUser user = new SysUser();
		
		if(StringUtils.isNotBlank(username)) {
			user.setUsername(username);
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = userService.pageQuery(user, pageable);
		
		return RestResult.ok(pageData);	
	}
	
	
	
	@GetMapping("/info")
	@ApiResponses(value = {
            @ApiResponse(code = 500, message = "系统错误"),
            @ApiResponse(code = 200, message = "操作成功", response = UserResponse.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户登录令牌查询用户信息")//swagger 当前接口注解
	public RestResult info(@RequestParam(value = "token") String token)  {		
		String username = JWTUtil.getUsername(token);
		if(StringUtils.isNoneBlank(username)) {
			SysUser user = userService.findByUsername(username);
			if(user != null) {
				UserResponse userResponse = new UserResponse();
				try {
					PropertyUtils.copyProperties(userResponse, user);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				
				List<String> roles =  userService.findRoleCodesByUserId(user.getId());
				userResponse.setRoles(roles.toString());
				
				List<AmpShop> shopList = shopService.findByUserId(user.getId());
				if(shopList != null && shopList.size()>0) {
					userResponse.setShopCode(shopList.get(0).getShopCode());
					userResponse.setShopName(shopList.get(0).getShopName());
				}
				
				return RestResult.ok(userResponse);
			}
		}

		return RestResult.error("认证信息已过期");
	}
	
		
	@GetMapping("/detail/{id}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = SysUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户Id查询用户信息")
	public RestResult detail(@PathVariable (value = "id") long id) {
		SysUser user = userService.findOne(id);
		return RestResult.ok(user);
	}
	
	
	@GetMapping("/findByUsername/{username}")
	@ApiResponses(value = {
            @ApiResponse(code = 100, message = "用户不存在"),
            @ApiResponse(code = 200, message = "操作成功", response = SysUser.class) })
    @ApiOperation(httpMethod = "GET", value = "根据用户名查询用户信息")
	public RestResult findByUsername(@PathVariable (value = "username") String username) {
		SysUser user = userService.findByUsername(username);
		return RestResult.ok(user);
	}
}
