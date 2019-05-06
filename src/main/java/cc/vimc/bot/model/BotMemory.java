package cc.vimc.bot.model;

import cc.vimc.bot.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class BotMemory {
    /**
     * 群 QQ 号码
     */
    private String id;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 0关闭 1 开启 早晚问候
     */
    private Integer niceDay;

    /**
     * 开启日语翻译
     */
    private Integer translateJp;

    public BotMemory(String id, String type, Integer niceDay, Integer translateJp) {
        this.id = id;
        this.type = type;
        this.niceDay = niceDay;
        this.translateJp = translateJp;
    }

    public String getAllFieldAndData(Boolean isWhere, String... needFields) {
        var result = "";
        var sb = new StringBuilder();
        var declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (!Arrays.asList(needFields).contains(field.getName())) {
                continue;
            }
            try {
                var fieldData = field.get(this);
                if (fieldData == null) {
                    continue;
                }

                sb.append(StringUtils.underline(new StringBuffer(field.getName()))).append("='").append(fieldData).append("'");
                if (isWhere) {
                    sb.append(" AND ");
                } else {
                    sb.append(",");
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();
        result = result.substring(0, isWhere ? result.length() - 5 : result.length() - 1);
        return result;

    }

    public Integer getTranslateJp() {
        return translateJp;
    }

    public void setTranslateJp(Integer translateJp) {
        this.translateJp = translateJp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNiceDay() {
        return niceDay;
    }

    public void setNiceDay(Integer niceDay) {
        this.niceDay = niceDay;
    }
}
