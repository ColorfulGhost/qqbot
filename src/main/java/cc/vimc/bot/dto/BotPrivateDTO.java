package cc.vimc.bot.dto;

public class BotPrivateDTO {

    /**
     * font : 55054360
     * message : 晚安
     * message_id : 91
     * message_type : private
     * post_type : message
     * raw_message : 晚安
     * self_id : 1277841527
     * sender : {"age":0,"nickname":"Ghost","sex":"unknown","user_id":815666528}
     * sub_type : friend
     * time : 1551543797
     * user_id : 815666528
     */

    private int font;
    private String message;
    private int message_id;
    private String message_type;
    private String post_type;
    private String raw_message;
    private int self_id;
    private SenderBean sender;
    private String sub_type;
    private int time;
    private int user_id;

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

    public int getSelf_id() {
        return self_id;
    }

    public void setSelf_id(int self_id) {
        this.self_id = self_id;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static class SenderBean {
        /**
         * age : 0
         * nickname : Ghost
         * sex : unknown
         * user_id : 815666528
         */

        private int age;
        private String nickname;
        private String sex;
        private int user_id;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
