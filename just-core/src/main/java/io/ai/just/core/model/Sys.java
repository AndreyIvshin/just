package io.ai.just.core.model;

public interface Sys {
    void exit();

    void exit(int code);

    void print(Str str);
}
