package com.emt.card.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Configuration
@EntityScan(basePackages = "com.emt.card.db.entity")
@EnableJpaRepositories(basePackages = "com.emt.card.db.repository")
@EnableJpaAuditing
public class JpaConfig {

    @Component
    public static class Auditor implements AuditorAware<Long> {
        @Override
        public Optional<Long> getCurrentAuditor() {
            return Optional.of(0L);
        }
    }
}
