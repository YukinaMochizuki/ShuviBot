package org.mochizuki.bot.service;

import org.mochizuki.bot.Bot;
import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Plugin.PluginInfo;
import org.mochizuki.bot.service.Plugin.PluginLoader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PluginManager implements PluginManagerInterface {
    private EventManager eventManager = new EventManager(this);
    private ArrayList<PluginInfo> pluginInfoArrayList = new ArrayList<>();
    private Logger logger;
    private BasicIO basicIO;
    private InjectService injectService;

    public PluginManager(){
    }

    public PluginManager(ServiceManager serviceManager){
        this.logger = Logger.getLogger("Plugin Manager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.basicIO = serviceManager.getBasicIO();
        this.injectService = new InjectService(serviceManager,this);
    }

    public PluginManager init(){
        PluginLoader pluginLoader = new PluginLoader(this).init();

        logger.info(" Loading plugin is complete");


//        ============Debug==============
        for (PluginInfo pluginInfo : pluginInfoArrayList)pluginInfo.makeObject();
        injectService.startingImport();
        eventManager.settingUpListener();
        eventManager.post(new Event().setEventType(EventType.BotConstructionEvent));
        eventManager.post(new Event().setEventType(EventType.BotPreInitializationEvent));
        eventManager.post(new Event().setEventType(EventType.BotInitializationEvent));
        eventManager.post(new Event().setEventType(EventType.BotPostInitializationEvent));

        return this;
    }

    public void registrationPlugin(Class<?> aClass){
        pluginInfoArrayList.add(new PluginInfo(aClass));
    }

    public Object findPluginObject(String className){
        for (PluginInfo pluginInfo : pluginInfoArrayList) {
            if(pluginInfo.getObjectClass().getName().equals(className))
                return pluginInfo.getObject();
        }
        return null;
    }

    protected PluginManager makeObject(){
        for (PluginInfo aPluginInfoArrayList : pluginInfoArrayList) {
            aPluginInfoArrayList.makeObject();
        }
        return this;
    }

    public PluginInfo findPlugin(Method method){
        for(PluginInfo pluginInfo : pluginInfoArrayList){
            if(pluginInfo.getName().equals(method.getDeclaringClass().getName())){
                return pluginInfo;
            }
        }
        return null;
        //        TODO Optional
    }

    public PluginInfo findPlugin(Object object){
        for(PluginInfo pluginInfo : pluginInfoArrayList){
            if(pluginInfo.getObject().equals(object)){
                return pluginInfo;
            }
        }
        return null;
        //        TODO Optional
    }

    public PluginInfo findPlugin(String ID){
        for(PluginInfo pluginInfo : pluginInfoArrayList){
            if(pluginInfo.getId().equals(ID)){
                return pluginInfo;
            }
        }
        return null;
        //        TODO Optional
    }

    public InjectService getInjectService() {
        return injectService;
    }
    public ArrayList<PluginInfo> getPluginInfoArrayList() {
        return pluginInfoArrayList;
    }
    public EventManager getEventRegister() {
        return eventManager;
    }
    public BasicIO getBasicIO() {
        return basicIO;
    }
}
