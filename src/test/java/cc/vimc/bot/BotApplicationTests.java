package cc.vimc.bot;

import cc.vimc.bot.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BotApplicationTests {

    @Resource
    UserDAO userDAO;

    @Test
    public void contextLoads() {
    }

    @Test
    public void usertest(){

        userDAO.setUserToken("tewst");
    }

}
