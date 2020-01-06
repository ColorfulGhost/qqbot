package cc.vimc.bot.dto;

import lombok.Data;

import java.util.List;
@Data
public class TranslateDTO {

    /**
     * code : 200
     * detected : {"lang":"zh"}
     * lang : zh-ja
     * text : ["天気が良い"]
     */

    private int code;
    private Detected detected;
    private String lang;
    private List<String> text;

    @Data
    public static class Detected {


        private String lang;


    }
}

