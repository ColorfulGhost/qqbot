package cc.vimc.bot.dto;

import lombok.Data;

import java.util.List;
@Data
public class TulingReponseDTO {

    private Intent intent;
    private List<Results> results;

    @Data
    public static class Intent {

        private int code;
        private String intentName;
        private String actionName;
        private Parameters parameters;


        @Data
        public static class Parameters {
            private String nearby_place;

        }
    }
    @Data
    public static class Results {

        private int groupType;
        private String resultType;
        private Values values;

        @Data
        public static class Values {
            private String url;
            private String text;
        }
    }
}
