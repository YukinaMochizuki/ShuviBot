package org.mochizuki.bot.service.Plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mochizuki.bot.service.Annotation.Plugin;

import java.lang.reflect.Method;

public class PluginInfo {
    private Class<?> aClass;
    private String className;
    private Object object = null;

    private String id;
    private String name;
    private String version;
    private String description;
    private String[] authors;

    public PluginInfo(Class<?> aClass){
        this.aClass = aClass;
        this.className = aClass.getName();
        Plugin plugin = aClass.getAnnotation(Plugin.class);
        this.id = plugin.id();
        this.name = plugin.name();
        this.version = plugin.version();
        this.description = plugin.description();
        this.authors = plugin.authors();
    }


    public void makeObject(){
        try {
            this.object = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean compareMethod(Method method){
        return method.getDeclaringClass().getName().equals(className);
    }

    public boolean compareClass(Class aClass){
        return aClass.getName().equals(className);
    }

    @Override
    public String toString(){
        return id;
    }

    public Class<?> getObjectClass() {
        return aClass;
    }

    public Object getObject() {
        return object;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }
}
