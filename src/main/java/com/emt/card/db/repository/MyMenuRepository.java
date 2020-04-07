package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.emt.card.db.entity.MyMenu;
public interface MyMenuRepository extends JpaRepositoryImplementation<MyMenu, String> {
}
