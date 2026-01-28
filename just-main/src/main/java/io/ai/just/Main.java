package io.ai.just;

import java.util.List;
import java.util.stream.Collectors;

import io.ai.just.config.Config;
import io.ai.just.core.model.Program;

public class Main {
    public static void main(final String... args) throws Throwable {
        final var config = new Config();
        final var compiler = config.<Program>compiler();
        compiler.compile(new Program() {
            @Override
            public String toString() {
                return List.of(
                    ".equ SYS_WRITE, 1",
                    ".equ SYS_EXIT, 60",
                    ".equ STDOUT, 1",
                    "",
                    ".section .data",
                    "text:",
                    "    .ascii \"Hello World!\\n\"",
                    "text_end:",
                    "    .equ text_len, text_end - text",
                    "",
                    ".section .text",
                    ".globl _start",
                    "_start:",
                    "   movq $SYS_WRITE, %rax",
                    "   movq $STDOUT, %rdi",
                    "   leaq text(%rip), %rsi",
                    "   movq $text_len, %rdx",
                    "   syscall",
                    "",   
                    "   movq $SYS_EXIT, %rax",
                    "   movq $7, %rdi",
                    "   syscall",
                    ""
                ).stream().map(list -> list + "\n").collect(Collectors.joining());
            }
        });
    }
}
