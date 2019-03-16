package cc.vimc.bot.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands {


    public static final String LIST = "/list";
    public static final String TPS = "/tps";
    public static final String FUCK= "/fuck";
    public static final String HELP= "/help";
    public static final String POINTS_LEAD= "/points lead";



    public static final Map<String,String> ALL_COMMANDS = new HashMap(){{
        put(LIST,"查询所有在线玩家");
        put(TPS,"查询游戏内TPS状态");
        put(POINTS_LEAD,"查询游戏内点券排行榜");
        put(FUCK,"花Q！");
        put(HELP,"查看所有可用命令");
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
