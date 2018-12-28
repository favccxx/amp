package com.favccxx.amp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
