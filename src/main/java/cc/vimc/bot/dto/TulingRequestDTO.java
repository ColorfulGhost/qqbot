package cc.vimc.bot.dto;

import lombok.Data;

@Data
public class TulingRequestDTO  {
    private int reqType;
    private Perception perception;
    private UserInfo userInfo;
    @Data
    public static class Perception {

        private InputText inputText;
        private InputURL inputImage;
        private InputURL inputMedia;
        private SelfInfo selfInfo;
        @Data
        public static class InputText {
            private String text;
        }
        @Data
        public static class InputURL {
            private String url;
        }
        @Data
        public static class SelfInfo {
            private Location location;
            @Data
            public static class Location {

                private String city;
                private String province;
                private String street;
            }
        }
    }
    @Data
    public static class UserInfo {
        private String apiKey;
        private String userId;
        private String groupId;
        private String userIdName;

    }
}
