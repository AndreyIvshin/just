package io.ai.just.impl.dsl;

import io.ai.just.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DslFile implements File<List<String>, Data<List<String>>, Port<List<String>>, Impl<List<String>, ?>, Conf<List<String>>> {
    private final Map<String, Data<List<String>>> datas;
    private final Map<String, Port<List<String>>> ports;
    private final Map<String, Impl<List<String>, ?>> impls;
    private final Map<String, Conf<List<String>>> confs;

    public DslFile(
        final Map<String, Data<List<String>>> datas,
        final Map<String, Port<List<String>>> ports,
        final Map<String, Impl<List<String>, ?>> impls,
        final Map<String, Conf<List<String>>> confs
    ) {
        this.datas = datas;
        this.ports = ports;
        this.impls = impls;
        this.confs = confs;
    }

    public DslFile() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    @Override
    public void data(final String name, final Data<List<String>> data) {
        datas.put(name, data);
    }

    @Override
    public void port(final String name, final Port<List<String>> port) {
        ports.put(name, port);
    }

    @Override
    public void impl(final String name, final Impl<List<String>, ?> impl) {
        impls.put(name, impl);
    }

    @Override
    public void conf(final String name, final Conf<List<String>> conf) {
        confs.put(name, conf);
    }

    @Override
    public List<String> compile() {
        return impls.values().stream()
            .map(Impl::compile)
            .flatMap(List::stream)
            .toList();
    }
}
