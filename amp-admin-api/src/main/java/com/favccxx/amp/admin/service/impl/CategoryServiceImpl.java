package com.favccxx.amp.admin.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.constants.CategoryStatus;
import com.favccxx.amp.admin.service.CategoryService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpCategory;
import com.favccxx.amp.db.repository.CategoryRepository;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryRepository, AmpCategory> implements CategoryService {

	@Override
	public Page<AmpCategory> pageQuery(AmpCategory category, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("categoryCode", GenericPropertyMatchers.startsWith())
				.withMatcher("categoryName", GenericPropertyMatchers.startsWith())
				.withMatcher("status", GenericPropertyMatchers.contains()).withIgnorePaths("parentId")
				.withIgnorePaths("categoryOrder").withIgnorePaths("id");
		if (category.getStatus() == 0) {
			matcher.getIgnoredPaths().add("status");
		} else {
			matcher.withMatcher("status", GenericPropertyMatchers.contains());
		}

		Example<AmpCategory> example = Example.of(category, matcher);

		return repository.findAll(example, pageable);
	}

	@Override
	public List<AmpCategory> listNormal() {
		return repository.findByStatus(CategoryStatus.ENABLE.value());
	}

	@Override
	public List<AmpCategory> listParent() {
		return repository.findByParentIdAndStatus(0, CategoryStatus.ENABLE.value());
	}

}
