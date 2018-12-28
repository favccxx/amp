package com.favccxx.amp.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;

import io.swagger.annotations.Api;

@RestController
@Api(tags="1.7 问答管理接口" )
public class HelloController {

	@GetMapping("/")
	public String hello() {
		return ("yeah");
	}

	@RequestMapping("/hello")
	public RestResult hello1() {
		return RestResult.ok("yeah");
	}

}
