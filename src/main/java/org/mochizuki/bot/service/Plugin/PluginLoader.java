package org.mochizuki.bot.service.Plugin;

import com.google.common.reflect.ClassPath;
import org.mochizuki.bot.service.Annotation.Inject;
import org.mochizuki.bot.service.Annotation.Listener;
import org.mochizuki.bot.service.Annotation.Plugin;
import org.mochizuki.bot.service.EventManager;
import org.mochizuki.bot.service.InjectService;
import org.mochizuki.bot.service.PluginManager;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;
import org.mochizuki.bot.unit.PluginFileVisitor;

import java.io.IOException;
import java.lang.reflect.Field;
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
    private PluginManager pluginManager;
    private EventManager eventManager;
    private InjectService injectService;

    public static ClassPath classPath;

    public PluginLoader(PluginManager pluginManager){
        this.logger = Logger.getLogger("PluginLoader");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.pluginManager = pluginManager;
        this.eventManager = pluginManager.getEventManager();
        this.injectService = pluginManager.getInjectService();
    }

    public PluginLoader init(){
        try {
            Path path = Paths.get("plugin");
            PluginFileVisitor pluginFileVisitor = new PluginFileVisitor();
            Files.walkFileTree(path,pluginFileVisitor);
            ArrayList<Path> fileVisitorPathArrayList = pluginFileVisitor.getPathArrayList();

            ClassPath classPath = ClassPath.from(GlobalSetting.getUrlClassLoader());
            PluginLoader.classPath = classPath;
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses()) {
                for (Path pluginPath : fileVisitorPathArrayList) {
                    String className = pluginPath.getFileName().toString().replaceAll(".jar", "");
                    if (classInfo.getName().endsWith(className)) {
                        logger.info("Debug:" + classInfo.getName());
                        Class<?> aClass = classInfo.load();
                        if (aClass.isAnnotationPresent(Plugin.class)) {
                            logger.info("Debug : Class have Annotation");
                            pluginManager.registrationPlugin(aClass);

                            Method[] aClassMethods = aClass.getDeclaredMethods();
                            for (Method aClassMethod : aClassMethods) {
                                if (aClassMethod.isAnnotationPresent(Listener.class)) {
                                    logger.info("Debug : Methods " + aClassMethod.getName() + " have Listener Annotation");
                                    logger.info("Debug : " + aClassMethod.getDeclaringClass().getSimpleName());
                                    aClassMethod.setAccessible(true);
                                    eventManager.registerListener(aClassMethod);
                                }
                                if (aClassMethod.isAnnotationPresent(Inject.class)) {
                                    logger.info("Debug  : Methods " + aClassMethod.getName() + " have Inject Annotation");
                                    aClassMethod.setAccessible(true);
                                    injectService.Inject(aClassMethod);
                                }
                            }
                            Field[] aClassFields = aClass.getDeclaredFields();
                            for (Field aClassField : aClassFields) {
                                if (aClassField.isAnnotationPresent(Inject.class)) {
                                    logger.info("Debug : Field " + aClassField.getName() + " have Inject Annotation");
                                    aClassField.setAccessible(true);
                                    injectService.Inject(aClassField);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Deprecated
    public PluginLoader init_(){

//        logger.info("Loading Plugin config");
        try {
        Path path = Paths.get("plugin");
        PluginFileVisitor pluginFileVisitor = new PluginFileVisitor();
        Files.walkFileTree(path,pluginFileVisitor);

//              Get  how many plugin need to load, and load it
        ArrayList<Path> fileVisitorPathArrayList = pluginFileVisitor.getPathArrayList();

        for(Path pluginPath : fileVisitorPathArrayList){
            boolean pluginIsSafe = true;
            String className = pluginPath.getFileName().toString().replaceAll(".jar","");
            URL url = pluginPath.toUri().toURL();
            URLClassLoader pluginUrlClassLoader = new URLClassLoader(new URL[] {url});

            /*
            try {
                pluginClass = pluginUrlClassLoader.loadClass(className);
                Annotation[] annotatedArrayType = pluginClass.getAnnotations();
                if(annotatedArrayType != null)logger.info(annotatedArrayType[0].toString());
                logger.info(pluginClass.getName());
            } catch (ClassNotFoundException e) {
                logger.warning(path.getFileName().toString() + "not had same name .class can be loading");
                logger.warning(path.getFileName().toString() + "loading error");
                pluginIsSafe = false;
            }
                    */

            ClassPath classPath = ClassPath.from(GlobalSetting.getUrlClassLoader());
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
                                logger.info("Debug : Methods " + aClassMethod.getName() + " have Listener Annotation");
                                logger.info("Debug : " + aClassMethod.getDeclaringClass().getSimpleName());
                                aClassMethod.setAccessible(true);
                                eventManager.registerListener(aClassMethod);
                            }
                            if(aClassMethod.isAnnotationPresent(Inject.class)){
                                logger.info("Debug  : Methods " + aClassMethod.getName() + " have Inject Annotation");
                                aClassMethod.setAccessible(true);
                                injectService.Inject(aClassMethod);
                            }
                        }
                        Field[] aClassFields = aClass.getDeclaredFields();
                        for (Field aClassField : aClassFields){
                            if(aClassField.isAnnotationPresent(Inject.class)){
                                logger.info("Debug : Field " + aClassField.getName() + " have Inject Annotation");
                                aClassField.setAccessible(true);
                                injectService.Inject(aClassField);
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
