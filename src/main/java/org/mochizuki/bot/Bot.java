package org.mochizuki.bot;

import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.io.HoconReader;
import org.mochizuki.bot.service.ConversationManager;
import org.mochizuki.bot.service.ServiceManager;

import org.mochizuki.bot.unit.DefaultConfig;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Bot {
    private Path path;
    private HoconReader hoconReader;
    private Logger logger;
    private Telegram telegram;

    public Bot(){
        this.logger = Logger.getLogger("Bot Main");
    }

    protected void onInitialization(){
//              Instantiate Config Storage
        logger.info("Instantiate Config Storage");
        boolean willSetDefault = false;
        this.path = Paths.get(".","config.conf");
        if(!Files.exists(path)){
            try {
                Files.createFile(path);
                willSetDefault = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.hoconReader = new HoconReader(logger).setPath(path).init();

//              Import default config
        if(willSetDefault)new DefaultConfig(this.hoconReader).setDefaultConfig();

//              Setting Logger level
        GlobalSetting.setLoggerSetting(hoconReader.getValue("Bot","Global","Logger-level"));
        LoggerLevels.setLoggerLevels(logger,GlobalSetting.getLoggerSetting());
        logger.info("Set Logger levels to " + GlobalSetting.getLoggerSetting());

//      =========================Debug=========================
//        hoconReader.setValue("225","test");
//        hoconReader.setValue("225","52","1234");
//        logger.info("value = " + hoconReader.getValue("225","52"));
//        hoconReader.serveFile();
//      =========================Debug=========================

//      Instantiate Telegram Bots API
        logger.info("Instantiate Telegram Bots API...");
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        this.telegram = new Telegram();
        try {
            telegramBotsApi.registerBot(this.telegram);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.err.print("OK");
//        telegram.sendMessage(240322569,"the init is done");

//        Instantiate ServiceManager
        ServiceManager serviceManager = new ServiceManager(this).init();
        logger.info("Instantiate ServiceManager completed");

//        Instantiate ProjectManager
        logger.info("Instantiate ProjectManager");
        serviceManager.initProjectManager();

//        Instantiate ConversationManager
        logger.info("Instantiate ConversationManager");
        ConversationManager conversationManager = new ConversationManager(serviceManager);

//        Set listener ready
        telegram.setAllReady(true);
    }

    public HoconReader getStorage(){
        return hoconReader;
    }
    public Logger getLogger(){
        return logger;
    }
    public Telegram getTelegram(){
        return telegram;
    }
}
