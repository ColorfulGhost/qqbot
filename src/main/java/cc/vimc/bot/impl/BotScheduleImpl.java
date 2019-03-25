package cc.vimc.bot.impl;

import cc.vimc.bot.dto.BotMemoryDTO;
import cc.vimc.bot.mapper.BotMemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cc.vimc.bot.enums.Fields.GROUP;
import static cc.vimc.bot.enums.Fields.PRIVATE;

@Service
public class BotScheduleImpl {

    @Autowired
    BotApiImpl botApi;
    @Autowired
    BotMemoryMapper botMemoryMapper;


    @Scheduled(cron = "0 25 7 ? * *")
    public void goodMorning(){
        var needNiceDayList = botMemoryMapper.selectNiceDayAll(null,1);
        for (BotMemoryDTO botMemoryDTO : needNiceDayList) {
            if (botMemoryDTO.getType().equals(GROUP)){
                botApi.sendMsgGroup(botMemoryDTO.getId(),"早安(๑•̀ㅁ•́✧ ！狗群员们！Nice Day！");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)){
                botApi.sendMsgPrivate(botMemoryDTO.getId(),"早安(๑•̀ㅁ•́✧ ！美好的一天开始啦！");
            }
        }
    }

    @Scheduled(cron = "0 25 23 ? * *")
    public void goodNight(){
        var needNiceDayList = botMemoryMapper.selectNiceDayAll(null,1);
        for (BotMemoryDTO botMemoryDTO : needNiceDayList) {
            if (botMemoryDTO.getType().equals(GROUP)){
                botApi.sendMsgGroup(botMemoryDTO.getId(),"晚安w！大家早点睡，熬夜没有女朋友！お(ノ￣0￣)ノや（o￣ ・￣）oす(。＿　＿)。みZZzzzz…");
            }
            if (botMemoryDTO.getType().equals(PRIVATE)){
                botApi.sendMsgPrivate(botMemoryDTO.getId(),"你个肥宅！赶紧睡觉！晚安w！(～﹃～)~zZ");
            }
        }
    }
//    private Map<String,String> getNiceDayKV(List<BotMemoryDTO> botMemoryDTOS){
//       return botMemoryDTOS.stream().collect(Collectors.toMap(BotMemoryDTO::getType,BotMemoryDTO::getId));
//    }
}
