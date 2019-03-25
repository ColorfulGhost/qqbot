package cc.vimc.bot.mapper;

import cc.vimc.bot.dto.BotMemoryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BotMemoryMapper {

    @Insert("INSERT INTO bot_memory (\n" +
            "            `id`,\n" +
            "            `type`,\n" +
            "            `nice_day`\n" +
            "        )\n" +
            "        VALUES(\n" +
            "            #{id},\n" +
            "            #{type},\n" +
            "            #{niceDay}\n" +
            "        )")
    Boolean insertNiceDay(@Param("id")String id, @Param("type")String type, @Param("niceDay") int niceDay);

    @Update("UPDATE bot_memory\n" +
            "        SET\n" +
            "          nice_day = #{niceDay}"+
            "        WHERE `id` = #{id} and `type`=#{type}")
    Boolean updateNiceDay(@Param("id") String id, @Param("type") String type, @Param("niceDay") int niceDay);

    @Select("        SELECT `id`,`type`,`nice_day`" +
            "        FROM bot_memory\n" +
            "        WHERE `id` = #{id} AND `type` =#{type}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "type",column = "type"),
            @Result(property = "niceDay",column = "nice_day"),
    })
    BotMemoryDTO selectNiceDay(@Param("id") String id, @Param("type") String type);

    @Select("<script>" +
            "SELECT `id`,`type`,`nice_day`" +
            "        FROM bot_memory WHERE `nice_day`= #{niceDay} AND "
            +"<if test='type!=null'>"
            +"`tpye`=#{tpye}"+
            "</if>"
            +"1=1"+
            "</script>")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "type",column = "type"),
            @Result(property = "niceDay",column = "nice_day"),
    })
    List<BotMemoryDTO> selectNiceDayAll(@Param("type")String type,@Param("niceDay") int niceDay);
}
