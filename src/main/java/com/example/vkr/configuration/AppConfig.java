package com.example.vkr.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan("com.example.vkr")
@EnableJpaAuditing
public class AppConfig {
}
