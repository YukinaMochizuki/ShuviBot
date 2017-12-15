package org.mochizuki.bot.service;

import org.mochizuki.bot.service.Plugin.PluginInfo;

import java.lang.reflect.Method;

public interface PluginManagerInterface {

    public PluginInfo findPlugin(Method method);

    public PluginInfo findPlugin(Object object);

    public PluginInfo findPlugin(String ID);

    public BasicIO getBasicIO();

}
