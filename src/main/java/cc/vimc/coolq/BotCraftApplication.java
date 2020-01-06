package cc.vimc.coolq;

import cc.vimc.bot.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@ImportResource("classpath:xxl-config.xml")
public class BotCraftApplication implements CommandLineRunner {

//    @Autowired
//    private WebSocketClient webSocketClient;

    public static void main(String[] args) {
        SpringApplication.run(BotCraftApplication.class, args);
        log.info("【服务启动完成】");
    }

    @Override
    public void run(String... args) {
//        boolean open = webSocketClient.getWebSocket().isOpen();
        WebSocketUtil webSocketUtil = new WebSocketUtil();
        webSocketUtil.webSocketStompClient();
//        log.warn(open ? "webSocket握手成功！" : "webSocket握手失败！");
//        if (open){

//        }
    }
}
