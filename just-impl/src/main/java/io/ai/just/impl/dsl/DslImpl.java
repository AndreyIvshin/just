package io.ai.just.impl.dsl;

import io.ai.just.core.Func;
import io.ai.just.core.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DslImpl implements Impl<List<String>, Func<List<String>>> {
    private final Map<String, Func<List<String>>> funcs;

    public DslImpl(final Map<String, Func<List<String>>> funcs) {
        this.funcs = funcs;
    }

    public DslImpl() {
        this(new HashMap<>());
    }

    @Override
    public void func(final String name, final Func<List<String>> func) {
        funcs.put(name, func);
    }

    @Override
    public List<String> compile() {
        return funcs.entrySet().stream()
            .map(e -> {
                final var lines = new ArrayList<String>();
                lines.add(".global " + e.getKey());
                lines.add(e.getKey() + ":");
                e.getValue().compile().forEach(line -> lines.add("   " + line));
                lines.add("");
                return lines;
            })
            .flatMap(List::stream)
            .toList();
    }
}
