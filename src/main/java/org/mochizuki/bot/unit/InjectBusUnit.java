package org.mochizuki.bot.unit;

import org.mochizuki.bot.event.EventType;
import org.mochizuki.bot.service.Plugin.PluginInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InjectBusUnit {
    private Object object;
    private PluginInfo pluginInfo = null;
    private String type;
    private Method method;
    private Field field;
    private Class injectDataType;

    public InjectBusUnit(Method method,Object object){
        this.method = method;
        this.object = object;
        this.type = "Method,Object";
    }

    public InjectBusUnit(Field field,Object object){
        this.field = field;
        this.object = object;
        this.type = "Field,Object";
    }

    public InjectBusUnit(Method method,Class injectDataType){
        this.method = method;
        this.injectDataType = injectDataType;
        this.type = "Method";
    }

    public InjectBusUnit(Field field,Class injectDataType){
        this.field = field;
        this.injectDataType = injectDataType;
        this.type = "Field";
    }

    public Method getMethod() {
        return method;
    }
    public Field getField() {
        return field;
    }
    public Class getInjectDataType() {
        return injectDataType;
    }
    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }
    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }
    public String getType() {
        return type;
    }
    public Object getObject() {
        return object;
    }
}
