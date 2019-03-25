package cc.vimc.bot.dto;

import java.io.Serializable;
import java.util.Date;

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

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getJoin_time() {
        return join_time;
    }

    public void setJoin_time(Date join_time) {
        this.join_time = join_time;
    }

    public Date getLast_sent_time() {
        return last_sent_time;
    }

    public void setLast_sent_time(Date last_sent_time) {
        this.last_sent_time = last_sent_time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getUnfriendly() {
        return unfriendly;
    }

    public void setUnfriendly(Boolean unfriendly) {
        this.unfriendly = unfriendly;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTitle_expire_time() {
        return title_expire_time;
    }

    public void setTitle_expire_time(Date title_expire_time) {
        this.title_expire_time = title_expire_time;
    }

    public Boolean getCard_changeable() {
        return card_changeable;
    }

    public void setCard_changeable(Boolean card_changeable) {
        this.card_changeable = card_changeable;
    }


}
