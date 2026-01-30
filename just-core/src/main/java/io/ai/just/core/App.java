package io.ai.just.core;

public interface App<T, FILE extends File> {
    void file(String name, FILE file);

    T compile();
}
