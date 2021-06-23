# ShuviBot
Event-driven lightweight chatbot framework that supports dependency injection and dynamically loading plugin jars at runtime.

## General Information
- Project status: **Archived**, because the technology used is outdated
- Last update: Feb 26, 2018 - [Bug fix and add ststic ClassPath](https://github.com/YukinaMochizuki/ShuviBot/commit/f67b326268f9dc61c3ead212cfdda0179eb41854)
- Last commit: Jun 23, 2021 Update README and Add example

## Table of Contents


## Requirements and Dependencies
- JDK 1.8 (higher version may work but not tested, project development environment using AdoptOpenJDK 8.0.292)
- Build system: Gradle >= 3.5-rc-2 (project development environment using 3.5-rc-2 but higher versions should also work)
- Dependency: [google/guava 23.5](https://github.com/google/guava) - Google core libraries for Java
- Dependency: [rubenlagus/TelegramBots 3.4](https://github.com/rubenlagus/TelegramBots) - Java library to create bots using Telegram Bots API
- Dependencies in 'lib' folder: [SpongePowered/Configurate 3.3](https://github.com/SpongePowered/Configurate) - Used to read configuration, serialize and deserialize object to hocon file

## Key Features
### Lightweight
Only has few dependencies, dependency injection and event listeners are implemented by the framework itself.

### Flexible
The framework will automatically scan the plugin folder, find and load the jar file.

### Other
- Low-invasive: Dependency injection and event listeners based on annotation
- Non-database: No need to maintain a database to store data, the framework provides a default path for each plugin to store serialized objects

## Build
On Linux
```bash
git clon https://github.com/YukinaMochizuki/ShuviBot.git
cd ShuviBot
./gradlew jar
```

## Usage and example
After the first run, a default config file will be generated

```bash
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





