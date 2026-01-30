package io.ai.just;

import io.ai.just.impl.dsl.*;

public class Main {
    public static void main(final String... args) {
        final var compiler = new DslCompiler("../out");
        compiler.compile(new DslApp() {{
            file("main.just", new DslFile() {{
                impl("MainApp", new DslImpl() {{
                    func("_start", new DslFunc() {{
                        val("sys", "just.Sys", "");
                        val("code", "just.U8", "7");
                        val("text", "just.Str", "Hello!\\n");
                        mov("", "sys", "print", "text");
                        mov("", "sys", "exit", "code");
                    }});
                }});
            }});
        }});
    }
}
