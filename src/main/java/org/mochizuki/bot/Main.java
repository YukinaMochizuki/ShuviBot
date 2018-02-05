package org.mochizuki.bot;


import com.google.common.reflect.ClassPath;
import org.mochizuki.bot.unit.LoggerFormatter;
import org.mochizuki.bot.unit.PluginFileVisitor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args)  {
        ArrayList<URL> urlArrayList = new ArrayList<>();

        System.err.println("Loading library...");
        URLClassLoader pluginUrlClassLoader;
        try {
            Path path = Paths.get("plugin");
            if(!Files.exists(path))Files.createDirectory(path);

            PluginFileVisitor pluginFileVisitor = new PluginFileVisitor();
            Files.walkFileTree(path,pluginFileVisitor);

            ArrayList<Path> fileVisitorPathArrayList = pluginFileVisitor.getPathArrayList();

            for(Path pluginPath : fileVisitorPathArrayList) urlArrayList.add(pluginPath.toUri().toURL());

            URL url = Paths.get("").toUri().toURL();
            URL url1 = Paths.get("plugin/ProjectL2.jar").toUri().toURL();
            URL[] urls = (URL[]) urlArrayList.toArray(new URL[0]);

            pluginUrlClassLoader = new URLClassLoader(urls);

            ClassPath classPath = ClassPath.from(pluginUrlClassLoader);

            for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
                if (classInfo.getName().compareTo("org.mochizuki.bot.Main") == 0) {
                    Class mainClass = classInfo.load();
                    Object mainObject = mainClass.newInstance();
                    Method startMethod = mainClass.getDeclaredMethod("start",URLClassLoader.class);
                    startMethod.setAccessible(true);
                    startMethod.invoke(mainObject,pluginUrlClassLoader);
                }
            }
        } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private void start(URLClassLoader pluginUrlClassLoader){
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
            bot_main.onInitialization(pluginUrlClassLoader);
            bot_main.startRunning();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
