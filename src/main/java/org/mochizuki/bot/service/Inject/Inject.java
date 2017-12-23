package org.mochizuki.bot.service.Inject;

import org.mochizuki.bot.communicate.Telegram;
import org.mochizuki.bot.service.*;

public class Inject {
    private BasicIO basicIO;
    private PluginManagerInterface pluginManagerInterface;
    private PluginManager pluginManager;
    private ConversationManager conversationManager;
    private Telegram telegram;

    public Inject(ServiceManager serviceManager){
        this.basicIO = serviceManager.getBasicIO();
        this.conversationManager = serviceManager.getConversationManager();
        this.telegram = serviceManager.getTelegram();
        this.pluginManagerInterface = pluginManager;
        this.pluginManager = pluginManager;
    }


}
