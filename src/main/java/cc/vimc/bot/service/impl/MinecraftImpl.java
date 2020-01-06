package cc.vimc.bot.service.impl;


import cc.vimc.bot.mapper.MinecraftMapper;
import cc.vimc.minecraft.rcon.RconClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Description 处理Minecraft消息
 * @author Ghost
 * @return
 * @date 2019/12/3
 */
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
    @Autowired
    BotApiImpl botApi;
    @Autowired
    private MinecraftMapper minecraftMapper;

    public List<String> onlinePlayerList() {
        return minecraftMapper.onlinePlayerList();
    }

//    public String userVerifyByToken(String userName, String password) {
//        var userDTO = minecraftMapper.getUserDTOByAll(userName);
//        //1是加密方案 2是盐 3是sha256  盐
//        List<String> crypts = Arrays.asList(userDTO.getPassword().split("\\$"));
//        var possible = crypts.get(1);
//        var salt = crypts.get(2);
//        var encode = crypts.get(3);
//        String encryptedPassword = "$" + possible + "$" + salt + "$" + Sha256.sha256(Sha256.sha256(password) + salt);
//        if (userDTO.getPassword().equals(encryptedPassword)) {
//            String token = userDAO.getUserToken(userName);
//            if (StringUtils.isEmpty(token)){
//                token = userDAO.setUserToken(userName);
//            }
//            return token;
//        }
//        return null;
//    }

    public String postPlayerList(String sendNickName) {
        var players = onlinePlayerList();
        var playerSize = players.size();
        var wall = "";
        var message ="";
        if (CollectionUtils.isEmpty(players)) {
             message = "Hi~" + sendNickName + "，当前没有在线玩家。";
        } else {
            wall= "Hi~" + sendNickName + "\n当前在线玩家：" + playerSize + "人\n";
             message = formatPlayers(players);
        }
        return wall+message;
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

    public String formatPlayers(List<String> players) {
        Optional<String> playerListOptionals = players.stream().reduce((s1, s2) -> s1 + "\n" + s2);
        if (playerListOptionals.isPresent()) {
            return playerListOptionals.get();
        }
        return null;
    }


}
