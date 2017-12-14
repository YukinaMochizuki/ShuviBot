package org.mochizuki.bot.service.Plugin;

import com.google.common.reflect.ClassPath;
import org.mochizuki.bot.configIO.HoconReader;
import org.mochizuki.bot.service.Annotation.Listener;
import org.mochizuki.bot.service.Annotation.Plugin;
import org.mochizuki.bot.service.EventManager;
import org.mochizuki.bot.service.PluginManager;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;
import org.mochizuki.bot.unit.PluginFileVisitor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PluginLoader {
    private Logger logger;
    private Path configPath;
    private boolean notHavePluginConfig = false;
    private HoconReader hoconReader;
    private PluginManager pluginManager;
    private EventManager eventManager;

    public PluginLoader(PluginManager pluginManager){
        this.logger = Logger.getLogger("PluginLoader");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.pluginManager = pluginManager;
        this.eventManager = pluginManager.getEventRegister();

//        this.configPath = Paths.get(".","plugin.conf");
//        if(!Files.exists(configPath)){
//            logger.info("Plugin config not find");
//            notHavePluginConfig = true;
//        }
    }

    public PluginLoader init(){
        if(notHavePluginConfig) return null;

        logger.info("Loading Pligin config");
//              this.hoconReader = new HoconReader(logger).setPath(configPath).init();
        try {
        Path path = Paths.get(".","plugin");
        PluginFileVisitor pluginFileVisitor = new PluginFileVisitor();
        Files.walkFileTree(path,pluginFileVisitor);

//              Get  how many plugin need to load, and load it
        ArrayList<Path> fileVisitorPathgArrayList = pluginFileVisitor.getPathArrayList();

        for(Path pluginPath : fileVisitorPathgArrayList){
            boolean pluginIsSafe = true;
            String className = pluginPath.getFileName().toString().replaceAll(".jar","");
            URL url = pluginPath.toUri().toURL();
            URLClassLoader pluginUrlClassLoader = new URLClassLoader(new URL[] {url});

            Class<?> pluginClass = null;
//            try {
//                pluginClass = pluginUrlClassLoader.loadClass(className);
//                Annotation[] annotatedArrayType = pluginClass.getAnnotations();
//                if(annotatedArrayType != null)logger.info(annotatedArrayType[0].toString());
//                logger.info(pluginClass.getName());
//            } catch (ClassNotFoundException e) {
//                logger.warning(path.getFileName().toString() + "not had same name .class can be loading");
//                logger.warning(path.getFileName().toString() + "loading error");
//                pluginIsSafe = false;
//            }

            ClassPath classPath = ClassPath.from(pluginUrlClassLoader);
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses()) {
                if (classInfo.getName().endsWith(className)) {
                    logger.info("Debug:" + classInfo.getName());
                    Class<?> aClass = classInfo.load();
                    if(aClass.isAnnotationPresent(Plugin.class)){
                        logger.info("Debug : Class have Annotation");
                        pluginManager.registrationPlugin(aClass);

                        Method[] aClassMethods = aClass.getMethods();
                        for (Method aClassMethod : aClassMethods) {
                            if (aClassMethod.isAnnotationPresent(Listener.class)){
                                logger.info("Debug : Methods" + aClassMethod.getName() + "have Annotation");
                                logger.info("Debug : " + aClassMethod.getDeclaringClass().getSimpleName());
                                eventManager.registerListener(aClassMethod);
                            }
                        }
                    }
                }
            }
        }


        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("沒有指定類別載入路徑與名稱");
        }
        catch(MalformedURLException e) {
            System.out.println("載入路徑錯誤");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
