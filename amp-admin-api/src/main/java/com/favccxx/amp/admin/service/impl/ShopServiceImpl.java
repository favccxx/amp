package com.favccxx.amp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.service.ShopService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpShop;
import com.favccxx.amp.db.model.AmpShopUser;
import com.favccxx.amp.db.repository.ShopRepository;
import com.favccxx.amp.db.repository.ShopUserRepository;

@Service
public class ShopServiceImpl extends BaseServiceImpl<ShopRepository, AmpShop> implements ShopService {

	@Autowired
	ShopUserRepository shopUserRepository;
	

	@Override
	public List<AmpShop> findByUserId(long userId) {
		List<AmpShop> shopList = new ArrayList<AmpShop>();
		List<AmpShopUser> list = shopUserRepository.findByUserId(userId);
		for(AmpShopUser shopUser : list) {
			AmpShop shop = repository.findById(shopUser.getShopId()).get();
			if(shop != null) {
				shopList.add(shop);
			}
		}
		return shopList;
	}

	@Override
	public AmpShop findByShopCode(String shopCode) {
		return repository.findByShopCode(shopCode);
	}

	@Override
	public Page<AmpShop> pageQuery(AmpShop shop, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("shopCode", GenericPropertyMatchers.exact())
				.withMatcher("shopName", GenericPropertyMatchers.startsWith())
				.withIgnorePaths("id");
		
		if(StringUtils.isBlank(shop.getStatus())) {
			matcher.getIgnoredPaths().add("status");
		}else {
			matcher.withMatcher("status", GenericPropertyMatchers.exact());
		}

		Example<AmpShop> example = Example.of(shop, matcher);

		return repository.findAll(example, pageable);
	}

}
