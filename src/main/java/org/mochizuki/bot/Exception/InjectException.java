package org.mochizuki.bot.Exception;

public class InjectException extends Exception{
    private String Target;
    private String Plugin_name;
    private String ErrorMassage;

    public InjectException(String Target, String Plugin_name, String ErrorMassage){
        this.ErrorMassage = ErrorMassage;
        this.Target = Target;
        this.Plugin_name = Plugin_name;
    }

    public String getErrorMassage() {
        return ErrorMassage;
    }

    public String getTarget() {
        return Target;
    }

    public String getPlugin_name() {
        return Plugin_name;
    }

    public void setErrorMassage(String errorMassage) {
        ErrorMassage = errorMassage;
    }

    public void setTarget(String Target) {
        this.Target = Target;
    }

    public void setPlugin_name(String plugin_name) {
        Plugin_name = plugin_name;
    }
}
