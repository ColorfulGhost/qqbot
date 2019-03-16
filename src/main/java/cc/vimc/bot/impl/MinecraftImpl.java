package cc.vimc.bot.impl;


import cc.vimc.bot.mapper.MinecraftMapper;
import cc.vimc.bot.rcon.RconClient;
import cc.vimc.bot.util.HttpUtils;
import cc.vimc.bot.util.Sha256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static cc.vimc.bot.enums.Apis.SEND_GROUP_MSG;
import static cc.vimc.bot.enums.Fields.GROUP_ID;
import static cc.vimc.bot.enums.Fields.MESSAGE;

@Service
public class MinecraftImpl {

    private static final Logger logger = LoggerFactory.getLogger(MinecraftImpl.class);
    @Value("${RCON.PASSWORD}")
    String rconPassword;

    @Value("${MCSERVER.HOST}")
    String mcServerHost;

    @Value("${RCON.PORT}")
    Integer mcRCONPort;


    @Value("${QQBOT_URL}")
    String qqbotUrl;

    @Value("${MC_GROUP_QQ}")
    String mcGroupQQ;

    @Autowired
    BotApiImpl botApi;

    @Autowired
    private MinecraftMapper minecraftMapper;

    public List<String> onlinePlayerList() {
        return minecraftMapper.onlinePlayerList();
    }

    public String userVerifyByToken(String userName, String password) {
        var userDTO = minecraftMapper.getUserDTOByAll(userName);
        //1是加密方案 2是盐 3是sha256  盐
        List<String> crypts = Arrays.asList(userDTO.getPassword().split("\\$"));
        var possible = crypts.get(1);
        var salt = crypts.get(2);
        var encode = crypts.get(3);
        String encryptedPassword = "$" + possible + "$" + salt + "$" + Sha256.sha256(Sha256.sha256(password) + salt);
        if (userDTO.getPassword().equals(encryptedPassword)) {
            return Sha256.sha256(salt + encode);
        }
        return null;

    }

    public void postPlayerList(String sendNickName) {
        var players = onlinePlayerList();
        var playerSize = players.size();
        var request = new HashMap();
        request.put(GROUP_ID, mcGroupQQ);

        if (CollectionUtils.isEmpty(players)) {
            var message = "Hi~" + sendNickName + "，当前没有在线玩家。";
            request.put(MESSAGE, message);
            HttpUtils.httpPost(qqbotUrl + SEND_GROUP_MSG, request, Collections.emptyMap());
        } else {
            var wall = "Hi~" + sendNickName + "\n当前在线玩家：" + playerSize + "人\n";
            String message = formatPlayers(players);
            request.put(MESSAGE, wall + message);
            HttpUtils.httpPost(qqbotUrl + SEND_GROUP_MSG, request, Collections.emptyMap());
        }
    }

    public String sendCommand(String command) {
        try (RconClient client = RconClient.open(mcServerHost, mcRCONPort, rconPassword)) {
            var message = client.sendCommand(command).replaceAll("§.*?[0-z]", "");
            //这里是为了去除服务端返回字符串后面\n换行
            return message.substring(0, message.length() - 1);
        } catch (Exception e) {
            logger.error("服务器RCON协议出现错误：{}", e);
            return "链接服务器RCON失败，请联系欧尼酱。";
        }
    }

    private String formatPlayers(List<String> players) {
        Optional<String> playerListOptionals = players.stream().reduce((s1, s2) -> s1 + "\n" + s2);
        if (playerListOptionals.isPresent()) {
            return playerListOptionals.get();
        }
        return null;
    }

    @Scheduled(cron = "0 25 3 ? * *")
    public void liverEmperor() {
        var players = onlinePlayerList();
        if (CollectionUtils.isEmpty(players)) {
            botApi.sendMsgGrouup(mcGroupQQ, "寻找肝帝失败，今晚没有发现肝帝在线！");
        } else {
            var message = "今晚肝帝诞生啦！现在还有" + players.size() + "人在线？！\n";
            sendCommand("say 发现肝帝！赶紧休息吧~身体重要，nano希望你能健康游戏~");
            var playersString = formatPlayers(players);
            botApi.sendMsgGrouup(mcGroupQQ, message + playersString);
        }
    }
}
