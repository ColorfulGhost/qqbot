package cc.vimc.bot.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EnumConstants implements Serializable {
    private static final long serialVersionUID = -170659620579917298L;

    public static final int SUCCESS = 200;
    public static final int SERVICEEXCEPTIONS = 500;

    public static final Map<Integer,String> CONSTANTS = new HashMap(){{
        put(SUCCESS,"请求成功");
        put(SERVICEEXCEPTIONS,"服务异常");
    }};
}
