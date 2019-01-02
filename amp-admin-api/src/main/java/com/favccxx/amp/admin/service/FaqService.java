package com.favccxx.amp.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpFaq;

public interface FaqService extends BaseService<AmpFaq> {

	/**
	 * 分页查询问答信息
	 * @param faq
	 * @param pageable
	 * @return
	 */
	Page<AmpFaq> pageQuery(AmpFaq faq, Pageable pageable);
	
}
