package org.mochizuki.bot;


import com.google.common.reflect.ClassPath;
import org.mochizuki.bot.unit.LoggerFormatter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args)  {
        System.err.println("Loading library...");
        URLClassLoader pluginUrlClassLoader;
        try {
            URL url = Paths.get(".").toUri().toURL();
            URL url1 = Paths.get(".","plugin").toUri().toURL();
            pluginUrlClassLoader = new URLClassLoader(new URL[] {url,url1});
            ClassPath classPath = ClassPath.from(pluginUrlClassLoader);
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses()) {
                if (classInfo.getName().compareTo("org.mochizuki.bot.Main") == 0) {
                    Class mainClass = classInfo.load();
                        Object mainObject = mainClass.newInstance();
                        Method startMethod = mainClass.getMethod("start");
                        startMethod.invoke(mainObject);
                }
            }
        } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
    public void start(){
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

        System.err.print("System build 0.80128");
        logger.info("Starting L2 cache support system");
        logger.info("version " + version);

        Bot bot_main = new Bot();

        try {
            bot_main.onInitialization();
            bot_main.startRunning();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
