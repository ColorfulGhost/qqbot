package cc.vimc.bot.provider;

import cc.vimc.bot.dto.BaiduAGDTO;
import cc.vimc.bot.dto.BaiduCensorDTO;
import cc.vimc.bot.util.HttpUtils;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.imageclassify.AipImageClassify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static cc.vimc.bot.util.Base64Utils.urlToBase64;
@Slf4j
public class ImageRec {

    //设置APPID/AK/SK
    public static final String APP_ID = "16008541";
    public static final String API_KEY = "XGlOXFjzhrMe82v6QdPDeB7h";
    public static final String SECRET_KEY = "panhsnjBQGIoKZLtjWlnFvbtMQ7uessF";

    public static List<BaiduAGDTO> advancedGeneral(String imageURL) {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        var res = client.advancedGeneral(urlToBase64(imageURL), new HashMap<>());
        return JSON.parseArray(res.get("result").toString(), BaiduAGDTO.class);
    }

    public String getToken(String apiKey, String secretKey) {
        var url = "https://aip.baidubce.com/oauth/2.0/token";
        var request = new HashMap<String, Object>();
        request.put("grant_type", "client_credentials");
        request.put("client_id", API_KEY);
        request.put("client_secret", SECRET_KEY);
        return JSON.parseObject(HttpUtil.get(url, request)).getString("access_token");
    }
}
