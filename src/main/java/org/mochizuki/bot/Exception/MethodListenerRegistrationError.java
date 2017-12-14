package org.mochizuki.bot.Exception;

public class MethodListenerRegistrationError extends Exception{
    private String Method;
    private String Plugin_name;
    private String ErrorMassage;

    public MethodListenerRegistrationError(String Method, String Plugin_name, String ErrorMassage){
        this.ErrorMassage = ErrorMassage;
        this.Method = Method;
        this.Plugin_name = Plugin_name;
    }

    public String getErrorMassage() {
        return ErrorMassage;
    }

    public String getMethod() {
        return Method;
    }

    public String getPlugin_name() {
        return Plugin_name;
    }

    public void setErrorMassage(String errorMassage) {
        ErrorMassage = errorMassage;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public void setPlugin_name(String plugin_name) {
        Plugin_name = plugin_name;
    }
}
