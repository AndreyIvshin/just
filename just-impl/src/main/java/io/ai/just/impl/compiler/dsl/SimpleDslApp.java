package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.model.Mod;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SimpleDslApp implements DslApp {
    private final Map<String, DslMod> mods;
    private final Supplier<DslMod> modGen;

    public SimpleDslApp(final Map<String, DslMod> mods, final Supplier<DslMod> modGen) {
        this.mods = mods;
        this.modGen = modGen;
    }

    @Override
    public void mod(final String name, final Consumer<Mod> scope) {
        final var mod = modGen.get();
        mods.put(name, mod);
        scope.accept(mod);
    }

    @Override
    public Map<String, List<String>> get() {
        return mods.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().get()
            ));
    }
}
