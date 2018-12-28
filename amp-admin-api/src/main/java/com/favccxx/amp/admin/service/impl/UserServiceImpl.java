package com.favccxx.amp.admin.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.admin.service.UserService;
import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AmpUser;
import com.favccxx.amp.db.repository.UserRepository;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserRepository, AmpUser> implements UserService {

	@Override
	public AmpUser findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public Page<AmpUser> pageQuery(AmpUser user, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<AmpUser> page = repository.findAll(pageRequest);
        return page;
	}


}
