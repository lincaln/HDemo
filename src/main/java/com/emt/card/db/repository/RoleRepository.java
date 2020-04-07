package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.Role;
public interface RoleRepository extends JpaRepositoryImplementation<Role, String> {
}
