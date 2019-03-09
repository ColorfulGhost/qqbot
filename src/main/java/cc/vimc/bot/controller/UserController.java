package cc.vimc.bot.controller;

import cc.vimc.bot.impl.MinecraftImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    MinecraftImpl minecraft;

    @RequestMapping("login")
    @ResponseBody
    public Boolean login(String username, String password){
        //把username强制转换成小写字母
       username = username.toLowerCase();
       return minecraft.getUser(username,password);
//        return s;

    }
}
