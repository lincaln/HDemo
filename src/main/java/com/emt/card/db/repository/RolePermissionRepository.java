package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.RolePermission;
public interface RolePermissionRepository extends JpaRepositoryImplementation<RolePermission, String> {
}
