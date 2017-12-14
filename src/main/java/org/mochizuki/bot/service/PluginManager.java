package org.mochizuki.bot.service;

import org.mochizuki.bot.event.Event;
import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Plugin.PluginInfo;
import org.mochizuki.bot.service.Plugin.PluginLoader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PluginManager {
    private EventManager eventManager = new EventManager(this);
    private ArrayList<PluginInfo> pluginInfoArrayList = new ArrayList<>();
    private Logger logger;

    public PluginManager(){
        this.logger = Logger.getLogger("ServiceManager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());
    }

    public PluginManager init(){
        PluginLoader pluginLoader = new PluginLoader(this).init();

        logger.info(" Loading plugin is complete");

//        ============Debug==============
        for (PluginInfo pluginInfo : pluginInfoArrayList)pluginInfo.makeObject();
        eventManager.settingUpListener();
        eventManager.post(new Event().setEventType(EventType.BotConstructionEvent));
        return this;
    }

    public void registrationPlugin(Class<?> aClass){
        pluginInfoArrayList.add(new PluginInfo(aClass));
    }

    public Object findPluginObject(String className){
        for (PluginInfo aPluginInfoArrayList : pluginInfoArrayList) {
            if(aPluginInfoArrayList.getObjectClass().getName().equals(className))
                return aPluginInfoArrayList.getObject();
        }
        return null;
    }

    protected void makeObject(){
        for (PluginInfo aPluginInfoArrayList : pluginInfoArrayList) {
            aPluginInfoArrayList.makeObject();
        }
    }

    public ArrayList<PluginInfo> getPluginInfoArrayList() {
        return pluginInfoArrayList;
    }

    public EventManager getEventRegister() {
        return eventManager;
    }
}
