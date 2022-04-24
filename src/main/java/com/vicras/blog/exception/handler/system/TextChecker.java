package com.vicras.blog.exception.handler.system;

import org.springframework.stereotype.Component;

@Component
public class TextChecker {

    public boolean containsCyrillic(String text) {
        return text.chars()
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(Character.UnicodeBlock.CYRILLIC::equals);
    }
}
