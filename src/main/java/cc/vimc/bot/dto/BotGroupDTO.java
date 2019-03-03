package cc.vimc.bot.dto;

public class BotGroupDTO {

    /**
     * anonymous : null
     * font : 71276472
     * group_id : 320510534
     * message : t
     * message_id : 95
     * message_type : group
     * post_type : message
     * raw_message : t
     * self_id : 1277841527
     * sender : {"age":0,"area":"","card":"","level":"传说","nickname":"Ghost","role":"owner","sex":"unknown","title":"Player","user_id":815666528}
     * sub_type : normal
     * time : 1551587539
     * user_id : 815666528
     */

    private Object anonymous;
    private int font;
    private int group_id;
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

    public Object getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Object anonymous) {
        this.anonymous = anonymous;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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
         * area :
         * card :
         * level : 传说
         * nickname : Ghost
         * role : owner
         * sex : unknown
         * title : Player
         * user_id : 815666528
         */

        private int age;
        private String area;
        private String card;
        private String level;
        private String nickname;
        private String role;
        private String sex;
        private String title;
        private int user_id;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
