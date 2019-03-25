package cc.vimc.bot.dto;

public class BotMemoryDTO {
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
    private int niceDay;

    public BotMemoryDTO(String id,String type,int niceDay){
        this.id=id;
        this.type=type;
        this.niceDay = niceDay;
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

    public int getNiceDay() {
        return niceDay;
    }

    public void setNiceDay(int niceDay) {
        this.niceDay = niceDay;
    }
}
