package com.favccxx.amp.wx.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpImage;
import com.favccxx.amp.util.thumb.ThumbnailUtil;
import com.favccxx.amp.wx.constants.SysConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/storage")
@Api(tags = "3.1 存储管理接口")
public class StorageController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostMapping("/upload")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = AmpImage.class) })
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public RestResult upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
				
		
		logger.info("###############fileName：" + file.getOriginalFilename() + "&&&&&:" + file.getContentType());

		String fileName = file.getOriginalFilename();
		String fileNamePostfix = fileName.substring(fileName.lastIndexOf("."));

		logger.info("@@@@@@@@@@@@@@@fileType:" + fileNamePostfix);

		String uuid = UUID.randomUUID().toString();
		String imageName = uuid + fileNamePostfix;
		String thumbnailName = uuid + "_thumb" + fileNamePostfix;

		// 保存文件
		File imageFile = new File(SysConstants.IMAGE_PATH + imageName);
		if (!imageFile.exists()) {
			imageFile.createNewFile();
		}
		file.transferTo(imageFile);

		// 保存缩略图
		File destFile = new File(SysConstants.IMAGE_PATH + thumbnailName);
		ThumbnailUtil.saveThumbnail(imageFile, destFile);
		

		return RestResult.ok();
	}
	
}
