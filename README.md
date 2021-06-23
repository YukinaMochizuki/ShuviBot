# ShuviBot
Event-driven lightweight chatbot framework that supports dependency injection and dynamically loading plugin jars at runtime.

## General Information
- Project status: **Archived**, because the technology used is outdated
- Last update: Feb 26, 2018 - [Bug fix and Add static ClassPath](https://github.com/YukinaMochizuki/ShuviBot/commit/f67b326268f9dc61c3ead212cfdda0179eb41854)
- Last commit: Jun 23, 2021 Update README and Add example

## Table of Contents
- [Requirements and Dependencies](#requirements-and-dependencies)
- [Key Features](#key-features)
- [Build](#build)
- [Usage](#usage)
- [Example plugin](#example-plugin)
- [Serialize and deserialize](#serialize-and-deserialize)

## Requirements and Dependencies
- JDK 1.8 (higher version may work but not tested)
- Build system: Gradle >= 3.5-rc-2 (project development environment using 3.5-rc-2 but higher versions should also work)
- Dependency: [google/guava 23.5](https://github.com/google/guava) - Google core libraries for Java (use for list all top level classes)
- Dependency: [rubenlagus/TelegramBots 3.4](https://github.com/rubenlagus/TelegramBots) - Java library to create bots using Telegram Bots API
- Dependencies in 'lib' folder: [SpongePowered/Configurate 3.3](https://github.com/SpongePowered/Configurate) - Used to read configuration, serialize and deserialize object to hocon file

## Key Features
### Lightweight
Only has few dependencies, dependency injection and event listeners are implemented by the framework itself.

### Flexible
The framework will automatically scan the plugin folder, find and load the jar file.

### Other
- Low-invasive: Dependency injection and event listeners are based on annotation
- Database is not required: No need to maintain a database to store data, the framework provides a default path for each plugin to store serialized objects

## Build
On Linux
```bash
git clon https://github.com/YukinaMochizuki/ShuviBot.git
cd ShuviBot
./gradlew jar
```

## Usage
After the first run, a default config file will be generated

```Shell
user@linux:~/path/ShuviBot$ mv build/libs/mochizuki_bot-1.0-SNAPSHOT.jar /your/other/path
user@linux:~/path/ShuviBot$ cd /your/other/path
user@linux:/your/other/path$ java -jar mochizuki_bot-1.0-SNAPSHOT.jar

Loading library...
System build 0.80226
[11:27:15][Main thread/INFO]:Starting L2 cache support system
[11:27:15][Main thread/INFO]:version Alpha 1.0
[11:27:15][Bot Main/INFO]:Instantiate Config Storage
[11:27:16][Bot Main/INFO]:The Configuration file Path is./config.conf
[11:27:16][Bot Main/INFO]:Default Config has be created, please setting it

user@linux:/your/other/path$ ls
config.conf  mochizuki_bot-1.0-SNAPSHOT.jar  plugin

user@linux:/your/other/path$ cat config.conf
Bot {
    Global {
        Logger-level=INFO
    }
    ServiceManager {
        BasicIO {
            type=Hocon
        }
    }
    Telegram {
        BotName=BotName
        BotToken="123456789:ABCDEFGH"
        ChatNumber=0
    }
}
```
### Setting chat_id
After setting the BotName and BotToken in config.conf, we need to continue setting ChatNumber, but don’t worry, this process is automatic.

```
user@linux:/your/other/path$ java -jar mochizuki_bot-1.0-SNAPSHOT.jar

Loading library...
System build 0.80226
[12:15:30][Main thread/INFO]:Starting L2 cache support system
[12:15:30][Main thread/INFO]:version Alpha 1.0
[12:15:30][Bot Main/INFO]:Instantiate Config Storage
[12:15:30][Bot Main/INFO]:The Configuration file Path is./config.conf
[12:15:31][Bot Main/INFO]:Set chat ID mode
[12:15:31][Bot Main/INFO]:Instantiate Developer Mode Telegram Bots API...OK
[12:15:33][Bot Main/INFO]:Exit for 1 Min later
```

When you see `Exit for 1 Min later`, please send a message to your telegram bot. The framework will automatically detect the chat_id and save it to the config file, then framework can be used normally at the next startup.

```
user@linux:/your/other/path$ java -jar mochizuki_bot-1.0-SNAPSHOT.jar

Loading library...
System build 0.80226
[12:20:31][Main thread/INFO]:Starting L2 cache support system
[12:20:31][Main thread/INFO]:version Alpha 1.0 
[12:20:31][Bot Main/INFO]:Instantiate Config Storage
[12:20:31][Bot Main/INFO]:The Configuration file Path is./config.conf
[12:20:32][Bot Main/INFO]:Set Logger levels to INFO
[12:20:32][Bot Main/INFO]:==============Debug==============
[12:20:32][Bot Main/INFO]:==============Debug==============
[12:20:32][Bot Main/INFO]:Instantiate Telegram Bots API...OK
[12:20:33][ServiceManager/INFO]:Instantiate Service Manager
[12:20:33][BasicIO/INFO]:Data Input/Output use Hocon mode
[12:20:33][BasicIO/INFO]:The Configuration file Path is./value.conf
[12:20:33][BasicIO/INFO]:Configuration file is not found, will create a new one
[12:20:33][ServiceManager/INFO]:Instantiate ConversationManager
[12:20:33][ServiceManager/INFO]:Instantiate Command Manager 
[12:20:33][ServiceManager/INFO]:Instantiate Plugin Manager
[12:20:33][Plugin Manager/INFO]: Loading plugin is complete
[12:20:35][Bot Main/INFO]:Instantiate ServiceManager completed
[12:20:35][Bot Main/INFO]:Command type ready
```
enjoy it!

### Screenshot
![](https://i.imgur.com/9FzNuHH.png)

## Example plugin
### Register plugin
After adding plugin ann, it will be automatically recognized by the framework

ProjectL2.java
```java
import org.mochizuki.bot.service.Annotation.Plugin;

@Plugin(id = "ProjectL2",
        name = "ProjectL2",
        version = "alpha1.0",
        authors = "mochizuki")
public class ProjectL2 {

}
```

### Dependency injection

```java
public class ProjectL2 {
    @Inject
    private ConversationManager conversationManager;

    @Inject
    private Logger logger;

    @Inject
    private ServiceInterface serviceInterface;

    @Inject @ConfigDir(sharedRoot = true)
    private Path path;
}
```

### Register listener
All system event are listed in this [file](https://github.com/YukinaMochizuki/ShuviBot/blob/master/src/main/java/org/mochizuki/bot/event/EventType.java)

```java
public class ProjectL2 {
... other code

    @Listener(eventType = EventType.BotPreInitializationEvent)
    public void onPreInitializationEvent(Event event){
        
    }
}
```

Then we can try to add some commands...

```java
public class ProjectL2 {
... other code

    @Listener(eventType = EventType.BotPreInitializationEvent)
    public void onPreInitializationEvent(Event event){
        
    }
    
    @Listener(eventType = EventType.BotInitializationEvent)
    public void onInitializationEvent(Event event){
        CommandManager commandManager = CommandManager.getCommandManager();
        try {
            commandManager.addCommand(this.getClass().getDeclaredMethod("ProjectL2_save"),this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    private void ProjectL2_save(){
        serviceInterface.displayMessage(logger,"save...");
    }
}
```

Now we can send `/ProjectL2_save` to the telegram bot to execute `ProjectL2_save` method

![](https://i.imgur.com/5p6fT9U.png)

### SingleMode

In addition to sending commands, the framework also provides the function of forwarding all messages to the plugin. First we create a class implements `SingleModeInterface`.

```java
import org.mochizuki.bot.service.conversation.SingleModeInterface;

public class SingleMessage implements SingleModeInterface{

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
```

Then register it when the `BotPostInitializationEvent` is triggered.

```java
public class ProjectL2 {

    @Inject
    private ServiceInterface serviceInterface;

    @Inject
    private ConversationManager conversationManager;

    @Listener(eventType = EventType.BotPostInitializationEvent)
    public void onPostInitializationEvent(Event event){
        conversationManager.singleModeRegister(this, new SingleMessage(serviceInterface));
        
        try {
            CommandManager commandManager = CommandManager.getCommandManager();
            commandManager.addCommand(this.getClass().getDeclaredMethod("ProjectL2_save"), this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void changeToSingleMode(){
        conversationManager.changeSingleMode("GoInSingleMode");
    }
}
```

Now you can send messages directly to your plugin.

![](https://i.imgur.com/4E9rR2q.png)

Full example plugin code is in ExamplePlugin folder.

## Serialize and deserialize

```java
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ProjectL2 {
    @Inject @ConfigDir(sharedRoot = true)
    private Path path;

    private Path configPath;
    private Path valuePath;
    
    private YourClass yourObject

    @Listener(eventType = EventType.BotPreInitializationEvent)
    public void onPreInitializationEvent(Event event){
        configPath = Paths.get(path.toString(),"config.conf");
        valuePath = Paths.get(path.toString(),"value.conf");
            
        try {
            if (!Files.exists(path)) Files.createDirectory(path);
            if(!Files.exists(valuePath)) Files.createFile(valuePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.valueLoader = HoconConfigurationLoader.builder().setPath(valuePath).build();

        try {
            this.configRootNode = configLoader.load();
            this.valueRootNode = valueLoader.load();
            
            yourObject = valueRootNode.getNode("ProjectL2","YourClass")
                .getValue(TypeToken.of(YourClass.class)); //return null if not exist
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e){
            e.printStackTrace();
        }
    }
    
    @Listener(eventType = EventType.BotStoppingServerEvent)
    public void onBotStopping(Event event){
        try {
            valueRootNode.getNode("ProjectL2","YourClass").
                    setValue(TypeToken.of(YourClass.class), yourObject);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        
        try {
            valueLoader.save(valueRootNode);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
```

