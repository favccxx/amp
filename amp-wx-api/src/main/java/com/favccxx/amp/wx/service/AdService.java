package com.favccxx.amp.wx.service;

import java.util.List;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpAd;

public interface AdService extends BaseService<AmpAd> {
	
	/**
	 * 查询首页需要展示的广告
	 * @return
	 */
	List<AmpAd> queryIndex();

}
