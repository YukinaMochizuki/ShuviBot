package org.mochizuki.bot.service;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.communicate.Communicate;
import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

public class ServiceManager implements ServiceInterface {
    private Telegram telegram;
    private HoconReader hoconReader;
    private Logger logger;
    private BasicIO basicIO;

    private ConversationManager conversationManager;
    private CommandManager commandManager;
    private PluginManager pluginManager;

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
//        Instantiate BIOS
        this.basicIO = new BasicIO();

        logger.info("Instantiate Service Manager");

//        Default type is Hocon
        if(hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("null") ||
                hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("Hocon"))
            basicIO.setHoconType();


//        Instantiate ConversationManager
        logger.info("Instantiate ConversationManager");
        this.conversationManager = new ConversationManager(this);

//        Starting load plugin
        logger.info("Instantiate Plugin Manager");
        this.pluginManager = new PluginManager(this).init();

//        Instantiate CommandManager
        logger.info("Instantiate Command Manager ");
        commandManager = new CommandManager(this).init().indexSystemCommand();

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
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
