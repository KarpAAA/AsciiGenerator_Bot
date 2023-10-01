package com.example.asciigenerator_bot.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j2
public class LoggerServices {
    public void logIntoFileRequests(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nUser: ").append(update.getMessage().getFrom().getUserName()).append("\n");
        stringBuilder.append("Chat_id: ").append(update.getMessage().getChatId()).append("\n");
        stringBuilder.append("Message: ").append(update.getMessage().getText());
        log.info(stringBuilder.toString());
    }
}
