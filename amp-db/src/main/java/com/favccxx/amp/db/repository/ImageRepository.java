package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpImage;

@Repository
public interface ImageRepository extends JpaRepository<AmpImage, Long> {

	/**
	 * 根据产品Id和图片类型查询图片列表
	 * @param productId
	 * @return
	 */
	List<AmpImage> findByProductIdAndType(long productId, String type);
	
}
