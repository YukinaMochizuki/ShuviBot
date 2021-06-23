package TestPlugin;

import org.mochizuki.bot.service.ServiceInterface;
import org.mochizuki.bot.service.conversation.SingleModeInterface;

public class SingleMessage implements SingleModeInterface {

    private ServiceInterface serviceInterface;

    public SingleMessage(ServiceInterface serviceInterface){
        this.serviceInterface = serviceInterface;
    }

    @Override
    public void massageInput(String s) {
        serviceInterface.displayMessage(null, s);
    }

    @Override
    public String getID() {
        return "GoInSingleMode";
    }
}
