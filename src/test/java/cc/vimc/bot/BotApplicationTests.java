package cc.vimc.bot;

import cc.vimc.bot.dao.UserDAO;
import cc.vimc.bot.dto.BotMemoryDTO;
import cc.vimc.bot.impl.BotApiImpl;
import cc.vimc.bot.impl.BotScheduleImpl;
import cc.vimc.bot.mapper.BotMemoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BotApplicationTests {

    @Resource
    UserDAO userDAO;

    @Autowired
    BotApiImpl botApi;

    @Autowired
    BotMemoryMapper botMemoryMapper;

    @Autowired
    BotScheduleImpl botSchedule;
    @Test
    public void contextLoads() {
    }

    @Test
    public void usertest(){
        userDAO.setUserToken("tewst");
    }

//    @Test
//    public void botApi(){
//        botApi.getGroupMember("320510534",null);
//    }

//    @Test
//    public void botMapper(){
//        botSchedule.likeGroupAllUser();
//    }
}
