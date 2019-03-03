package cc.vimc.bot.mapper;

import org.apache.ibatis.annotations.Mapper;
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
}
