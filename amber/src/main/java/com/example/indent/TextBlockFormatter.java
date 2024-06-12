package com.example.indent;

public class TextBlockFormatter {
    private final int CODE_TEXT_BLOCK_INDENT = 4;

    String formatLinesToCodeBlockIndent(String code) {
        return code.indent(CODE_TEXT_BLOCK_INDENT);
    }
}
