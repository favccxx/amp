package com.favccxx.amp.wx.service;

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
	
	/**
	 * 更新产品的幻灯片图片
	 * @param productId 产品Id
	 * @param list 图片列表
	 */
	void updateProductImages(long productId, List<AmpImage> list);
	
	/**
	 * 根据产品Id查询图片列表
	 * @param productId
	 * @return
	 */
	List<AmpImage> findByProductId(long productId);
	
}
