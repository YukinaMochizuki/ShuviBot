package org.mochizuki.bot.service.unit;

public class ServiceContainer {
    private Object provider;
    private Class serviceClass;
    private Object plugin;
    public ServiceContainer(Object plugin, Class serviceClass, Object provider){
        this.plugin = plugin;
        this.provider = provider;
        this.serviceClass = serviceClass;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public Object getPlugin() {
        return plugin;
    }

    public Object getProvider() {
        return provider;
    }
}
