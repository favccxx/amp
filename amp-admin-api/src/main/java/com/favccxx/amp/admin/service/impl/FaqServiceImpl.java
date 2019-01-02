package com.favccxx.amp.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.service.FaqService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpFaq;
import com.favccxx.amp.db.repository.FaqRepository;

@Service
public class FaqServiceImpl extends BaseServiceImpl<FaqRepository, AmpFaq> implements FaqService {

	@Override
	public Page<AmpFaq> pageQuery(AmpFaq faq, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("content", GenericPropertyMatchers.contains())
				.withIgnorePaths("id").withIgnorePaths("score").withIgnorePaths("replyId").withIgnorePaths("userId");

		if (faq.getProductId() == 0) {
			matcher.getIgnoredPaths().add("productId");
		} else {
			matcher.withMatcher("productId", GenericPropertyMatchers.exact());
		}

		if (StringUtils.isBlank(faq.getUsername())) {
			matcher.getIgnoredPaths().add("username");
		} else {
			matcher.withMatcher("username", GenericPropertyMatchers.exact());
		}

		Example<AmpFaq> example = Example.of(faq, matcher);

		return repository.findAll(example, pageable);
	}

}
