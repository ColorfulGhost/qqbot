package cc.vimc.bot.controller;

import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.dto.Sender;
import cc.vimc.bot.impl.BotApiImpl;
import cc.vimc.bot.impl.MinecraftImpl;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cc.vimc.bot.enums.Commands.LIST;
import static cc.vimc.bot.enums.Commands.TPS;
import static cc.vimc.bot.enums.Fields.*;


@Controller
@RequestMapping("/")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    BotApiImpl botApi;

    @Autowired
    MinecraftImpl minecraft;

    @Value("${MASTER_QQ}")
    String MASTER_QQ;

    @Value("${MC_GROUP_QQ}")
    String MC_GROUP_QQ;

    @RequestMapping("/")
    @ResponseBody
    public void pushMsg(@RequestHeader(name = "X-Self-ID", required = true) String botQQ, @RequestBody String request) {
        BotRequestDTO botRequestDTO = new BotRequestDTO();
        Sender sender = new Sender();
        try {
            botRequestDTO = JSON.parseObject(request, BotRequestDTO.class);
            sender = botRequestDTO.getSender();
        } catch (Exception e) {
            logger.error("模型转换出错！：{}", e);
        }
        if (botRequestDTO == null || sender == null) {
            return;
        }
        String messageType = botRequestDTO.getMessage_type();
        String message = botRequestDTO.getMessage();

        if (!StringUtils.isEmpty(message)) {
            switch (messageType) {
                //处理组消息
                case GROUP:
                    //处理MCQQ群里的消息
                    if (MC_GROUP_QQ.equals(botRequestDTO.getGroup_id())) {
                        //命令
                        switch (message) {
                            case LIST:
                                minecraft.postPlayerList(sender.getNickname());
                                break;
                            case TPS:
                                botApi.sendMsg(botRequestDTO, minecraft.sendCommand("tps"));
                                break;
                            default:
                                break;
                        }
                    }
                    //处理私有消息
                case PRIVATE:
                    // 这里处理主人QQ消息
                    if (MASTER_QQ.equals(sender.getUser_id())) {
                        if (message.startsWith("//")) {
                            botApi.sendMsg(botRequestDTO, minecraft.sendCommand(message.substring(2)));
                        }
                    }
            }


        }
    }

}
