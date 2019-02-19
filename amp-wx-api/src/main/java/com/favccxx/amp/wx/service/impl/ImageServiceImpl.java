package com.favccxx.amp.wx.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpImage;
import com.favccxx.amp.db.repository.ImageRepository;
import com.favccxx.amp.wx.constants.SysConstants;
import com.favccxx.amp.wx.service.ImageService;

@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageRepository, AmpImage> implements ImageService {

	

	@Override
	public List<AmpImage> listThumbnailsByProductId(long productId) {
		return repository.findByProductIdAndType(productId, SysConstants.IMAGE_SLIDER);
	}

	@Transactional
	@Override
	public void updateProductImages(long productId, List<AmpImage> list) {
		repository.deleteByProductId(productId);
		for(AmpImage image : list) {
			repository.save(image);
		}
		
	}

	@Override
	public List<AmpImage> findByProductId(long productId) {
		return repository.findByProductIdAndType(productId, SysConstants.IMAGE_SLIDER);
	}

}
