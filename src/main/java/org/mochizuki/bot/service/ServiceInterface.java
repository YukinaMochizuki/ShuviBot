package org.mochizuki.bot.service;

import org.mochizuki.bot.service.unit.ServiceContainer;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.logging.Logger;

public interface ServiceInterface {
    void displayMessage(Logger logger, String message);
    void displayWarnMessage(Logger logger,String message);
    void setReplyMarkup(@NotNull ArrayList<String> keyboardButtons);
    void setReplyMarkup(@NotNull ArrayList<String> keyboardButtons,@NotNull ArrayList<String> keyboardButtons1);
    void restartReplyMarkup();
    void setProvider(Object plugin, Class serviceClass, Object provider);
    ServiceContainer provide(Class serviceClass);
}
