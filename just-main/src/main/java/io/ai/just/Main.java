package io.ai.just;

import io.ai.just.impl.dsl.DslApp;
import io.ai.just.impl.dsl.DslCompiler;
import io.ai.just.impl.dsl.DslFile;
import io.ai.just.impl.dsl.DslImpl;

import java.util.List;

public class Main {
    public static void main(final String... args) {
        final var compiler = new DslCompiler("../out");
        compiler.compile(new DslApp() {{
            file("main.just", new DslFile() {{
                impl("MainApp", new DslImpl() {{
                    func("_start", () -> List.of(
                        "movq $60, %rax",
                        "movq $7, %rdi",
                        "syscall"
                    ));
                }});
            }});
        }});
    }
}
