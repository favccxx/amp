package com.favccxx.amp.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.constants.SysConstants;
import com.favccxx.amp.admin.service.ImageService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpImage;
import com.favccxx.amp.db.repository.ImageRepository;

@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageRepository, AmpImage> implements ImageService {

	

	@Override
	public List<AmpImage> listThumbnailsByProductId(long productId) {
		return repository.findByProductIdAndType(productId, SysConstants.IMAGE_SLIDER);
	}

}
