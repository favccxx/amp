package com.favccxx.amp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.favccxx.amp.db.model.SysRole;

@Repository
public interface RoleRepository extends JpaRepository<SysRole, Long> {

	@Query(value = "select distinct r from SysRole r left join SysUserRole ur on ur.roleId=r.id and ur.userId=?1")
	List<SysRole> findByUserId(long userId);
	
}
