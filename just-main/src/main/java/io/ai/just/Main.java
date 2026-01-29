package io.ai.just;

import io.ai.just.core.model.Sys;
import io.ai.just.impl.compiler.dsl.*;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(final String... args) {
        final var compiler = new DslCompiler("../out", Main::dslApp);
        compiler.compile(app -> {
            app.mod("code.s", mod -> {
                mod.fun("_start", true, fun -> {
//                    fun.sys().exit(7);
                });
            });
        });
    }

    private static DslApp dslApp() {
        return new SimpleDslApp(new HashMap<>(), Main::dslMod);
    }

    private static DslMod dslMod() {
        return new SimpleDslMod(new HashMap<>(), Main::dslFun);
    }

    private static DslFun dslFun() {
        return new DslFun() {
            @Override
            public Sys sys() {
                return null;
            }

            @Override
            public List<String> get() {
                return List.of(
                    "movq $60, %rax",
                    "movq $77, %rdi",
                    "syscall"
                );
            }
        };
    }
}
