package com.favccxx.amp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.AmpCategory;

@Repository
public interface CategoryRepository extends JpaRepository<AmpCategory, Long> {

}
