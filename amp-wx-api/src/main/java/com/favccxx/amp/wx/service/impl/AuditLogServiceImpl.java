package com.favccxx.amp.wx.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.favccxx.amp.db.base.service.impl.BaseServiceImpl;
import com.favccxx.amp.db.model.AuditLog;
import com.favccxx.amp.db.repository.AuditLogRepository;
import com.favccxx.amp.wx.service.AuditLogService;

@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLogRepository, AuditLog> implements AuditLogService {
	

	@Override
	public Page<AuditLog> pageQuery(AuditLog auditLog, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<AuditLog> page = repository.findAll(pageRequest);
        return page;
	}

}
