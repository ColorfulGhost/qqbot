package cc.vimc.bot.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Commands {


    public static final String LIST = "/list";
    public static final String NICE_DAY = "/nice_day";
    public static final String TPS = "/tps";
    public static final String FUCK = "/fuck";
    public static final String HELP = "/help";
    public static final String LIKE = "/like";
    public static final String POINTS_LEAD = "/points lead";
    public static final String LIGHT = "/light";
    public static final String QR = "/qr";
    public static final String KG = "/kg";
    public static final String JP = "/jp";

    //不在这个项目内的命令 其他服务或插件
    public static final String CAR = "!car";
    public static final String ANIME = "!anime";


    public static final Map<String, String> ALL_COMMANDS = new LinkedHashMap<>() {{
        put(HELP, "使用help来查看nano食用方法吧！");
        put(LIST, "查询在线玩家列表（Minecraft群专用）");
        put(TPS, "查询游戏内TPS状态（Minecraft群专用）");
        put(POINTS_LEAD, "查询游戏内点券排行榜（Minecraft群专用）");
        put(NICE_DAY, "なの酱会每天向你致安~(\"/nice_day 0(关闭) 1(开启)\")");
        put(NICE_DAY, "なの酱会每天向你致安~(\"/nice_day 0(关闭) 1(开启)\")");
        put(JP, "日语模式 食用:/jp 0(关闭) 1(开启)");
        put(QR, "生成二维码 食用：/qr [图片]");
        put(KG, "人工智障识图 食用：/kg [图片]");
//        put(CAR, "让祷告休息休息，让我来吧。");
//        put(ANIME, "以图搜番，例：@nano [图片]");
        put(FUCK, "花Q！");
        put("Minecraft QQ群", "320510534");
        put("Github", "https://github.com/ColorfulGhost/qqbot");
//        put("其他默认开启功能","全员点赞、复读");
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
