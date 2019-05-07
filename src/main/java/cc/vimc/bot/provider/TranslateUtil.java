package cc.vimc.bot.provider;

import cc.vimc.bot.dto.TranslateDTO;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class TranslateUtil {
    //https://translate.yandex.net/api/v1/tr.json/translate?
    // id=5aeeaa38.5cce90c8.f2115313-17-0&srv=tr-text&lang=zh-ja&reason=auto
    private static String TRANSLATE_API = "https://translate.yandex.net/api/v1.5/tr.json/translate";
//    private static String LANG = "zh-ja";
    private static String KEY = "trnsl.1.1.20190505T073101Z.53b562f99a187c15.44dc8bff164656082f7d70d021ea72ac4b3e2fa0\n";
    public static String ZH_JA = "zh-ja";
    public static String JA_ZH = "ja-zh";

    public static String Translate(String text,String lang) {

        var request = new HashMap<String, Object>();
        request.put("text", text);
        request.put("key", KEY);
        request.put("lang", lang);
        request.put("options", 4);

        var result = "";
        try {
            var translateRes = JSON.parseObject(HttpUtil.post(TRANSLATE_API,request), TranslateDTO.class);
            if (200 != translateRes.getCode()) {
                return null;
            }
            var optNode = translateRes.getText().stream().findFirst();
            if (translateRes.getText().stream().findFirst().isPresent()) {
                result = optNode.get();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e.getMessage();

        }
        return result;
    }

}
