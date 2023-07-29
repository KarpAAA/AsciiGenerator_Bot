package com.example.asciigenerator_bot.utils;


import org.springframework.stereotype.Component;

@Component
public class TextUtils {
    public String linkText(String text) {
        return "`" + text + "`";
    }
}
