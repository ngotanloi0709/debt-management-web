package com.ngtnl1.dmw.config;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TimeZoneConfig {
    @Value("${APP_TIMEZONE}")
    private String applicationTimezone;

    @PostConstruct
    public void configureTimezone() {
        try {
            TimeZone timeZone = TimeZone.getTimeZone(applicationTimezone);
            TimeZone.setDefault(timeZone);

            System.setProperty("user.timezone", applicationTimezone);

            log.info("Application timezone configured to: {} ({})",
                    applicationTimezone, timeZone.getDisplayName());
            log.info("Current JVM timezone: {}", TimeZone.getDefault().getID());
        } catch (Exception e) {
            log.error("Failed to set timezone: {}. Falling back to UTC", applicationTimezone, e);

            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

            System.setProperty("user.timezone", "UTC");
        }
    }
}