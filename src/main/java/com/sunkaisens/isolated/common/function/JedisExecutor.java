package com.sunkaisens.isolated.common.function;

import com.sunkaisens.isolated.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
