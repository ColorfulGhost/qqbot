package cc.vimc.bot.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands {


    public static final String LIST = "/list";
    public static final String TPS = "/tps";
    public static final String FUCK= "/fuck";



    public static final Map<String,String> ALL_COMMANDS = new HashMap(){{
        put("list","/list");
        put("tps","/tps");
        put("fuck","/fuck");
    }};

    public static String getCommand(String name) {
        for (Map.Entry<String, String> command : ALL_COMMANDS.entrySet()) {
            if (command.getValue().contains(name)) {
                return command.getValue();
            }
        }
        return "";
    }
}
