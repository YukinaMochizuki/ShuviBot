package org.mochizuki.bot.communicate;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.service.ServiceManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Telegram extends TelegramLongPollingBot implements Communicate {

    private boolean allReady = false;
    private Bot bot;
    private ServiceManager serviceManager;

    public Telegram(Bot bot){
        this.bot = bot;
    }

    public void sendMessage(long chat_id,String message_text){
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(message_text);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update){
        // We check if the update has a message and the message has text
        if(allReady) {
            if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getChatId() == 240322569) {
                // Set variables
                String message_text = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();
                System.out.println(message_text);
                serviceManager.communicate(message_text,this);
            }
        }
        // We check if the update has a message and the message has text
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            // Set variables
//            String message_text = update.getMessage().getText();
//            long chat_id = update.getMessage().getChatId();
//
//            System.out.print(chat_id);
//
//            SendMessage message = new SendMessage() // Create a message object object
//                    .setChatId(chat_id)
//                    .setText(message_text);
//            try {
//                execute(message); // Sending our message object to user
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public String getBotUsername(){
        return "Mochizuki_bot";
    }

    @Override
    public String getBotToken(){
        return "492635588:AAGRLpxwMBtpW6Lulhy30Ny3bOJYSTGkn34";
    }

    @Override
    public String nowCommunicate() {
        return "Telegram";
    }

    public boolean isAllReady() {
        return allReady;
    }

    public void setAllReady(boolean allReady) {
        this.serviceManager = bot.getServiceManager();
        this.allReady = allReady;
    }
}
