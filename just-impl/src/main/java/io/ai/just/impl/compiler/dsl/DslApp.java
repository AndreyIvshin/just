package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.model.App;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface DslApp extends App, Supplier<Map<String, List<String>>> {
}
