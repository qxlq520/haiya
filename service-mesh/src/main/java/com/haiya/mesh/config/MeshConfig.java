package com.haiya.mesh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class MeshConfig {

    @Value("${mesh.circuit-breaker.failure-rate-threshold:50}")
    private int failureRateThreshold;

    @Value("${mesh.circuit-breaker.slow-call-rate-threshold:100}")
    private int slowCallRateThreshold;

    @Value("${mesh.circuit-breaker.wait-duration-in-open-state:60000}")
    private int waitDurationInOpenState;

    @Value("${mesh.circuit-breaker.permitted-number-of-calls-in-half-open-state:10}")
    private int permittedNumberOfCallsInHalfOpenState;

    @Value("${mesh.circuit-breaker.sliding-window-size:100}")
    private int slidingWindowSize;

    @Value("${mesh.circuit-breaker.minimum-number-of-calls:10}")
    private int minimumNumberOfCalls;

    @Value("${mesh.circuit-breaker.slow-call-duration-threshold:5000}")
    private int slowCallDurationThreshold;

    @Value("${mesh.rate-limiter.default-limit-for-period:100}")
    private int defaultLimitForPeriod;

    @Value("${mesh.rate-limiter.default-limit-refresh-period:1000}")
    private int defaultLimitRefreshPeriod;

    @Value("${mesh.rate-limiter.default-timeout-duration:100}")
    private int defaultTimeoutDuration;

    @Value("${mesh.traffic.retry-attempts:3}")
    private int retryAttempts;

    @Value("${mesh.traffic.default-timeout:5000}")
    private int defaultTimeout;

    @Value("${mesh.messaging.rabbitmq.exchange:mesh.exchange}")
    private String rabbitmqExchange;

    @Value("${mesh.messaging.rabbitmq.routing-key-prefix:mesh.event}")
    private String rabbitmqRoutingKeyPrefix;

    @Value("${mesh.messaging.kafka.topic:mesh.events}")
    private String kafkaTopic;

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .slowCallRateThreshold(slowCallRateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
                .permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState)
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(minimumNumberOfCalls)
                .slowCallDurationThreshold(Duration.ofMillis(slowCallDurationThreshold))
                .build();

        return CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(defaultLimitForPeriod)
                .limitRefreshPeriod(Duration.ofMillis(defaultLimitRefreshPeriod))
                .timeoutDuration(Duration.ofMillis(defaultTimeoutDuration))
                .build();

        return RateLimiterRegistry.of(rateLimiterConfig);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(retryAttempts)
                .waitDuration(Duration.ofMillis(100))
                .retryExceptions(Exception.class)
                .build();

        return RetryRegistry.of(retryConfig);
    }

    @Bean
    public TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(defaultTimeout))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // RabbitMQ配置
    @Bean
    public DirectExchange meshExchange() {
        return new DirectExchange(rabbitmqExchange);
    }

    @Bean
    public Queue meshEventQueue() {
        return QueueBuilder.durable("mesh.event.queue").build();
    }

    @Bean
    public Binding meshEventBinding() {
        return BindingBuilder.bind(meshEventQueue())
                .to(meshExchange())
                .with(rabbitmqRoutingKeyPrefix);
    }

    // Kafka配置
    @Bean
    public String kafkaTopic() {
        return kafkaTopic;
    }
}