package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.CardClazz;
public interface CardClazzRepository extends JpaRepositoryImplementation<CardClazz, String> {
}
