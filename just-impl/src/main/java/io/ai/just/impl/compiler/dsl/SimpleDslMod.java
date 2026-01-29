package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.model.Fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleDslMod implements DslMod {
    private final Map<String, Map.Entry<Boolean, DslFun>> funs;
    private final Supplier<DslFun> funGen;

    public SimpleDslMod(final Map<String, Map.Entry<Boolean, DslFun>> funs, final Supplier<DslFun> funGen) {
        this.funs = funs;
        this.funGen = funGen;
    }

    @Override
    public void fun(final String name, final Consumer<Fun> scope) {
        fun(name, false, scope);
    }

    @Override
    public void fun(final String name, final boolean isGlobal, final Consumer<Fun> scope) {
        final var fun = funGen.get();
        funs.put(name, Map.entry(isGlobal, fun));
        scope.accept(fun);
    }

    @Override
    public List<String> get() {
        final var lines = new ArrayList<String>();
        funs.forEach((name, entry) -> {
            final var isGlobal = entry.getKey();
            if (isGlobal) {
                lines.add(".global " + name);
            }
            lines.add(name + ":");
            entry.getValue().get().forEach(line -> lines.add("    " + line));
        });
        return lines;
    }
}
