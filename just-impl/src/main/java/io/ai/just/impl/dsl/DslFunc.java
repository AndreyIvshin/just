package io.ai.just.impl.dsl;

import io.ai.just.core.Func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DslFunc implements Func<List<String>> {
    private final Map<String, Map.Entry<String, String>> vals;
    private final Map<String, Map.Entry<String, String>> vars;
    private final List<String> data;
    private final List<String> text;

    public DslFunc(final Map<String, Map.Entry<String, String>> vals, final Map<String, Map.Entry<String, String>> vars, final List<String> data, final List<String> text) {
        this.vals = vals;
        this.vars = vars;
        this.data = data;
        this.text = text;
    }

    public DslFunc() {
        this(new HashMap<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void val(final String name, final String type, final String bits) {
        if (vals.containsKey(name)) throw new IllegalStateException("Cannot assign to \"" + name + "\"");
        vals.put(name, Map.entry(type, bits));
    }

    @Override
    public void var(final String name, final String type, final String bits) {
        if (vals.containsKey(name)) throw new IllegalStateException("Cannot assign to \"" + name + "\"");
        vars.put(name, Map.entry(type, bits));
    }

    @Override
    public void mov(final String dst, final String src, final String func, final String... args) {
        if (!"".equals(dst) && !vals.containsKey(dst) && !vars.containsKey(dst)) {
            throw new IllegalStateException("Cannot find \"" + dst + "\"");
        }
        if (!vals.containsKey(src) && !vars.containsKey(src)) {
            throw new IllegalStateException("Cannot find \"" + src + "\"");
        }
        for (final var arg : args) {
            if (!vals.containsKey(arg) && !vars.containsKey(arg)) {
                throw new IllegalStateException("Cannot find \"" + arg + "\"");
            }
        }
        if (vals.containsKey(dst) && dst.equals(src)) {
            throw new IllegalStateException("Cannot assign to \"" + dst + "\"");
        }
        final var type = vals.get(src).getKey();
        if (type.equals("just.Sys")) {
            if (func.equals("exit")) {
                if (args.length == 0) {
                    text.addAll(List.of(
                        "movq $60, %rax",
                        "movq $0, %rdi",
                        "syscall"
                    ));
                    return;
                }
                if (args.length == 1) {
                    var arg1 = vals.get(args[0]);
                    if (arg1 == null) arg1 = vars.get(args[0]);
                    if (arg1.getKey().equals("just.U8")) {
                        text.addAll(List.of(
                            "movq $60, %rax",
                            "movq $" + arg1.getValue() + ", %rdi",
                            "syscall"
                        ));
                        return;
                    }
                }
            }
            if (func.equals("print")) {
                if (args.length == 1) {
                    var arg1 = vals.get(args[0]);
                    if (arg1 == null) arg1 = vars.get(args[0]);
                    if (arg1.getKey().equals("just.Str")) {
                        data.addAll(List.of(
                            "text:",
                            "    .ascii \"" + arg1.getValue() + "\"",
                            "text_end:",
                            "    .equ text_len, text_end - text"
                        ));
                        text.addAll(List.of(
                            "movq $1, %rax",
                            "movq $1, %rdi",
                            "leaq text(%rip), %rsi",
                            "movq $text_len, %rdx",
                            "syscall"
                        ));
                        return;
                    }
                }
            }
        }
        throw new IllegalStateException("Cannot find \"" + func + "\"");
    }

    @Override
    public void ret(final String name) {
        text.add("mov $" + name + ", %rax");
    }

    @Override
    public List<String> data() {
        return data;
    }

    @Override
    public List<String> text() {
        return text;
    }
}
