package com.example.asciigenerator_bot;

import com.example.asciigenerator_bot.services.TextServices;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@RequiredArgsConstructor
public class AsciiGeneratorBotApplication {

    private final BotApi bot;
    //private final TextServices textServices;

    public static void main(String[] args) {
        SpringApplication.run(AsciiGeneratorBotApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeBot(){
        return args -> {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
//            System.out.println(textServices.perenestuSlovo("bratuky"));
//            System.out.println(textServices.perenestuSlovo("bratukyyyyy"));
        };
    }
}
