package io.ai.just.core;

public interface File<T, DATA extends Data<?>, PORT extends Port<?>, IMPL extends Impl<?, ?>, CONF extends Conf<?>> {
    void data(String name, DATA data);

    void port(String name, PORT port);

    void impl(String name, IMPL impl);

    void conf(String name, CONF conf);

    T compile();
}
