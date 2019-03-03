package cc.vimc.bot.controller;

import cc.vimc.bot.dto.BotGroupDTO;
import cc.vimc.bot.dto.BotPrivateDTO;
import cc.vimc.bot.impl.BotApiImpl;
import cc.vimc.bot.impl.GroupEventImpl;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    GroupEventImpl groupEvent;

    @Autowired
    BotApiImpl botApi;

    @Value("${List_PLAYER}")
    String listPlayer;

    @RequestMapping("/")
    @ResponseBody
    public void pushMsg(@RequestHeader(name = "X-Self-ID", required = true) String botQQ, @RequestBody String request) {
        if (request.contains("\"message_type\":\"group\"")) {
            var botGroupDTO = JSON.parseObject(request, BotGroupDTO.class);
            if (!StringUtils.isEmpty(botGroupDTO.getMessage()) && botGroupDTO.getMessage().equals(listPlayer)) {

                List<String> playerList = groupEvent.onlinePlayerList(botGroupDTO);
                botApi.postPlayerList(playerList,botGroupDTO.getSender().getNickname());
                logger.info("{}", playerList);

            }

        } else if (request.contains("\"message_type\":\"private\"")) {
            var botPrivateDTO = JSON.parseObject(request, BotPrivateDTO.class);
        }
    }

}
