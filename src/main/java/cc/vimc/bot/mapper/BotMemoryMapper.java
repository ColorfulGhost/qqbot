package cc.vimc.bot.mapper;

import cc.vimc.bot.model.BotMemory;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wlwang3
 * @Description
 * @return
 * @date 2019/5/6
 */
@Mapper
public interface BotMemoryMapper {
    String[] sqlField = {"id", "type", "nice_day", "translate_jp"};
    String ID = "id";
    String TYPE = "type";
    String NICE_DAY = "niceDay";
    String TRANSLATEJP = "translateJp";
    String tableName = "bot_memory";

    /**
     * 条件查询所有用户记忆数据
     *
     * @return
     */
    @SelectProvider(type = BotMemoryProvider.class, method = "selectAllBotMemory")
    List<BotMemory> selectAllBotMemory(BotMemory botMemory);

    /**
     * 条件查找用户行为数据
     *
     * @param botMemory
     * @return
     */
    @SelectProvider(type = BotMemoryProvider.class, method = "selectBotMemory")
    BotMemory selectBotMemory(BotMemory botMemory);

    /**
     * 插入用户行为数据
     *
     * @param botMemory
     * @return
     */
    @InsertProvider(type = BotMemoryProvider.class, method = "insertBotMemory")
    Boolean insertBotMemory(BotMemory botMemory);

    /**
     * 更新用户行为数据
     *
     * @param botMemory
     * @return
     */
    @UpdateProvider(type = BotMemoryProvider.class, method = "updateBotMemory")
    Boolean updateBotMemory(BotMemory botMemory);

    class BotMemoryProvider {
        public String selectAllBotMemory(BotMemory botMemory) {
            return new SQL() {{
                SELECT(sqlField);
                FROM(tableName);
                WHERE(botMemory.getAllFieldAndData(true,
                        new String[]{NICE_DAY, TRANSLATEJP}));
            }}.toString();
        }

        public String updateBotMemory(BotMemory botMemory) {
            return new SQL() {{
                UPDATE(tableName);
                SET(botMemory.getAllFieldAndData(false,
                        new String[]{NICE_DAY, TRANSLATEJP}));
                WHERE(botMemory.getAllFieldAndData(true,
                        new String[]{ID, TYPE}));
            }}.toString();
        }

        public String insertBotMemory(BotMemory botMemory) {
            return new SQL() {{
                INSERT_INTO(tableName);
                VALUES(sqlField[0],new StringBuilder().append("'").append(botMemory.getId()).append("'").toString());
                VALUES(sqlField[1],new StringBuilder().append("'").append(botMemory.getType()).append("'").toString());
                if (botMemory.getNiceDay() != null) {
                    VALUES(sqlField[2],botMemory.getNiceDay().toString());
                }
                if (botMemory.getTranslateJp()!=null){
                    VALUES(sqlField[3],botMemory.getTranslateJp().toString());
                }
            }}.toString();
        }

        public String selectBotMemory(BotMemory botMemory) {
            return new SQL() {{
                SELECT(sqlField);
                FROM(tableName);
                WHERE(botMemory.getAllFieldAndData(true, new String[]{ID, TYPE}));
            }}.toString();
        }
    }
}
