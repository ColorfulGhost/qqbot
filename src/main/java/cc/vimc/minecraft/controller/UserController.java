package cc.vimc.minecraft.controller;

import cc.vimc.bot.model.ReturnModel;
import cc.vimc.bot.service.impl.MinecraftImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    MinecraftImpl minecraft;

    @RequestMapping("login")
    @ResponseBody
    public ReturnModel login(String username, String password){
        ReturnModel returnModel = new ReturnModel();
//
//        //把username强制转换成小写字母
//       username = username.toLowerCase();
//       String token = minecraft.userVerifyByToken(username,password);
//       if (StringUtils.isEmpty(token)){
//           returnModel.setCode(EnumConstants.GETTOKENFAIL);
//           returnModel.setMsg(EnumConstants.CONSTANTS.get(EnumConstants.GETTOKENFAIL));
//       }else{
//           returnModel.setCode(EnumConstants.SUCCESS);
//           returnModel.setMsg(EnumConstants.CONSTANTS.get(EnumConstants.SUCCESS));
//           returnModel.setData(token);
//       }
       return returnModel;
    }
}
