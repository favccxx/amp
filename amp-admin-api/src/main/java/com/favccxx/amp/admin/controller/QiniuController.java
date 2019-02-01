package com.favccxx.amp.admin.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpImage;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/qiniu")
@Api(tags="4.3 七牛存储管理接口" )
public class QiniuController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/storage/token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = String.class) })
	@ApiOperation(httpMethod = "GET", value = "获取七牛存储的身份认证信息")
	public RestResult detail() {
		String accessKey = "6R7po_eOIYXMQEAvmPWnNqGPmtLtzu2eNaCn2WSs";
		String secretKey = "5fob66bbqn0-zWugnBKJpMXkduZtacnKnKDozQtZ";
		String bucket = "amp-pic";
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		return RestResult.ok(upToken);
	}
	
	
	@PostMapping("/upload")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpImage.class) })
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public RestResult upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
		Configuration cfg = new Configuration(Zone.zone0());
		UploadManager uploadManger = new UploadManager(cfg);
		
		String accessKey = "6R7po_eOIYXMQEAvmPWnNqGPmtLtzu2eNaCn2WSs";
		String secretKey = "5fob66bbqn0-zWugnBKJpMXkduZtacnKnKDozQtZ";
		String bucket = "amp-pic";
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		
		String key = UUID.randomUUID().toString();
		
		uploadManger.put(file.getInputStream(), key, upToken, null, null);
		
		return RestResult.ok(SysConstants.QINIU_URL + key);
	}
}
