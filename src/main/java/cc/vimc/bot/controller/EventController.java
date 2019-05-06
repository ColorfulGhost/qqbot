package cc.vimc.bot.controller;


import cc.vimc.bot.impl.BotEventImpl;

import cc.vimc.bot.util.Sha256;
import com.xxl.conf.core.annotation.XxlConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    BotEventImpl botEvent;

    @Value("${token}")
    String token;

    @XxlConf("")
    @Value("${authorization}")
    String authorization;

    @RequestMapping("/botEvent")
    @ResponseBody
    public void pushMsg(@RequestHeader(name = "X-Self-ID", required = true) String botQQ, @RequestBody String request) {
        //验证是否是我的BOT
        if (!Sha256.sha256(token+botQQ).equals(authorization)) {
            return;
        }
        //处理所有事件
        try {
            botEvent.botEventHandle(request);
        } catch (Exception e) {
            logger.error("机器人处理事件失败："+e.getMessage(),e);
        }
    }
}
