package com.favccxx.amp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.service.UserService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.SysRole;
import com.favccxx.amp.db.model.SysUser;
import com.favccxx.amp.db.repository.RoleRepository;
import com.favccxx.amp.db.repository.UserRepository;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository, SysUser> implements UserService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public SysUser findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public Page<SysUser> pageQuery(SysUser user, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<SysUser> page = repository.findAll(pageRequest);
        return page;
	}

	@Override
	public List<String> findRoleCodesByUserId(long userId) {
		List<SysRole> roleList = roleRepository.findByUserId(userId);
		List<String> list = new ArrayList<String>();
		for(SysRole role : roleList) {
			list.add(role.getRoleCode());
		}
		return list;
	}


}
