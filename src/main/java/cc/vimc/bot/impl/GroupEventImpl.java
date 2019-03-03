package cc.vimc.bot.impl;

import cc.vimc.bot.dto.BotGroupDTO;
import cc.vimc.bot.mapper.MinecraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class GroupEventImpl {

    @Autowired
    private MinecraftMapper minecraftMapper;

    public List<String> onlinePlayerList(BotGroupDTO botGroupDTO){


                return minecraftMapper.onlinePlayerList();



    }
}
