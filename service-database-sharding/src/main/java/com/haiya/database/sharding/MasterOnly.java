package com.haiya.database.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 强制使用主库查询注解
 * 用于在读写分离环境中，强制某些读操作使用主库以避免主从延迟问题
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MasterOnly {
}