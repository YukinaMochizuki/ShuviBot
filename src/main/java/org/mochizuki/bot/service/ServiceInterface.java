package org.mochizuki.bot.service;

import org.mochizuki.bot.service.unit.ServiceContainer;

import java.util.logging.Logger;

public interface ServiceInterface {
    void displayMessage(Logger logger, String message);
    void displayWarnMessage(Logger logger,String message);
    void setProvider(Object plugin, Class serviceClass, Object provider);
    ServiceContainer provide(Class serviceClass);
}
