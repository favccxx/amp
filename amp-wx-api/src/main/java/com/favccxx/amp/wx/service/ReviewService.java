package com.favccxx.amp.wx.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpReview;

public interface ReviewService extends BaseService<AmpReview> {

	/**
	 * 分页查询评论信息
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<AmpReview> pageQuery(AmpReview review, Pageable pageable);
	
}
