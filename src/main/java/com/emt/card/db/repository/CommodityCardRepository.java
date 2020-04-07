package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.CommodityCard;
public interface CommodityCardRepository extends JpaRepositoryImplementation<CommodityCard, String> {
}
