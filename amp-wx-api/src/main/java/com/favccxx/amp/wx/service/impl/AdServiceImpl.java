package com.favccxx.amp.wx.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpAd;
import com.favccxx.amp.db.repository.AdRepository;
import com.favccxx.amp.wx.service.AdService;

@Service
public class AdServiceImpl extends BaseServiceImpl<AdRepository, AmpAd> implements AdService {

	

	@Override
	public List<AmpAd> queryIndex() {		
		return repository.findAll();
	}

}
