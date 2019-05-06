package cc.vimc.bot.dto;

import java.util.List;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Detected getDetected() {
        return detected;
    }

    public void setDetected(Detected detected) {
        this.detected = detected;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public static class Detected {
        /**
         * lang : zh
         */

        private String lang;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }
}

