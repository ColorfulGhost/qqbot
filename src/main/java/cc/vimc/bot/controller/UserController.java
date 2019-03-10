package cc.vimc.bot.controller;

import cc.vimc.bot.dto.ReturnModel;
import cc.vimc.bot.enums.EnumConstants;
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
    public ReturnModel login(String username, String password){
        ReturnModel returnModel = new ReturnModel();

        //把username强制转换成小写字母
       username = username.toLowerCase();
       Boolean isSuccess = minecraft.userVerify(username,password);
       if (isSuccess){
           returnModel.setCode(EnumConstants.SUCCESS);
           returnModel.setMsg(EnumConstants.CONSTANTS.get(EnumConstants.SUCCESS));
       }
       return returnModel;
    }
}
