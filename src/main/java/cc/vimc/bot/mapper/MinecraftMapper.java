package cc.vimc.bot.mapper;

import cc.vimc.bot.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MinecraftMapper {

    @Select("SELECT `realname` FROM `minecraft`.`authme` WHERE `isLogged` IN ('1')")
    List<String> onlinePlayerList();

    @Select("SELECT `id`, `username`, `realname`, `password`, `ip`, `lastlogin`, `world`, `email`, `isLogged` FROM authme WHERE username=#{username}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "realname",column = "realname"),
            @Result(property = "password",column = "password"),
            @Result(property = "ip",column = "ip"),
            @Result(property = "lastlogin",column = "lastlogin"),
            @Result(property = "world",column = "world"),
            @Result(property = "email",column = "email"),
            @Result(property = "isLogged",column = "isLogged")
    })
    UserDTO getUserDTOByAll(String username);

}
