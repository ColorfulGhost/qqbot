package cc.vimc.bot.impl;


import cc.vimc.bot.dto.BotRequestDTO;
import cc.vimc.bot.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static cc.vimc.bot.enums.Apis.SEND_MSG;
import static cc.vimc.bot.enums.Fields.*;


@Service
public class BotApiImpl {

    @Value("${QQBOT_URL}")
    String qqbotUrl;

    public void sendMsg(BotRequestDTO botRequestDTO, String message) {
        String messageType = botRequestDTO.getMessage_type();
        var request = new HashMap();
        request.put(MESSAGE_TYPE, botRequestDTO.getMessage_type());
        request.put(messageType.equals("private") ? "user_id" : messageType + "_id",
                botRequestDTO.getGroup_id() == null ? botRequestDTO.getUser_id() : botRequestDTO.getGroup_id());
        request.put(MESSAGE, message);
        HttpUtils.httpPost(qqbotUrl + SEND_MSG, request, Collections.emptyMap());
    }

    public void sendMsgGrouup(String groupId, String message) {
        var request = new HashMap();
        request.put(MESSAGE_TYPE, GROUP);
        request.put(GROUP_ID, groupId);
        request.put(MESSAGE, message);
        HttpUtils.httpPost(qqbotUrl + SEND_MSG, request, Collections.emptyMap());
    }

}
