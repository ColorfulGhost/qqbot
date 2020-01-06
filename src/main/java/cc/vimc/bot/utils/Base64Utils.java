package cc.vimc.bot.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.net.URL;

@Slf4j
public class Base64Utils {
    public static String urlToBase64ToString(String imageURL) {
        return org.springframework.util.Base64Utils.encodeToString(urlToBase64(imageURL));
    }

    public static byte[] urlToBase64(String imageURL) {
        try {
            var imgurl = new URL(imageURL);
            return new BufferedInputStream(imgurl.openConnection().getInputStream()).readAllBytes();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
