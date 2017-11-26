package org.mochizuki.bot.communicate;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Telegram extends TelegramLongPollingBot implements Communicate {

    private boolean allReady = false;

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
            }
        }
        // We check if the update has a message and the message has text
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            // Set variables
//            String message_text = update.getMessage().getText();
//            long chat_id = update.getMessage().getChatId();
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
        return "mochizuki_no_bot";
    }

    @Override
    public String getBotToken(){
        return "334160538:AAH5zUjzrZ6BI3Yp-HG_KdtoXGOTGEd-eRM";
    }

    @Override
    public String nowCommunicate() {
        String nowCommunicate = "Telegram";
        return nowCommunicate;
    }

    public boolean isAllReady() {
        return allReady;
    }

    public void setAllReady(boolean allReady) {
        this.allReady = allReady;
    }
}
