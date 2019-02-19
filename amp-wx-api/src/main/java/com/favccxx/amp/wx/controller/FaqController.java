package com.favccxx.amp.wx.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favccxx.amp.db.dto.RestResult;
import com.favccxx.amp.db.model.AmpFaq;
import com.favccxx.amp.wx.constants.SysConstants;
import com.favccxx.amp.wx.service.FaqService;
import com.favccxx.amp.wx.util.SortUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/faq")
@Api(tags="2.3.2 问答管理接口" )
public class FaqController {

	@Autowired
	FaqService faqService;
	
	@GetMapping("/list")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "操作成功", response = AmpFaq.class) })
    @ApiOperation(httpMethod = "GET", value = "分页查询评论信息")
	public RestResult list(			
			@RequestParam(value = "content", required=false) String content,
			@RequestParam(value = "productId", required=false) String productId,
			@RequestParam(value="sort", defaultValue="+id") String sort,
			@RequestParam(value = "page", defaultValue="1")  int page,
			@RequestParam(value = "limit", defaultValue=SysConstants.PAGE_SIZE)  int limit) {
		
		Page<AmpFaq> pageData = null;
		AmpFaq faq = new AmpFaq();

		
		if(StringUtils.isNotBlank(content)) {
			faq.setContent(content);
		}
		
		if(StringUtils.isNoneBlank(productId)) {
			faq.setProductId(Long.valueOf(productId));
		}

		Sort mySort = SortUtil.getSort(sort);
		Pageable pageable = PageRequest.of(page - 1, limit, mySort);
		pageData = faqService.pageQuery(faq, pageable);
		
		return RestResult.ok(pageData);	
	}
	
}
