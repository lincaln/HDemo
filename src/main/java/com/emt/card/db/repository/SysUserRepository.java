package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.SysUser;
public interface SysUserRepository extends JpaRepositoryImplementation<SysUser, String> {
}
