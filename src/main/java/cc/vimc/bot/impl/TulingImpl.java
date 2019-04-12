package cc.vimc.bot.impl;

import cc.vimc.bot.dto.TulingRequestDTO;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TulingImpl {

    private static final String  TULING_URL = "http://openapi.tuling123.com/openapi/api/v2";
    public String chat(TulingRequestDTO tulingRequestDTO){
        Map request =JSON.parseObject(JSONUtil.toJsonStr(tulingRequestDTO),Map.class);
        return HttpUtil.post(TULING_URL,request);
    }
}
