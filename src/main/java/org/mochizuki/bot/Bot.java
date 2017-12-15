package org.mochizuki.bot;

import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.service.ConversationManager;
import org.mochizuki.bot.service.Plugin.PluginLoader;
import org.mochizuki.bot.service.PluginManager;
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
    private Path configPath;
    private HoconReader hoconReader;
    private Logger logger;
    private Telegram telegram;
    private ServiceManager serviceManager;

    Bot(){
        this.logger = Logger.getLogger("Bot Main");
    }

    protected void onInitialization() throws IOException {
//              Instantiate Config Storage
        logger.info("Instantiate Config Storage");
        boolean willSetDefault = false;
        this.configPath = Paths.get(".","config.conf");
        if(!Files.exists(configPath)){
            try {
                Files.createFile(configPath);
                willSetDefault = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.hoconReader = new HoconReader(logger).setPath(configPath).init();

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

//              Instantiate Telegram Bots API
        logger.info("Instantiate Telegram Bots API...");
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        this.telegram = new Telegram(this);
        try {
            telegramBotsApi.registerBot(this.telegram);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.err.print("OK");
//        telegram.sendMessage(240322569,"the init is done");

//              Instantiate ServiceManager
        this.serviceManager = new ServiceManager(this).init();
        logger.info("Instantiate ServiceManager completed");

//              Instantiate ConversationManager
        logger.info("Instantiate ConversationManager");
        ConversationManager conversationManager = new ConversationManager(serviceManager);

//              Starting load plugin
        logger.info("Instantiate Plugin Manager");
        PluginManager pluginManager = new PluginManager(serviceManager).init();




//              Set  Telegram listener ready
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
    public ServiceManager getServiceManager() {
        return serviceManager;
    }
}
