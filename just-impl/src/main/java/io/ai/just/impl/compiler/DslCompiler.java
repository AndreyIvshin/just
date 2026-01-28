package io.ai.just.impl.compiler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.ai.just.core.model.Program;

public class DslCompiler implements io.ai.just.core.service.Compiler<Program> {
    private final String path;

    public DslCompiler(final String path) {
        this.path = path;
    }

    @Override
    public void compile(final Program program) {
        try {
            Files.writeString(Paths.get(path), program.toString(), StandardOpenOption.WRITE);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
