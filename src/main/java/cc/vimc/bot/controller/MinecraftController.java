package cc.vimc.bot.controller;


import cc.vimc.bot.impl.MinecraftImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("minecraft")
public class MinecraftController {

    @Autowired
    MinecraftImpl minecraft;
    @RequestMapping("console")
    @ResponseBody
    public void console(String command) throws NoSuchFieldException, IllegalAccessException {
        minecraft.sendCommand(command);

    }
}
