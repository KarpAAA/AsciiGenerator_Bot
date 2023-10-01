package com.example.asciigenerator_bot;

import com.example.asciigenerator_bot.constants.MessagesConstants;
import com.example.asciigenerator_bot.constants.SongsConstants;
import com.example.asciigenerator_bot.entity.WordToSeconds;
import com.example.asciigenerator_bot.services.LoggerServices;
import com.example.asciigenerator_bot.services.MessageServices;
import com.example.asciigenerator_bot.services.TextServices;
import com.example.asciigenerator_bot.utils.TextUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BotApi extends TelegramLongPollingBot {
    private final TextServices textServices;
    private final MessageServices messageServices;
    private final MessagesConstants messagesConstants;
    private final SongsConstants songsConstants;
    private final LoggerServices loggerServices;
    private final TextUtils textUtils;
    private ExecutorService executorService;

    @PostConstruct
    private void postConsMethod(){
        executorService = Executors.newFixedThreadPool(10);
    }

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.key}")
    private String botKey;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botKey;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        executorService.execute(() -> processUpdate(update));
    }
    private void processUpdate(Update update) {
        loggerServices.logIntoFileRequests(update);
        Long chatId = getChatId(update).orElseThrow();
        StringBuilder textToBeSent = new StringBuilder();

        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                textToBeSent.append(messagesConstants.startMessage(getBotUsername()));
            }
            else if (update.getMessage().getText().equals("/help")) {
                textToBeSent.append(messagesConstants.helpMessage());
            }
            else if (update.getMessage().getText().equals("/song")) {
                sendAudioToUser(chatId);
                return;
            }
            else if (update.getMessage().getText().startsWith("/message ")) {
                String sentence = update.getMessage().getText().substring(9);
                showMessage(chatId, textServices.getSentenceBySecondsOutput(sentence));
                return;
            }
            else if (update.getMessage().getText().startsWith("/custom ")) {
                String id = update.getMessage().getText().substring(8);
                showMessage(chatId, messageServices.getCustomSentence(id));
                return;
            }
            else if (update.getMessage().getText().startsWith("/create ")) {
                String createdSentenceId =
                        messageServices.createCustomMessage(update.getMessage().getText().substring(8));
                String messageReply = "Your custom message id is " + textUtils.linkText(createdSentenceId);
                SendMessage sendMessage = new SendMessage(chatId.toString(), messageReply);
                sendMessage.enableMarkdown(true);
                sendApiMethodAsync(sendMessage);
                return;
            }
            else {
                textToBeSent.append(update.getMessage().getText());
                sendAsciiArtMessage(new SendMessage(chatId.toString(), textToBeSent.toString()));
                return;
            }
        }
        sendApiMethodAsync(new SendMessage(chatId.toString(), textToBeSent.toString()));
    }


    private void showMessage(Long chatId, List<WordToSeconds> sentBySec) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(sentBySec.get(0).getMessage());
        int messageId;
        try {
            messageId = sendAsciiArtMessage(sendMessage).get().getMessageId();
            Thread.sleep(sentBySec.get(0).getSeconds());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        Integer finalMessageId = messageId;
        sentBySec.stream().skip(1).forEach(
                structure -> {
                    try {
                        editMessage(chatId, finalMessageId, structure.getMessage(), true);
                        Thread.sleep(structure.getSeconds());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        deleteMessage(messageId, chatId);
    }
    public void sendAudioToUser(long chatId) {
        String audioFilePath =
                "https://jesusful.com/wp-content/uploads/music/2022/07/Bon_Jovi_-_Its_My_Life_(Jesusful.com).mp3";
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        sendAudio.setAudio(new InputFile(audioFilePath));
        try {
            execute(sendAudio);
            sendAudioMessageControllerToUser(chatId);
            playSongText(chatId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendAudioMessageControllerToUser(long chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Plat song in 5 sec!");
        Message sentMessage = execute(sendMessage);
        int messageId = sentMessage.getMessageId();
        int maxValue = 5;
        IntStream
                .rangeClosed(1, maxValue)
                .map(i -> maxValue + 1 - i)
                .forEach(
                        value -> {
                            try {
                                if(value == maxValue) Thread.sleep(1000);
                                editMessage(chatId, messageId, String.valueOf(value),false);
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        deleteMessage(messageId, chatId);
    }
    public void playSongText(long chatId) throws TelegramApiException {
        List<WordToSeconds> sentBySecList = songsConstants.itsMyLifeSong();
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Starting song!");
        int messageId = execute(sendMessage).getMessageId();
        sentBySecList.forEach(
                sentBySec -> {
                    editMessage(chatId,messageId,sentBySec.getMessage(),false);
                    try {
                        Thread.sleep(sentBySec.getSeconds() * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }
    private void deleteMessage(Integer messageId, Long chatId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void editMessage(Long chatId, Integer messageId, String newText, boolean enableMarkdown) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId(messageId);

        if(enableMarkdown){
            newText = textServices.getBlockAsciiMessage(newText);
        }
        editMessageText.setText(newText);
        editMessageText.enableMarkdown(enableMarkdown);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<Message> sendAsciiArtMessage(SendMessage message) {
        message.setText(textServices.getBlockAsciiMessage(message.getText()));
        message.enableMarkdown(true);
        return sendApiMethodAsync(message);
    }
    private Optional<Long> getChatId(Update update) {
        Optional<Long> res = Optional.empty();
        if (update.hasMessage()) res = Optional.of(update.getMessage().getFrom().getId());
        if (update.hasCallbackQuery()) res = Optional.of(update.getCallbackQuery().getFrom().getId());
        return res;
    }

}
