package org.mochizuki.bot.unit;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.communicate.Communicate;
import org.mochizuki.bot.service.ServiceManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class DeveloperModeTelegram extends TelegramLongPollingBot implements Communicate {

    private Bot bot;

    public DeveloperModeTelegram(Bot bot){
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();

            System.out.print(chat_id);
            bot.getStorage().getRootNode().getNode("Bot","Telegram","ChatNumber").setValue(chat_id);
            bot.getStorage().serveFile();

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }

    @Override
    public String getBotUsername(){
        return GlobalSetting.getBotName();
    }

    @Override
    public String getBotToken(){
        return GlobalSetting.getBotToken();
    }

    @Override
    public String nowCommunicate() {
        return "Telegram";
    }
}
