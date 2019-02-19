package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpProduct;

@Repository
public interface ProductRepository extends JpaRepository<AmpProduct, Long> {
	
	@Query(nativeQuery = true ,value = "SELECT * FROM AMP_PRODUCT t  where t.category_id=?1 ORDER BY t.online_date DESC limit ?2;")
	List<AmpProduct> findByNew(long categoryId, int count);
	
	@Query(nativeQuery = true ,value = "SELECT * FROM AMP_PRODUCT t  ORDER BY t.online_date DESC limit ?;")
	List<AmpProduct> findByNew(int count);
	

	/**
	 * 根据产品编码查询产品详情
	 * @param productNo
	 * @return
	 */
	AmpProduct findByProductNo(String productNo);
	
}
