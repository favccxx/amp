package com.favccxx.amp.wx.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpReview;
import com.favccxx.amp.db.repository.ReviewRepository;
import com.favccxx.amp.wx.service.ReviewService;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<ReviewRepository, AmpReview> implements ReviewService {

	@Override
	public Page<AmpReview> pageQuery(AmpReview review, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("content", GenericPropertyMatchers.contains())
				.withIgnorePaths("id").withIgnorePaths("score").withIgnorePaths("replyId").withIgnorePaths("userId");

		if (review.getProductId() == 0) {
			matcher.getIgnoredPaths().add("productId");
		} else {
			matcher.withMatcher("productId", GenericPropertyMatchers.exact());
		}

		if (StringUtils.isBlank(review.getReviewUsername())) {
			matcher.getIgnoredPaths().add("reviewUsername");
		} else {
			matcher.withMatcher("reviewUsername", GenericPropertyMatchers.exact());
		}

		Example<AmpReview> example = Example.of(review, matcher);

		return repository.findAll(example, pageable);
	}

}
