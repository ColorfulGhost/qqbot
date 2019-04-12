package cc.vimc.bot.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Commands {


    public static final String LIST = "/list";
    public static final String NICE_DAY = "/nice_day";
    public static final String TPS = "/tps";
    public static final String FUCK= "/fuck";
    public static final String HELP= "/help";
    public static final String LIKE= "/like";
    public static final String POINTS_LEAD= "/points lead";
    public static final String LIGHT= "/light";



    public static final Map<String,String> ALL_COMMANDS = new LinkedHashMap<>(){{
        put("","Minecraft QQ群：320510534");
        put(HELP,"使用help来查看なの酱的使用手册");
        put(LIST,"查询在线玩家列表");
        put(TPS,"查询游戏内TPS状态");
        put(POINTS_LEAD,"查询游戏内点券排行榜");
        put(NICE_DAY,"なの酱会每天向你致安~(\"/nice day 0(关闭) 1(开启)\")");
        put(FUCK,"花Q！");
        put(LIGHT,"老王公寓灯开关（懒的下床关灯 开发中");
        put("其他默认开启功能","全员点赞、复读");
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
