package com.restaurant.management.config;

import com.restaurant.management.audit.AuditingAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditingAwareImpl();
    }
}


