package io.ai.just.config;

import io.ai.just.core.service.Compiler;
import io.ai.just.impl.compiler.DslCompiler;

public class Config {
    public <T> io.ai.just.core.service.Compiler<T> compiler() {
        return (Compiler<T>) new DslCompiler("../code.s");
    }
}
