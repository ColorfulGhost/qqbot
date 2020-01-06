package cc.vimc.bot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class GroupMemberDTO implements Serializable {
    String group_id;
    String user_id;
    String nickname;
    String card;
    String sex;
    Integer age;
    String area;
    Date join_time;
    Date last_sent_time;
    String level;
    String role;
    Boolean unfriendly;
    String title;
    Date title_expire_time;
    Boolean card_changeable;


}
