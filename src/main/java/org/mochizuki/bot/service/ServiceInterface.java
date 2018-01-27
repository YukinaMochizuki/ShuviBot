package org.mochizuki.bot.service;

import java.util.logging.Logger;

public interface ServiceInterface {
    void displayMessage(Logger logger, String message);
    void displayWarnMessage(Logger logger,String message);
}
