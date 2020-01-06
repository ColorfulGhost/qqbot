package cc.vimc.bot.dto;

import cc.vimc.bot.model.Sender;
import lombok.Data;

@Data
public class BotRequestDTO {



    private int font;
    private String message;
    private int message_id;
    private String message_type;
    private String post_type;
    private String raw_message;
    private String self_id;
    private String sub_type;
    private int time;
    private String user_id;
    private Sender sender;
    private Object anonymous;
    private String group_id;




}
