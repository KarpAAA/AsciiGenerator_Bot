package com.example.asciigenerator_bot.constants;

import org.springframework.stereotype.Component;

@Component
public class MessagesConstants {
    public String startMessage(String botName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello in ").append(botName).append("\n\n");
        stringBuilder.append(helpMessage());
        stringBuilder.append("Have fun using this bot!");
        return stringBuilder.toString();
    }
    public String helpMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("To help you i will live a list of command: \n");
        stringBuilder.append("1) /help -> to get commands list\n\n")
                .append("2) /create -> to create custom sentence\n")
                .append("Format: /create [{timesInMills:message},{timesInMills:message},{timesInMills:message}]\n\n")
                .append("3) /message -> to print out sentence\n")
                .append("Format: /message {just simple sentence}\n\n")
                .append("4) /custom {id} -> to print custom sentence\n\n")
                .append("5) if you print just a word you will take a ascii text representation\n\n");
        return stringBuilder.toString();
    }
}
