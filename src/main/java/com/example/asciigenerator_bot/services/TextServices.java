package com.example.asciigenerator_bot.services;

import com.example.asciigenerator_bot.entity.WordToSeconds;
import com.github.lalyos.jfiglet.FigletFont;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TextServices {

    public String perenestuSlovo(String word) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < word.length() / 5 + 1; ++i) {
            if(i+1 < word.length() / 5 + 1) words.add(word.substring(i * 5, (i + 1) * 5));
            else words.add(word.substring(i * 5));
        }

        StringBuilder result = new StringBuilder();
        words = words.stream().filter(str -> !str.isBlank()).toList();
        words.stream()
                .limit(words.size() - 1)
                .forEach(s -> result.append(generateAsciiArt(s + "-")).append("\n"));

        result.append(generateAsciiArt(words.get(words.size()-1))).append("\n");
        return result.toString();
    }
    public String generateAsciiArt(String text) {
        try {
            return FigletFont.convertOneLine(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String transformToAsciiArtText(String text) {
        if(text.length()<=6) return generateAsciiArt(text);
        else return perenestuSlovo(text);
    }
    public String getBlockAsciiMessage(String text) {
        return "```\n" + transformToAsciiArtText(text) + "\n```";
    }

    public List<WordToSeconds> getSentenceBySecondsOutput(String sentence) {
        List<WordToSeconds> result = new ArrayList<>();
        List<String> words = Arrays.asList(sentence.split(" "));

        words.forEach(
                word -> result.add(new WordToSeconds(null,1000L ,word)));
        return result;
    }



    public void showMessage(String message){
        List<WordToSeconds> toShow = getSentenceBySecondsOutput(message);
        toShow.forEach(
                structure -> {
                    try {
                        Thread.sleep(structure.getSeconds());
                        System.out.println(transformToAsciiArtText(structure.getMessage()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

}
