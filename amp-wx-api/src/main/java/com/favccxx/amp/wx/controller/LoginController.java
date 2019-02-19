package com.favccxx.amp.wx.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.SysUser;
import com.favccxx.amp.util.jwt.JWTUtil;
import com.favccxx.amp.wx.dto.req.LoginUserReq;
import com.favccxx.amp.wx.dto.resp.LoginUser;
import com.favccxx.amp.wx.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/login")
@Api(value = "登录登出管理接口", tags = "1.0 登录接口")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;

	@PostMapping("/login")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "用户不存在"),
		@ApiResponse(code = 200, message = "操作成功", response = LoginUser.class) })
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "user", value = "登录用户实体", required = true, dataType = "LoginUserReq") })
	@ApiOperation(httpMethod = "POST", value = "用户登录")
	public RestResult login(@RequestBody LoginUserReq user) {
		logger.info("Ready--> login");
		LoginUser loginUser = new LoginUser();
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return RestResult.error("用户名或密码不允许为空!");
		}

		SysUser ampUser = userService.findByUsername(user.getUsername());
		if (ampUser == null || !user.getPassword().equals(ampUser.getPassword())) {
			return RestResult.error("用户名或密码错误!");
		}

		String token = JWTUtil.sign(user.getUsername(), user.getPassword());
		logger.info("------login token:-----" + token);
		BeanUtils.copyProperties(loginUser, ampUser);
		loginUser.setLoginDate(new Date());
		loginUser.setToken(token);
		return RestResult.ok(loginUser);
	}

//	@POST
//	@Path("/login")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "用户登录. ", response = SmartClub.class)
//	@ApiResponse(code = 200, message = "操作成功")	
//	public Response login(
//    		@FormParam("userName")  String userName,
//    		@FormParam("password")  String password) {
//		
//		if (StringUtils.isBlank(userName)) {
//			return Response.status(Response.Status.BAD_REQUEST).build();
//		}
//		
//		
//		AmpUser AmpUser = userService.findByUserName(userName);
//		if(AmpUser == null || !AmpUser.getPassword().equals(password)) {
//			return Response.status(Status.NO_CONTENT).build();
//		}
//		
//		
//		LoginSession loginUser = sessionUserService.findOrCreateByUserName(userName);
//		
//	
//
//				
//		return Response.ok(loginUser).build();
//	}

}
