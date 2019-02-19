package com.favccxx.amp.wx.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpProduct;
import com.favccxx.amp.db.repository.ProductRepository;
import com.favccxx.amp.wx.constants.ProductStatus;
import com.favccxx.amp.wx.service.ProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductRepository, AmpProduct> implements ProductService {
	
	@Override
	public List<AmpProduct> queryByNew(long categoryId, int count) {
		if(categoryId == 0) {
			return repository.findByNew(count);
		}
		return repository.findByNew(categoryId, count);
	}

	@Override
	public List<AmpProduct> queryByHot(long categoryId, int count) {
	
		return queryByNew(categoryId, count);
	}

	@Override
	public AmpProduct findByProductNo(String productNo) {
		return repository.findByProductNo(productNo);
	}

	@Override
	public Page<AmpProduct> pageQuery(AmpProduct product, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("productNo", GenericPropertyMatchers.startsWith())
				.withMatcher("productName", GenericPropertyMatchers.startsWith())
				.withIgnorePaths("id")
				.withIgnorePaths("workTime").withIgnorePaths("isPackage").withIgnorePaths("originalPrice");
		if (product.getCategoryId() == 0) {
			matcher.getIgnoredPaths().add("categoryId");
		} else {
			matcher.withMatcher("categoryId", GenericPropertyMatchers.exact());
		}
		
		if (product.getStatus() == 0) {
			product.setStatus(ProductStatus.PUBLISHED.value());
			matcher.withMatcher("status", GenericPropertyMatchers.startsWith());
		} else {
			matcher.withMatcher("status", GenericPropertyMatchers.exact());
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
