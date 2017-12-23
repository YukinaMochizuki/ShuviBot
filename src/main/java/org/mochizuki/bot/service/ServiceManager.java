package org.mochizuki.bot.service;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.communicate.Communicate;
import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

public class ServiceManager {
    private Telegram telegram;
    private HoconReader hoconReader;
    private Logger logger;
    private BasicIO basicIO;
    private ConversationManager conversationManager;


    private String nowCommunicate;

    public ServiceManager(Bot bot){
        this.hoconReader = bot.getStorage();
        this.telegram = bot.getTelegram();
        this.logger = Logger.getLogger("ServiceManager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());
    }

    public void communicate(String input, Communicate communicate){
        this.nowCommunicate = communicate.nowCommunicate();
//        TODO
    }

    public ServiceManager init(){
//              Instantiate BIOS
        this.basicIO = new BasicIO();

        logger.info("Instantiate Service Manager");

//              Default type is Hocon
        if(hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("null") ||
                hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("Hocon"))
            basicIO.setHoconType();

//              Init conversation
        this.conversationManager = new ConversationManager(this);

        return this;
    }



    public BasicIO getBasicIO(){
        if (this.basicIO == null) {
            logger.severe("Please Instantiate Service Manager first before getBasicIO");
            throw new NullPointerException();
        }
        return this.basicIO;
    }

    public String getNowCommunicate(){
        return this.nowCommunicate;
    }
    public Telegram getTelegram() {
        return telegram;
    }
    public ConversationManager getConversationManager() {
        return conversationManager;
    }
}
