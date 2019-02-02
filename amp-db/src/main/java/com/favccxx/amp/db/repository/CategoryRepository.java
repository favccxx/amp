package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpCategory;

@Repository
public interface CategoryRepository extends JpaRepository<AmpCategory, Long> {
	
	

	/**
	 * 根据类别状态查询产品类别信息列表
	 * 
	 * @param status 类别状态
	 * @return
	 */
	List<AmpCategory> findByStatus(int status);

	/**
	 * 根据父类别Id和状态查询类别列表
	 * 
	 * @param parentId
	 * @param status
	 * @return
	 */
	List<AmpCategory> findByParentIdAndStatus(long parentId, int status);
	
	
}
