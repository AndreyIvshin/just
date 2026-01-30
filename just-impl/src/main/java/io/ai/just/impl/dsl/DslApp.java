package io.ai.just.impl.dsl;

import io.ai.just.core.App;
import io.ai.just.core.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DslApp implements App<Map<String, List<String>>, File<List<String>, ?, ?, ?, ?>> {
    private final Map<String, File<List<String>, ?, ?, ?, ?>> files;

    public DslApp(final Map<String, File<List<String>, ?, ?, ?, ?>> files) {
        this.files = files;
    }

    public DslApp() {
        this(new HashMap<>());
    }

    @Override
    public void file(final String name, final File<List<String>, ?, ?, ?, ?> file) {
        files.put(name, file);
    }

    public Map<String, List<String>> compile() {
        return files.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().compile()));
    }
}
