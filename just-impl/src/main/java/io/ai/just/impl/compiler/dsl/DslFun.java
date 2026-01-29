package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.model.Fun;

import java.util.List;
import java.util.function.Supplier;

public interface DslFun extends Fun, Supplier<List<String>> {
}
