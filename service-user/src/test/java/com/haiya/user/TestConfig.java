package com.haiya.user;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "com.haiya.user")
public class TestConfig {
}