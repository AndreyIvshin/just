package io.ai.just.core;

public interface Impl<T, FUNC extends Func<?>> {
    void func(String name, FUNC func);

    T compile();
}
