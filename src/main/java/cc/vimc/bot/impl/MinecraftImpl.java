package cc.vimc.bot.impl;


import cc.vimc.bot.dto.UserDTO;
import cc.vimc.bot.mapper.MinecraftMapper;
import cc.vimc.bot.rcon.RconClient;
import cc.vimc.bot.util.HttpUtils;
import cc.vimc.bot.util.Sha256;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static cc.vimc.bot.enums.Apis.SEND_GROUP_MSG;
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

    public Boolean getUser(String userName,String password){
        var userDTO =   minecraftMapper.getUserDTOByAll(userName);
        //1是加密方案 2是盐 3是sha256  盐
        List<String> crypts= Arrays.asList(userDTO.getPassword().split("\\$"));
        var possible = crypts.get(1);
        var salt = crypts.get(2);
        var encode = crypts.get(3);
        String encryptedPassword = "$"+possible+"$"+salt+"$"+ Sha256.sha256(Sha256.sha256(password)+salt);
        if (userDTO.getPassword().equals(encryptedPassword)){
            return true;
        }else {
            return false;
        }
    }

    public void postPlayerList(String sendNickName) {
        var players = this.onlinePlayerList();
        var playerSize = players.size();
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
        try (RconClient client = RconClient.open(MCSERVERHOST, MCRCONPORT, RCONPASSWORD)) {
            var message = client.sendCommand(command).replaceAll("§.*?[0-z]", "");
            //这里是为了去除服务端返回字符串后面\n换行
            return message.substring(0,message.length()-1);
        } catch (Exception e) {
            logger.error("服务器RCON协议出现错误：{}", e);
            return "链接服务器RCON失败，请联系欧尼酱。";
        }
    }


}
