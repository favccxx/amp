package com.favccxx.amp.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AmpUser;

public interface UserService extends BaseService<AmpUser> {


	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	AmpUser findByUsername(String username);
	
	
	
	/**
	 * 分页查询用户信息
	 * @param vip
	 * @param pageable
	 * @return
	 */
	Page<AmpUser> pageQuery(AmpUser user, Pageable pageable);
	
}
