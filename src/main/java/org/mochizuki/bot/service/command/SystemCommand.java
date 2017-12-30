package org.mochizuki.bot.service.command;

import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Annotation.SystemCommandAnnotation;
import org.mochizuki.bot.service.BasicIO;
import org.mochizuki.bot.service.PluginManager;
import org.mochizuki.bot.service.ServiceManager;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

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
        logger.info("Bot stopping...");
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotStoppingServerEvent));
        pluginManager.getEventManager().post(new Event().setEventType(EventType.BotStoppedServerEvent));
        System.exit(1);
    }
}
