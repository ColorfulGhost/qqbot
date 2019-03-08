package cc.vimc.bot.impl;


import cc.vimc.bot.mapper.MinecraftMapper;
import cc.vimc.bot.rcon.RconClient;
import cc.vimc.bot.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static cc.vimc.bot.enums.Apis.SEND_GROUP_MSG;
import static cc.vimc.bot.enums.Apis.SEND_MSG;
import static cc.vimc.bot.enums.Fields.GROUP_ID;
import static cc.vimc.bot.enums.Fields.MESSAGE;

@Service
public class MinecraftImpl {

    private static final Logger logger = LoggerFactory.getLogger(MinecraftImpl.class);
    @Value("${RCON.PASSWORD}")
    String RCONPASSWORD;

    @Value("${MCSERVER.HOST}")
    String MCSERVERHOST;

    @Value("${RCON.PORT}")
    Integer MCRCONPORT;


    @Value("${QQBOT_URL}")
    String QQBOT_URL;

    @Value("${MC_GROUP_QQ}")
    String MC_GROUP_QQ;

    @Autowired
    BotApiImpl botApi;

    @Autowired
    private MinecraftMapper minecraftMapper;

    public List<String> onlinePlayerList() {
        return minecraftMapper.onlinePlayerList();
    }

    public void postPlayerList(List<String> players, String sendNickName) {

        int playerSize = players.size();
        var request = new HashMap();
        request.put(GROUP_ID, MC_GROUP_QQ);

        if (CollectionUtils.isEmpty(players)) {
            var message = "Hi~" + sendNickName + "，当前没有在线玩家。";
            request.put(MESSAGE, message);
            HttpUtils.httpPost(QQBOT_URL + SEND_GROUP_MSG, request, Collections.emptyMap());
        } else {
            var wall = "Hi~" + sendNickName + "\n当前在线玩家：" + playerSize + "人\n";
            Optional<String> playerListOptionals = players.stream().reduce((s1, s2) -> s1 + "\n" + s2);
            String message = "";
            if (playerListOptionals.isPresent()) {
                message = playerListOptionals.get();
            }
            request.put(MESSAGE, wall + message);
            HttpUtils.httpPost(QQBOT_URL + SEND_GROUP_MSG, request, Collections.emptyMap());
        }
    }

    public String sendCommand(String command) {
        HashMap<String, String> request = new HashMap<>();
        request.put(GROUP_ID, MC_GROUP_QQ);
        try (RconClient client = RconClient.open(MCSERVERHOST, MCRCONPORT, RCONPASSWORD)) {
            return client.sendCommand(command).replaceAll("§.*?[0-z]", "");
        } catch (Exception e) {
            var message = "链接服务器RCON失败，请联系欧尼酱。";
            request.put(MESSAGE, message);
            HttpUtils.httpPost(QQBOT_URL + SEND_MSG, request, Collections.emptyMap());
            logger.error("服务器RCON协议出现错误：{}", e);
            return null;
        }
    }
}
