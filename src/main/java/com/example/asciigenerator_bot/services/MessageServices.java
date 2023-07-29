package com.example.asciigenerator_bot.services;

import com.example.asciigenerator_bot.entity.WordToSeconds;
import com.example.asciigenerator_bot.entity.Message;
import com.example.asciigenerator_bot.repository.SentenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MessageServices {
    private final SentenceRepository sentenceRepository;

    public String createCustomMessage(String data) {
        Pattern pattern = Pattern.compile("\\{(\\d+):([\\w\\s]+\\?*)\\}");
        Matcher matcher = pattern.matcher(data);
        List<WordToSeconds> sentBySecList = new ArrayList<>();
        while (matcher.find()) {
            long seconds = Long.parseLong(matcher.group(1));
            String text = matcher.group(2);
            sentBySecList.add(new WordToSeconds(null, seconds, text));
        }
        return saveMessage(sentBySecList);
    }

    public String saveMessage(List<WordToSeconds> sentBySecList){
        return sentenceRepository.save(new Message(UUID.randomUUID().toString(),sentBySecList)).getId();
    }
    public List<WordToSeconds> getCustomSentence(String id) {
        return sentenceRepository.findById(id).get().getSentBySecList();
    }
}
