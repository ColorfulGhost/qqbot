package cc.vimc.bot.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EnumConstants implements Serializable {

    public static final int SUCCESS = 200;
    public static final int SERVICEEXCEPTIONS = 500;
    public static final int GETTOKENFAIL = 501;

    public static final Map<Integer,String> CONSTANTS = new HashMap(){{
        put(SUCCESS,"请求成功");
        put(GETTOKENFAIL,"获取Token失败");
        put(SERVICEEXCEPTIONS,"服务异常");
    }};
}
