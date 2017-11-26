package org.mochizuki.bot;


import org.mochizuki.bot.unit.LoggerFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        String version = "Alpha 1.0";
        Logger logger = Logger.getLogger("Main thread");

        try {
            for(Handler h : logger.getParent().getHandlers()) {
                if(h instanceof ConsoleHandler) {
                    h.setFormatter(new LoggerFormatter());
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        System.err.print("System build 0.1120");
        logger.info("Starting L2 cache support system");
        logger.info("version " + version);

        Bot bot_main = new Bot();
        bot_main.onInitialization();
    }
}
