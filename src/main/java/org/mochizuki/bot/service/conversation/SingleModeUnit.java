package org.mochizuki.bot.service.conversation;

import org.mochizuki.bot.service.Plugin.PluginInfo;
import org.mochizuki.bot.service.ServiceManager;

public class SingleModeUnit {
    private Boolean ready = false;

    private Object pluginObject;
    private SingleModeInterface singleModeInterface;

    private PluginInfo pluginInfo;
    private String Id;

    SingleModeUnit(Object pluginObject,SingleModeInterface singleModeInterface){
        this.pluginObject = pluginObject;
        this.singleModeInterface = singleModeInterface;

        pluginInfo = ServiceManager.getPluginManager().findPlugin(pluginObject);
        if (pluginInfo != null){
            this.Id = pluginInfo.getId();
            ready = true;
        }
    }

    public void singleTalk(String inputMessage){
        singleModeInterface.massageInput(inputMessage);
    }

    public String getId() {
        return Id;
    }

    public Boolean getReady() {
        return ready;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public Object getPluginObject() {
        return pluginObject;
    }

    public SingleModeInterface getSingleModeInterface() {
        return singleModeInterface;
    }
}
