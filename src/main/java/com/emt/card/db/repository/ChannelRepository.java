package com.emt.card.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emt.card.db.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, String> {
	
}
