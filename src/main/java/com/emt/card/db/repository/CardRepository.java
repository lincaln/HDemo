package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.Card;
public interface CardRepository extends JpaRepositoryImplementation<Card, String> {
}
