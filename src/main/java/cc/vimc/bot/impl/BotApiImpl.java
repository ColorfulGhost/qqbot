package cc.vimc.bot.impl;


import cc.vimc.bot.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static cc.vimc.bot.enums.Apis.SEND_GROUP_MSG;
import static cc.vimc.bot.enums.Fields.GROUP_ID;
import static cc.vimc.bot.enums.Fields.MESSAGE;


@Service
public class BotApiImpl {

    @Value("${QQBOT_URL}")
    String QQBOT_URL;

    @Value("${MC_GROUP_QQ}")
    String MC_GROUP_QQ;

    public void postPlayerList(List<String> players,String sendNickName) {

        var wall = "Hi~"+sendNickName+"\n当前在线玩家："+players.size()+"人\n";
        var message = players.stream().reduce((s1, s2) -> s1 + "\n" + s2 ).get();
        var request = new HashMap();
        request.put(GROUP_ID, MC_GROUP_QQ);
        request.put(MESSAGE, wall+message);

        HttpUtils.httpPost(QQBOT_URL + SEND_GROUP_MSG, request, new HashMap<>());

    }
}
