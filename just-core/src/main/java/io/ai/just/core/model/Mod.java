package io.ai.just.core.model;

import java.util.function.Consumer;

public interface Mod {
    void fun(String name, Consumer<Fun> scope);

    void fun(String name, boolean isGlobal, Consumer<Fun> scope);
}
