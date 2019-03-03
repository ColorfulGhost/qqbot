package cc.vimc.bot.controller;


import cc.vimc.bot.impl.BedEventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("bedEvent")
public class BedEventController {
    @Autowired
    BedEventImpl bedEvent;
    @RequestMapping("test")
    @ResponseBody
    public String goToBed(){
        return bedEvent.QQBOT_URL;
    }
}
