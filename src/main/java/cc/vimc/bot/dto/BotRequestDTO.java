package cc.vimc.bot.dto;
public class BotRequestDTO {

    /**
     * font : 8014448
     * message : .
     * message_id : 1386
     * message_type : private
     * post_type : message
     * raw_message : .
     * self_id : 1277841527
     * sender : {"age":0,"nickname":"Ghost","sex":"unknown","user_id":815666528}
     * sub_type : friend
     * time : 1552021149
     * user_id : 815666528
     */

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
    private  Sender sender;
    private Object anonymous;
    private String group_id;


    public Object getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Object anonymous) {
        this.anonymous = anonymous;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }


    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }

    public String getSelf_id() {
        return self_id;
    }

    public void setSelf_id(String self_id) {
        this.self_id = self_id;
    }


    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
