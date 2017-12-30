package org.mochizuki.bot.event;

import org.mochizuki.bot.service.Plugin.PluginInfo;

import java.lang.reflect.Method;

public class EventBusUnit {
    private PluginInfo pluginInfo = null;
    private Method method;
    private EventType eventType;
    private Object object = null;

    public EventBusUnit(Method method,EventType eventType){
        this.method = method;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public EventBusUnit setObject(Object object) {
        this.object = object;
        return this;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public Object getObject() {
        return object;
    }
}
