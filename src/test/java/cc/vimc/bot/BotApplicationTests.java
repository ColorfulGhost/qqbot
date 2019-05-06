package cc.vimc.bot;

import cc.vimc.bot.dao.UserDAO;
import cc.vimc.bot.provider.ImageRec;
import cc.vimc.bot.provider.TranslateUtil;
import cc.vimc.bot.impl.BotApiImpl;
import cc.vimc.bot.impl.BotScheduleImpl;
import cc.vimc.bot.mapper.BotMemoryMapper;
import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
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

    //
    @Test
    public void usertest() throws IOException, InterruptedException {
//        userDAO.setUserToken("tewst");
//        Map<String,String> map =new HashMap<>();
//        map.put("wd","233333");
//        HttpUtils.httpClinetPost("https://www.baidu.com/s",map);
    }

    @Test
    public void botApi() {
//        botApi.getGroupMember("320510534",null);
//        botSchedule.goodNight();
//        baiduAi.getToken();
//        imageRec("http://i.acg.gy/VOYIW2u.jpg");
//        log.error(baiduAi.getToken());
//        botSchedule.goodMorning();
        TranslateUtil.Translate("天气不错");

    }

    @Test
    public void botMapper() {
        var messages2 = "adadadad[CQ:image,file=B8D3DB919EEE07C3A729FC189BD2DE9C.gif,url=https://gchat.qpic.cn/gchatpic_new/815666528/676456354-2584208473-B8D3DB919EEE07C3A729FC189BD2DE9C/0?vuin=1277841527&amp;term=2]hhah[CQ:image,file=78EB4A8DFB252261F8A9E2F3B68F2717.jpg,url=https://c2cpicdw.qpic.cn/offpic_new/815666528//6cebd03a-3977-446c-947b-934df285e0cf/0?vuin=1277841527&amp;term=2]d[CQ:image,file=5E43923B1E1FBABB04113ADDFCA3CDC6.jpg,url=https://c2cpicdw.qpic.cn/offpic_new/815666528//2d91dcde-3338-4898-b9bd-1544f6084b0e/0?vuin=1277841527&amp;term=2]dadadad";
        var message = "[CQ:image,file=F793CDF7B8D3963DA02E1A44B421F3A8.jpg,url=https://gchat.qpic.cn/gchatpic_new/1277841527/2189324349-2744146997-F793CDF7B8D3963DA02E1A44B421F3A8/0?vuin=1277841527&amp;term=2]";
        var p = Pattern.compile("\\[CQ:(image|face|record),file=(.*?),url=(.*?)\\]");
        p.split(messages2);


        List<String> resultFindAll = ReUtil.findAll("\\[CQ:(image|face|record),file=(.*?),url=(.*?)\\]", messages2, 0, new ArrayList<String>());
//        p.matcher(messages2).group();
//        p.split(messages2);
        return;
    }
}
