package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.Registration;
public interface RegistrationRepository extends JpaRepositoryImplementation<Registration, String> {
}
