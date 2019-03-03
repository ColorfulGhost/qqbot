package cc.vimc.bot.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BedEventImpl {
    private static final Logger logger = LoggerFactory.getLogger(BedEventImpl.class);

    @Value("${QQBOT_URL}")
    public String QQBOT_URL;

    @Scheduled(cron = "30 22 * * *")
    public Boolean goToBed(){
        return true;
    }

    @Scheduled(cron = "0 6 * * *")
    public Boolean wakeUp(){
        return true;
    }
}
