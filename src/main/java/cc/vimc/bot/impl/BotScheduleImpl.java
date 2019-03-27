package cc.vimc.bot.impl;

import cc.vimc.bot.dto.BotMemoryDTO;
import cc.vimc.bot.mapper.BotMemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

import static cc.vimc.bot.enums.Fields.GROUP;
import static cc.vimc.bot.enums.Fields.PRIVATE;

@Service
public class BotScheduleImpl {

    @Autowired
    BotApiImpl botApi;
    @Autowired
    BotMemoryMapper botMemoryMapper;


    @Scheduled(cron = "0 30 6 ? * *")
    public void goodMorning() {
        var needNiceDayList = botMemoryMapper.selectNiceDayAll(null, 1);
        for (BotMemoryDTO botMemoryDTO : needNiceDayList) {
            if (botMemoryDTO.getType().equals(GROUP)) {
                botApi.sendMsgGroup(botMemoryDTO.getId(), "[CQ:image,file=15E9DA60F14104A38746621A477F59FD.jpg,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2292468112-15E9DA60F14104A38746621A477F59FD/0?vuin=1277841527&amp;term=2]");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)) {
                botApi.sendMsgPrivate(botMemoryDTO.getId(), "[CQ:image,file=18E924A1319C2FCDA21418AAB5C43CA4.png,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2543752398-18E924A1319C2FCDA21418AAB5C43CA4/0?vuin=1277841527&amp;term=2]早安(๑•̀ㅁ•́✧ ！美好的一天开始啦！");
            }
        }
    }

    @Scheduled(cron = "0 30 22 ? * *")
    public void goodNight() {
        var needNiceDayList = botMemoryMapper.selectNiceDayAll(null, 1);
        for (BotMemoryDTO botMemoryDTO : needNiceDayList) {
            if (botMemoryDTO.getType().equals(GROUP)) {
                botApi.sendMsgGroup(botMemoryDTO.getId(), "[CQ:image,file=24C8E346B782329A8496CEB1C1E2D4EB.png,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2797522169-24C8E346B782329A8496CEB1C1E2D4EB/0?vuin=1277841527&amp;term=2] 熬夜没有女朋友！お(ノ￣0￣)ノや（o￣ ・￣）oす(。＿　＿)。みZZzzzz…");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)) {
                botApi.sendMsgPrivate(botMemoryDTO.getId(), "你个肥宅！赶紧睡觉！晚安w！[CQ:image,file=1EAE238AFC37A25A8449BA20001D861B.png,url=https://gchat.qpic.cn/gchatpic_new/815666528/3810510534-2240241385-1EAE238AFC37A25A8449BA20001D861B/0?vuin=1277841527&amp;term=2]");
            }
        }
    }

    @Scheduled(cron = "0 0 6 ? * *")
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
