package io.ai.just.core;

public interface Compiler<T, APP extends App> {
    T compile(APP app);
}
