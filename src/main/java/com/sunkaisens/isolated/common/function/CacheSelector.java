package com.sunkaisens.isolated.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
