package cc.vimc.bot.controller;

import cc.vimc.bot.dto.BotGroupDTO;
import cc.vimc.bot.dto.BotPrivateDTO;
import cc.vimc.bot.impl.BotApiImpl;
import cc.vimc.bot.impl.GroupEventImpl;
import cc.vimc.bot.impl.MinecraftImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cc.vimc.bot.enums.Fields.*;


@Controller
@RequestMapping("/")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    GroupEventImpl groupEvent;

    @Autowired
    BotApiImpl botApi;

    @Autowired
    MinecraftImpl minecraft;

    @Value("${List_PLAYER}")
    String List_PLAYER;

    @Value("${SLEEP}")
    String SLEEP;

    @Value("${MASTER_QQ}")
    String MASTER_QQ;

    @RequestMapping("/")
    @ResponseBody
    public void pushMsg(@RequestHeader(name = "X-Self-ID", required = true) String botQQ, @RequestBody String request) throws NoSuchFieldException, IllegalAccessException {

        JSONObject jsonObject = JSON.parseObject(request);
        String messageType = jsonObject.getString("message_type");
        String message = jsonObject.getString("message");
        String userId = jsonObject.getString("user_id");


        if (messageType.equals(GROUP)) {
            var botGroupDTO = JSON.parseObject(request, BotGroupDTO.class);
            if (!StringUtils.isEmpty(botGroupDTO.getMessage())) {
                if (botGroupDTO.getMessage().equals(List_PLAYER)) {
                    List<String> playerList = groupEvent.onlinePlayerList();
                    botApi.postPlayerList(playerList, botGroupDTO.getSender().getNickname());
                    logger.info("{}", playerList);
                }
            }
        } else if (messageType.equals(PRIVATE)) {
            var botPrivateDTO = JSON.parseObject(request, BotPrivateDTO.class);
            if (!StringUtils.isEmpty(botPrivateDTO.getMessage())) {
                if (SLEEP.equals(botPrivateDTO.getMessage())) {

                }
            }

        }
        if (MASTER_QQ.equals(userId) && message.startsWith("//")) {
            botApi.sendMsg(jsonObject.getString(messageType+"_id"),messageType,minecraft.sendCommand(message.substring(2)));
        }
    }

}
