package com.favccxx.amp.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.favccxx.amp.db.base.service.BaseService;
import com.favccxx.amp.db.model.AuditLog;

public interface AuditLogService extends BaseService<AuditLog>{
	
	/**
	 * 分页查询审计日志信息
	 * @param auditLog
	 * @param pageable
	 * @return
	 */
	Page<AuditLog> pageQuery(AuditLog auditLog, Pageable pageable);

}
