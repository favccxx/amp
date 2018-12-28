package com.favccxx.amp.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.SysUser;

public interface UserService extends BaseService<SysUser> {

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);

	/**
	 * 分页查询用户信息
	 * 
	 * @param vip
	 * @param pageable
	 * @return
	 */
	Page<SysUser> pageQuery(SysUser user, Pageable pageable);

	/**
	 * 根据用户Id查询角色代码列表
	 * 
	 * @param userId
	 * @return
	 */
	List<String> findRoleCodesByUserId(long userId);

}
