package org.mochizuki.bot.communicate;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.service.ServiceManager;
import org.mochizuki.bot.unit.GlobalSetting;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Telegram extends TelegramLongPollingBot implements Communicate {
    private boolean allReady = false;
    private Bot bot;
    private ServiceManager serviceManager;

    public Telegram(Bot bot){
        this.bot = bot;
    }

    public void setReplyMarkup(long chat_id, @NotNull KeyboardRow keyboardButtons,
                               KeyboardRow keyboardButtons1, KeyboardRow keyboardButtons2){
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardButtons);
        if(keyboardButtons1 != null)keyboardRowList.add(keyboardButtons1);
        if(keyboardButtons2 != null)keyboardRowList.add(keyboardButtons2);


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRowList).setResizeKeyboard(true);

        SendMessage sendMessage = new SendMessage()
                .setChatId(chat_id)
                .setText("Update Keyboard")
                .setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(long chat_id,String message_text){
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(message_text);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chat_id);
//        sendMessage.setText("Custom message text");
//
//        // Create ReplyKeyboardMarkup object
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        // Create the keyboard (list of keyboard rows)
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        // Create a keyboard row
//        KeyboardRow row = new KeyboardRow();
//        // Set each button, you can also use KeyboardButton objects if you need something else than text
//        row.add("Button 1");
//        row.add("Button 2");
//        row.add("Button 3");
//        // Add the first row to the keyboard
//        keyboard.add(row);
//        // Create another keyboard row
//        row = new KeyboardRow();
//        // Set each button for the second line
//        row.add("Row 2 Button 1");
//        row.add("Row 2 Button 2");
//        row.add("Row 2 Button 3");
//        // Add the second row to the keyboard
//        keyboard.add(row);
//        // Set the keyboard to the markup
//        keyboardMarkup.setKeyboard(keyboard).setResizeKeyboard(true);
//        // Add it to the message
//        sendMessage.setReplyMarkup(keyboardMarkup);
//
//        try {
//            // Send the message
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpdateReceived(Update update){
        // We check if the update has a message and the message has text
        if(allReady) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // Set variables
                String message_text = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();

                serviceManager.communicate(message_text,this);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println();
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

    public boolean isAllReady() {
        return allReady;
    }

    public void setAllReady(boolean allReady) {
        this.serviceManager = bot.getServiceManager();
        this.allReady = allReady;
    }
}
