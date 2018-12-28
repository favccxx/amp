package com.favccxx.amp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpProduct;

@Repository
public interface ProductRepository extends JpaRepository<AmpProduct, Long> {

}
