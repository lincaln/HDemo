package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.CardPeriod;
public interface CardPeriodRepository extends JpaRepositoryImplementation<CardPeriod, String> {
}
