package ua.novaarchitecture.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration for enabling async processing.
 * Used for sending emails asynchronously.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
