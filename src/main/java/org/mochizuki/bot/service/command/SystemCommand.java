package org.mochizuki.bot.service.command;

import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Annotation.SystemCommandAnnotation;
import org.mochizuki.bot.service.BasicIO;
import org.mochizuki.bot.service.Plugin.PluginInfo;
import org.mochizuki.bot.service.PluginManager;
import org.mochizuki.bot.service.ServiceManager;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SystemCommand {
    private ServiceManager serviceManager;
    private PluginManager pluginManager;
    private BasicIO basicIO;
    private Logger logger;

    public SystemCommand(ServiceManager serviceManager){
        this.logger = Logger.getLogger("System Command");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.serviceManager = serviceManager;
        this.pluginManager = serviceManager.getPluginManager();
        this.basicIO = serviceManager.getBasicIO();
    }

    @SystemCommandAnnotation
    public void stop(){
        serviceManager.displayMessage(logger,"Bot stopping...");
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotStoppingServerEvent));
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotStoppedServerEvent));
        System.exit(1);
    }

    @SystemCommandAnnotation
    public void isrunning(String telegram){
        if(telegram.compareTo("true") == 0){
            serviceManager.displayMessage(logger,"Yes");
        }
    }

    @SystemCommandAnnotation
    public void help(){
        ArrayList<CommandIndexUnit> commandIndexUnitArrayList = ServiceManager.getCommandManager().getCommandIndexUnitArrayList();
        String name = "/";

        for(CommandIndexUnit commandIndexUnit : commandIndexUnitArrayList){
            name = name.concat(commandIndexUnit.getName().concat(" /"));
        }

        name = name.substring(0,name.length() - 1 );
        serviceManager.displayMessage(logger , name);
    }

    @SystemCommandAnnotation
    public void pluginlist(){
        ArrayList<PluginInfo> pluginInfoArrayList = pluginManager.getPluginInfoArrayList();
        String name = "";

        if(pluginInfoArrayList.size() == 0){
            name = "none";
        }else {
            for (PluginInfo pluginInfo : pluginInfoArrayList) {
                name = name.concat(pluginInfo.getId() + " ");
            }
        }
        serviceManager.displayMessage(logger,name);
    }
}
