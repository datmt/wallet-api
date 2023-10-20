package com.datmt.wallet.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.datmt.wallet.api.repositories"})
public class AppConfig {
}
