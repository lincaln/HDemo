package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.CardEquities;
public interface CardEquitiesRepository extends JpaRepositoryImplementation<CardEquities, String> {
}
