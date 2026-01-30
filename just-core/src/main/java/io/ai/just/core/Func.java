package io.ai.just.core;

public interface Func<T> {
    void val(String name, String type, String bits);

    void var(String name, String type, String bits);

    void mov(String dst, String src, String func, String... args);

    void ret(String name);

    T data();

    T text();
}
