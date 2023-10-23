package com.datmt.wallet.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.datmt.wallet.api.repositories"})
@EnableMongoAuditing
public class AppConfig {
}
