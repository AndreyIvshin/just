package io.ai.just.impl.dsl;

import io.ai.just.core.App;
import io.ai.just.core.Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.reverseOrder;

public class DslCompiler implements Compiler<Integer, App<Map<String, List<String>>, ?>> {
    private final String folder;

    public DslCompiler(final String folder) {
        this.folder = folder;
    }

    public Integer compile(final App<Map<String, List<String>>, ?> app) {
        rmdir();
        final var files = app.compile().entrySet();
        for (final var file : files) {
            final var path = Paths.get(folder + "/" + file.getKey().replace(".just", ".s"));
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, file.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return files.size();
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
