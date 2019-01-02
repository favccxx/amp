package com.favccxx.amp.admin.service;

import java.util.List;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpImage;

public interface ImageService extends BaseService<AmpImage> {

	/**
	 * 根据产品Id查询缩略图
	 * 
	 * @param productId
	 * @return
	 */
	List<AmpImage> listThumbnailsByProductId(long productId);
	
}
