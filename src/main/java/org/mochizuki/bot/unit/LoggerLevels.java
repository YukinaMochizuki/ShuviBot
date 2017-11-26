package org.mochizuki.bot.unit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerLevels {
    public static void setLoggerLevels(Logger logger,String levelStr) {
        if (levelStr.equals("ALL")) logger.setLevel(Level.ALL);
        if (levelStr.equals("INFO")) logger.setLevel(Level.INFO);
        if (levelStr.equals("FINE")) logger.setLevel(Level.FINE);
        if (levelStr.equals("FINER")) logger.setLevel(Level.FINER);
        if (levelStr.equals("FINEST"))logger.setLevel(Level.FINEST);
    }
}
