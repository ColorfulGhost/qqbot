package cc.vimc.bot.dto;

import lombok.Data;

import java.util.List;
@Data
public class BaiduCensorDTO {
    private ResultX result;
    @Data
    public static class ResultX {
        private Antiporn antiporn;
        @Data
        public static class Antiporn {

            private String conclusion;
            private long log_id;
            private int result_num;
            private String confidence_coefficient;
            private List<Result> result;
            private List<ResultFine> result_fine;

            @Data
            public static class Result {
                private double probability;
                private String class_name;

            }
            @Data
            public static class ResultFine {

                private int probability;
                private String class_name;
            }
        }
    }
}
