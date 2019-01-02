package com.favccxx.amp.admin.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.service.ProductService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpProduct;
import com.favccxx.amp.db.repository.ProductRepository;

@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductRepository, AmpProduct> implements ProductService {

	@Override
	public AmpProduct findByProductNo(String productNo) {
		return repository.findByProductNo(productNo);
	}

	@Override
	public Page<AmpProduct> pageQuery(AmpProduct product, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("productNo", GenericPropertyMatchers.startsWith())
				.withMatcher("productName", GenericPropertyMatchers.startsWith())
				.withMatcher("status", GenericPropertyMatchers.exact()).withIgnorePaths("id")
				.withIgnorePaths("workTime").withIgnorePaths("isPackage").withIgnorePaths("originalPrice");
		if (product.getCategoryId() == 0) {
			matcher.getIgnoredPaths().add("categoryId");
		} else {
			matcher.withMatcher("categoryId", GenericPropertyMatchers.exact());
		}

		if (product.getShopId() == 0) {
			matcher.getIgnoredPaths().add("clubId");
		} else {
			matcher.withMatcher("clubId", GenericPropertyMatchers.exact());
		}

		if (product.getStar() == 0) {
			matcher.getIgnoredPaths().add("star");
		} else {
			matcher.withMatcher("star", GenericPropertyMatchers.exact());
		}

		Example<AmpProduct> example = Example.of(product, matcher);

		return repository.findAll(example, pageable);
	}

	

}
