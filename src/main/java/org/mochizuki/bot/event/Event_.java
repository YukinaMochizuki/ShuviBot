package org.mochizuki.bot.event;

import org.mochizuki.bot.service.Plugin.PluginInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class Event_ {
    private boolean isStaticEvent = false;
    private String eventName;
    private Object[] argObjs1 = {this};
    private ArrayList<Method> methodArrayList = new ArrayList<>();

    public void addMethodArrayList(Method method){
        if(isStaticEvent)methodArrayList.add(method);
    }

//    public void callMethod(ArrayList<PluginInfo> pluginInfoArrayList) {
//        if(isStaticEvent) {
//            try {
//                for (Method method : methodArrayList){
//                    for(PluginInfo pluginInfo : pluginInfoArrayList){
//                        if(method.getDeclaringClass().getName().equals(pluginInfo.getaClass().getName())){
//                            method.invoke(pluginInfo.getObject(),argObjs1);
//                            break;
//                        }
//                    }
//                }
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void setArgObjs1(Object[] argObjs1) {
        if(isStaticEvent)this.argObjs1 = argObjs1;
    }

    public ArrayList<Method> getMethodArrayList() {
        return methodArrayList;
    }

    public Event_ setEventName(String eventName) {
        if(isStaticEvent)this.eventName = eventName;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    protected void setIsStaticEvent(){
        isStaticEvent = true;
    }
}