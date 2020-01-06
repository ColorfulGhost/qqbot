package cc.vimc.bot.dto;

import lombok.Data;
@Data
public class BaiduAGDTO {


    private double score;
    private String root;
    private BaikeInfo baike_info;
    private String keyword;

    @Data
    public static class BaikeInfo {


        private String baike_url;
        private String image_url;
        private String description;

    }
}
