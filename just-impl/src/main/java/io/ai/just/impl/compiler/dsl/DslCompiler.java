package io.ai.just.impl.compiler.dsl;

import io.ai.just.core.service.Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Comparator.reverseOrder;

public class DslCompiler implements Compiler<Consumer<DslApp>> {
    private final String folder;
    private final Supplier<DslApp> appGen;

    public DslCompiler(final String folder, final Supplier<DslApp> appGen) {
        this.folder = folder;
        this.appGen = appGen;
    }

    @Override
    public void compile(final Consumer<DslApp> scope) {
        rmdir();
        final var app = appGen.get();
        scope.accept(app);
        final var files = app.get().entrySet();
        for (final var file : files) {
            final var path = Paths.get(folder + "/" + file.getKey());
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, file.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void rmdir() {
        final var path = Paths.get(folder);
        if (!Files.exists(path)) {
            return;
        }
        try (final var files = Files.walk(path)) {
            files.sorted(reverseOrder()).forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}