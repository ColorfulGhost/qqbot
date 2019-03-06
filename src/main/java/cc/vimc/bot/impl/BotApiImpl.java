package cc.vimc.bot.impl;


import cc.vimc.bot.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static cc.vimc.bot.enums.Apis.SEND_GROUP_MSG;
import static cc.vimc.bot.enums.Apis.SEND_MSG;
import static cc.vimc.bot.enums.Fields.*;


@Service
public class BotApiImpl {

    @Value("${QQBOT_URL}")
    String QQBOT_URL;

    @Value("${MC_GROUP_QQ}")
    String MC_GROUP_QQ;

    public void postPlayerList(List<String> players,String sendNickName) {

        int playerSize = players.size();
        var request = new HashMap();
        request.put(GROUP_ID, MC_GROUP_QQ);

        if (CollectionUtils.isEmpty(players)){
            var message = "Hi~"+sendNickName+"，当前没有在线玩家。";
            request.put(MESSAGE,message);
            HttpUtils.httpPost(QQBOT_URL + SEND_GROUP_MSG, request, Collections.EMPTY_MAP);
        }else {
            var wall = "Hi~"+sendNickName+"\n当前在线玩家："+playerSize+"人\n";
            var message = players.stream().reduce((s1, s2) -> s1 + "\n" + s2 ).get();
            request.put(MESSAGE, wall+message);

            HttpUtils.httpPost(QQBOT_URL + SEND_GROUP_MSG, request, Collections.EMPTY_MAP);
        }
    }

    public void sendMsg(String id,String messageType,String message){
        var request = new HashMap();
        request.put(MESSAGE_TYPE,messageType);

        if (messageType.equals(PRIVATE)){
            request.put(USER_ID,id);
        }else if (messageType.equals(GROUP)){
            request.put(GROUP_ID,id);
        }else if (messageType.equals(DISCUSS)){
            request.put(DISCUSS_ID,id);
        }
        request.put(MESSAGE,message);
        HttpUtils.httpPost(QQBOT_URL + SEND_MSG, request, Collections.EMPTY_MAP);

        request.put(GROUP_ID, MC_GROUP_QQ);
    }

}
