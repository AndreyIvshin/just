package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.model.Mod;

import java.util.List;
import java.util.function.Supplier;

public interface DslMod extends Mod, Supplier<List<String>> {
}
