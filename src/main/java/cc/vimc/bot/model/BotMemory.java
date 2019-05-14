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
        var sb = new StringBuilder();
        //对所有字段进行循环
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                //获取这个字段的值
                var fieldData = field.get(this);
                //只保留我需要的字段数据   如果没有值放弃这个数据的查询
                if (!Arrays.asList(needFields).contains(field.getName()) || fieldData == null) {
                    continue;
                }
                //添加这个字段的数据到StringBuilder
                sb.append(StringUtils.underline(new StringBuffer(field.getName()))).append("='").append(fieldData).append("'").append(isWhere ? " AND " : ",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.substring(0, isWhere ? sb.length() - 5 : sb.length() - 1);

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
