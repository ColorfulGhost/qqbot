package cc.vimc.bot.controller;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.TulingRequestDTO;
import cc.vimc.bot.impl.BotEventImpl;

import cc.vimc.bot.util.Sha256;
import com.alibaba.fastjson.JSON;
import com.xxl.conf.core.annotation.XxlConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static cc.vimc.bot.enums.Fields.GROUP;
import static cc.vimc.bot.enums.Fields.PRIVATE;


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
        if (!Sha256.sha256(token + botQQ).equals(authorization)) {
            return;
        }
        //处理所有事件
        try {
            var botRequestDTO = new BotRequestDTO();
            botRequestDTO = JSON.parseObject(request, BotRequestDTO.class);
            //数据组装
            if (!StringUtils.isEmpty(botRequestDTO.getMessage())) {
                if (botEvent.anyEvent(botRequestDTO)) {
                    return;
                }
                switch (botRequestDTO.getMessage_type()) {
                    //处理组消息
                    case GROUP:
                        botEvent.groupEvent(botRequestDTO);
                        break;
                    //处理私有消息
                    case PRIVATE:
                        botEvent.privateEvent(botRequestDTO);
                        break;
                    default:
                        //这里是处理所有消息的地方  如果存在条件则会在这里返回
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
