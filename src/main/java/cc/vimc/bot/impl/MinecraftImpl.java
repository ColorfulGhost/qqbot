package cc.vimc.bot.impl;

import cc.vimc.bot.rtti.EditField;
import cc.vimc.bot.util.HttpUtils;
import com.github.t9t.minecraftrconclient.RconClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;

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


    public String sendCommand(String command) throws NoSuchFieldException, IllegalAccessException {
        HashMap<String, String> request = new HashMap<>();
        request.put(GROUP_ID, MC_GROUP_QQ);

         EditField.editField(StandardCharsets.UTF_8,"PAYLOAD_CHARSET",RconClient.class);
         EditField.editField(StandardCharsets.UTF_8,"US_ASCII",StandardCharsets.class);
        try (RconClient client = RconClient.open(MCSERVERHOST, MCRCONPORT, RCONPASSWORD)) {
            String backMessage = client.sendCommand(String.format(command, StandardCharsets.US_ASCII));
            return new String(backMessage.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8);
        } catch (Exception e) {
            var message = "链接服务器RCON失败，请联系欧尼酱。";
            request.put(MESSAGE, message);
            HttpUtils.httpPost(QQBOT_URL + SEND_MSG, request, Collections.EMPTY_MAP);
            logger.error("{}", e);
            return "";
        }
    }
}
