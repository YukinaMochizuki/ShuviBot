package org.mochizuki.bot.service;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.communicate.Communicate;
import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

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
        conversationManager = new ConversationManager(this);

//        Starting load plugin
        logger.info("Instantiate Plugin Manager");
        pluginManager = new PluginManager(this).init();

//        Instantiate CommandManager
        logger.info("Instantiate Command Manager ");
        commandManager = new CommandManager(this).init().indexSystemCommand();

//        Register system listener
        basicIO.registerListener();

        return this;
    }

    public void startCommandDrivenInterface(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            String input = scanner.nextLine();
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
                                parameter.add(input.substring(index_start + 1,index_end - 1));
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
            }
        }
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
