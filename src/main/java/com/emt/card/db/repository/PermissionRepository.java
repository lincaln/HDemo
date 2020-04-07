package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.Permission;
public interface PermissionRepository extends JpaRepositoryImplementation<Permission, String> {
}
