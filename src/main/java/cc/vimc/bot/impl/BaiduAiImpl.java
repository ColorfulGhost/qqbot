package cc.vimc.bot.impl;

import cc.vimc.bot.dto.BaiduAGDTO;
import cc.vimc.bot.util.HttpUtils;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;

@Service
@Slf4j
public class BaiduAiImpl {
    @Value("${baidu.apiKey}")
    String API_KEY;
    @Value("${baidu.secretKey}")
    String SECRET_KEY;

    public String getToken() {
        var url = "https://aip.baidubce.com/oauth/2.0/token";

        var request = new HashMap<String, Object>();
        request.put("grant_type", "client_credentials");
        request.put("client_id", API_KEY);
        request.put("client_secret", SECRET_KEY);

        return JSON.parseObject(HttpUtil.get(url, request)).getString("access_token");
    }

    public BaiduAGDTO advancedGeneral(String imageURL) {
        var url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general?access_token=" + getToken();
        var request = new HashMap<String, Object>();
        try {
            var imgurl = new URL(imageURL);
            var imageBytes=new BufferedInputStream(imgurl.openConnection().getInputStream()).readAllBytes();
            var base64 = Base64Utils.encodeToString(imageBytes);
            request.put("image", base64);
            request.put("access_token", getToken());
            return JSON.parseObject(HttpUtil.post(url, request), BaiduAGDTO.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }
}
