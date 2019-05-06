package cc.vimc.bot.impl;

import cc.vimc.bot.model.BotMemory;
import cc.vimc.bot.mapper.BotMemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static cc.vimc.bot.enums.Fields.GROUP;
import static cc.vimc.bot.enums.Fields.PRIVATE;

@Service
public class BotScheduleImpl {
    @Value("${MC_GROUP_QQ}")
    String mcGroupQQ;
    @Autowired
    BotApiImpl botApi;
    @Autowired
    BotMemoryMapper botMemoryMapper;
    @Autowired MinecraftImpl minecraft;

    @Scheduled(cron = "0 25 3 ? * *")
    public void liverEmperor() {
        var players = minecraft.onlinePlayerList();
        if (CollectionUtils.isEmpty(players)) {
            botApi.sendMsgGroup(mcGroupQQ, "寻找肝帝失败，今晚没有发现肝帝在线！");
        } else {
            var message = "今晚肝帝诞生啦！现在还有" + players.size() + "人在线？！\n";
            minecraft.sendCommand("say 发现肝帝！赶紧休息吧~身体重要，nano希望你能健康游戏~");
            var playersString = minecraft.formatPlayers(players);
            botApi.sendMsgGroup(mcGroupQQ, message + playersString);
        }
    }
    @Scheduled(cron = "0 20 6 ? * *")
    public void goodMorning() {
        var botMemory = new BotMemory(null,null,1,null);
        var needNiceDayList =  botMemoryMapper.selectAllBotMemory(botMemory);
        for (BotMemory botMemoryDTO : needNiceDayList) {
            var random = new Random(System.currentTimeMillis());
            var randomInt = random.nextInt(20) % 10;
            if (randomInt>3)
            if (botMemoryDTO.getType().equals(GROUP)) {
                botApi.sendMsgGroup(botMemoryDTO.getId(), "[CQ:image,file=15E9DA60F14104A38746621A477F59FD.jpg,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2292468112-15E9DA60F14104A38746621A477F59FD/0?vuin=1277841527&amp;term=2]");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)) {
                botApi.sendMsgPrivate(botMemoryDTO.getId(), "[CQ:image,file=D070FAEA8F6D04A7DEBE8ADDDD22C2EE.jpg,url=https://c2cpicdw.qpic.cn/offpic_new/815666528//274dc8d6-dce0-4b21-8ae4-b43009a369e5/0?vuin=1277841527&amp;term=2]");
            }
        }
    }

    @Scheduled(cron = "0 30 22 ? * *")
    public void goodNight() {
        var botMemory = new BotMemory(null,null,1,null);
        var needNiceDayList =  botMemoryMapper.selectAllBotMemory(botMemory);
        for (BotMemory botMemoryDTO : needNiceDayList) {
            if (botMemoryDTO.getType().equals(GROUP)) {
                botApi.sendMsgGroup(botMemoryDTO.getId(), "[CQ:image,file=1EAE238AFC37A25A8449BA20001D861B.png,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2240241385-1EAE238AFC37A25A8449BA20001D861B/0?vuin=1277841527&amp;term=2]");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)) {
                botApi.sendMsgPrivate(botMemoryDTO.getId(), "[CQ:image,file=24C8E346B782329A8496CEB1C1E2D4EB.png,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2797522169-24C8E346B782329A8496CEB1C1E2D4EB/0?vuin=1277841527&amp;term=2]");
            }
        }
    }

    @Scheduled(cron = "0 0 4 ? * *")
    public void likeGroupAllUser() {
        //获取所有群组
        var groupList = botApi.getGroupList();
        //获取群组所有成员
        var userList = botApi.getGroupMember(groupList);
        var users = new HashSet<String>();
        //去重
        userList.forEach((k,v)->v.forEach(user->users.add(user.getUser_id())));
        //发送点赞
        users.forEach(user->botApi.sendLike(user));
    }

}
