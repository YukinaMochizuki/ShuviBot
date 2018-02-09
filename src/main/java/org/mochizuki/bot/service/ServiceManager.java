package org.mochizuki.bot.service;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.communicate.CDI;
import org.mochizuki.bot.communicate.Communicate;
import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.conversation.ConversationMode;
import org.mochizuki.bot.service.unit.ServiceContainer;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ServiceManager implements ServiceInterface {
    private Telegram telegram;
    private HoconReader hoconReader;
    private Logger logger;
    private BasicIO basicIO;

    private static ConversationManager conversationManager;
    private static CommandManager commandManager;
    private static PluginManager pluginManager;

    private ArrayList<ServiceContainer> serviceContainerArrayList = new ArrayList<>();

    private String nowCommunicate = "CDI";

    public ServiceManager(Bot bot){
        this.hoconReader = bot.getStorage();
        this.telegram = bot.getTelegram();
        this.logger = Logger.getLogger("ServiceManager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());
    }

    public synchronized void communicate(String input, Communicate communicate){
        this.nowCommunicate = communicate.nowCommunicate();
        logger.info( "Message Input("+ communicate.nowCommunicate() + "): " + input);
        switch (conversationManager.getConversationMode()){
            case TalkMode:
                this.globalCommand(input, communicate);
                break;
            case SingleMode:
                conversationManager.communicate(input);
                break;
        }
    }

    public synchronized void globalCommand(String input, Communicate communicate){
        if(input.startsWith("/")){
            ArrayList<String> parameter = new ArrayList<>();
            if(input.contains(" ")){
                boolean doFirst = true;
                int index_start = 0;
                int index_end = 0;
                while (true){
                    index_start = input.indexOf(" ",index_start);

                    if(doFirst){
                        parameter.add(input.substring(1,index_start));
                        doFirst = false;
                    }

                    if(index_start == -1)break;
                    else {
                        if(input.indexOf(" ",index_start + 1) != -1){
                            index_end = input.indexOf(" ",index_start + 1);
                            parameter.add(input.substring(index_start + 1,index_end));
                            index_start++;
                        }else {
                            parameter.add(input.substring(index_start + 1));
                            index_start++;
                        }
                    }
                }
            }else {
                parameter.add(input.substring(1));
            }
            commandManager.cellCommand(parameter);
        }else conversationManager.communicate(input);
    }

    @Override
    public synchronized void displayMessage(Logger logger,String message){

        if(nowCommunicate.compareTo("Telegram") == 0){
            telegram.sendMessage(GlobalSetting.getChatNumber(),message);

            if(GlobalSetting.getLoggerSetting().compareTo("FINE") == 0 && logger != null) logger.info(message);
            else if(GlobalSetting.getLoggerSetting().compareTo("FINE") == 0 && logger == null) this.logger.info("Unknown logger: " + message);

        }else if(nowCommunicate.compareTo("CDI") == 0 && logger != null) logger.info(message);

        else if(nowCommunicate.compareTo("CDI") == 0 && logger == null)this.logger.info("Unknown logger: " + message);
    }

    @Override
    public synchronized void displayWarnMessage(Logger logger,String message){

        if(nowCommunicate.compareTo("Telegram") == 0){
            telegram.sendMessage(GlobalSetting.getChatNumber(),"Warning : ".concat(message));

            if(logger != null) logger.info(message);
            else this.logger.info("Unknown logger: " + message);

        }else if(nowCommunicate.compareTo("CDI") == 0 && logger != null) logger.warning(message);

        else if(nowCommunicate.compareTo("CDI") == 0 && logger == null)this.logger.warning("Unknown logger: " + message);
    }

    public void setReplyMarkup(@NotNull ArrayList<String> keyboardButtons){
        KeyboardRow keyboardRow = new KeyboardRow();
        for(String string:keyboardButtons)keyboardRow.add(string);

        telegram.setReplyMarkup(GlobalSetting.getChatNumber(),keyboardRow,null);
    }

    public void setReplyMarkup(@NotNull ArrayList<String> keyboardButtons,@NotNull ArrayList<String> keyboardButtons1){
        KeyboardRow keyboardRow = new KeyboardRow();
        for(String string:keyboardButtons)keyboardRow.add(string);

        KeyboardRow keyboardRow1 = new KeyboardRow();
        for(String string:keyboardButtons1)keyboardRow.add(string);

        telegram.setReplyMarkup(GlobalSetting.getChatNumber(),keyboardRow,keyboardRow1);
    }

    public ServiceManager init(){
//        Instantiate BIOS
        this.basicIO = new BasicIO();

        logger.info("Instantiate Service Manager");

//        Default type is Hocon
        if(hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("null") ||
                hoconReader.getValue("Bot","ServiceManager","BasicIO","type").equals("Hocon"))
            basicIO.setHoconType();

//        Init EventManager
        pluginManager = new PluginManager(this);

//        Instantiate ConversationManager
        logger.info("Instantiate ConversationManager");
        conversationManager = new ConversationManager(this);

//        Instantiate CommandManager
        logger.info("Instantiate Command Manager ");
        commandManager = new CommandManager(this).init().indexSystemCommand();

//        Starting load plugin
        logger.info("Instantiate Plugin Manager");
        pluginManager.init();

        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotConstructionEvent));

//        Register system listener
        basicIO.registerListener();

        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotPreInitializationEvent));
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotInitializationEvent));
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotPostInitializationEvent));
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotLoadCompleteEvent));

        return this;
    }

    public void startCommandDrivenInterface(){
        CDI cdi = new CDI(this);
        cdi.start();
    }

    public BasicIO getBasicIO(){
        if (this.basicIO == null) {
            logger.severe("Please Instantiate Service Manager first before getBasicIO");
            throw new NullPointerException();
        }
        return this.basicIO;
    }

    public void setProvider(Object plugin, Class serviceClass, Object provider){
        serviceContainerArrayList.add(new ServiceContainer(plugin,serviceClass,provider));
    }

    public ServiceContainer provide(Class serviceClass){
        for(ServiceContainer serviceContainer: serviceContainerArrayList){
            if(serviceContainer.getServiceClass().getName().compareTo(serviceClass.getName()) == 0){
                return serviceContainer;
            }
        }
        return null;
    }

    public String getNowCommunicate(){
        return this.nowCommunicate;
    }
    public Telegram getTelegram() {
        return telegram;
    }

    public static ConversationManager getConversationManager() {
        return conversationManager;
    }
    public static CommandManager getCommandManager() {
        return commandManager;
    }
    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}
