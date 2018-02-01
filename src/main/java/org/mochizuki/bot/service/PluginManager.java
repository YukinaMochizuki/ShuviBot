package org.mochizuki.bot.service;

import org.mochizuki.bot.service.Plugin.PluginInfo;
import org.mochizuki.bot.service.Plugin.PluginLoader;
import org.mochizuki.bot.unit.GlobalSetting;
import org.mochizuki.bot.unit.LoggerLevels;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PluginManager implements PluginManagerInterface {
    private ServiceManager serviceManager;
    private ServiceInterface serviceInterface;
    private EventManager eventManager = new EventManager(this);
    private ArrayList<PluginInfo> pluginInfoArrayList = new ArrayList<>();
    private Logger logger;
    private BasicIO basicIO;
    private InjectService injectService;


    PluginManager(ServiceManager serviceManager){
        this.logger = Logger.getLogger("Plugin Manager");
        LoggerLevels.setLoggerLevels(logger, GlobalSetting.getLoggerSetting());

        this.serviceInterface = serviceManager;
        this.serviceManager = serviceManager;
        this.basicIO = serviceManager.getBasicIO();
    }

    public void init(){
        this.injectService = new InjectService(serviceManager,this);
        PluginLoader pluginLoader = new PluginLoader(this).init();

        logger.info(" Loading plugin is complete");

        for (PluginInfo pluginInfo : pluginInfoArrayList)pluginInfo.makeObject();
        eventManager.settingUpListener();
        injectService.startingImport();
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

    @Override
    public ServiceInterface getServiceInterface(){
        return this.serviceInterface;
    }

    public InjectService getInjectService() {
        return injectService;
    }
    public ArrayList<PluginInfo> getPluginInfoArrayList() {
        return pluginInfoArrayList;
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public BasicIO getBasicIO() {
        return basicIO;
    }
}
