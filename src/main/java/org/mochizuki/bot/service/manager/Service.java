package org.mochizuki.bot.service.manager;

import org.mochizuki.bot.service.ConversationManager;

public abstract class Service {
    private ConversationManager conversationManager;

    private void setConversationManager(ConversationManager conversationManager1){
        this.conversationManager = conversationManager1;
    }

    public abstract void conversationThreadManager(String[] strings);
}
