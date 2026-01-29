package io.ai.just.core.model;

import java.util.function.Consumer;

public interface App {
    void mod(String name, Consumer<Mod> scope);
}
