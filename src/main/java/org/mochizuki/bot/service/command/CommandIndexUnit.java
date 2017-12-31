package org.mochizuki.bot.service.command;

import org.mochizuki.bot.Exception.InvokeParameterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CommandIndexUnit {
    private String name;
    private String description;
    private Boolean hasParameter;
    private Class[] parameters = new Class[0];

    private Object object;
    private Method method;

    public CommandIndexUnit(Method method,Object object){
        this.method = method;
        this.object = object;
        this.name = method.getName();

        if(method.getParameterTypes().length == 0){
            this.hasParameter = false;
        }else {
            this.hasParameter = true;
            parameters = method.getParameterTypes();
        }
    }

    public void invokeMethod(Object ... commandObjects) throws InvokeParameterException,NullPointerException {
        if(commandObjects.length != method.getParameterTypes().length){
            throw new InvokeParameterException(method,"The parameter number is inconsistent")
                    .setGiveParameterNumber(commandObjects.length);
        }

        if(this.object == null)throw new NullPointerException();

        try {
            method.invoke(object,commandObjects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasParameter() {
        return hasParameter;
    }

    public Class[] getParameters() {
        return parameters;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        if(this.description == null){
            return "No description";
        }
        return description;
    }
}
