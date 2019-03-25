package cc.vimc.bot.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class MessageDAO {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);

    @Autowired
    RedisTemplate redisTemplate;

    public boolean repeatMessageCount(String groupId, String message) {
        var key = "group" + ":" + groupId + ":" + message;
        try {
            ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
            operations.increment(key);
            if (redisTemplate.hasKey(key)) {
                var count = operations.get(key);
                if (count != null) {
                    if (count == 3) {
                        return hasSendMessage(key);
                    }
                }
            }
            redisTemplate.expire(key, 2, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.error("Redis 缓存token失败", e);
        }
        return false;
    }

    public boolean hasSendMessage(String key){
        var hasSendMessage = key + ":hasSend";
        ValueOperations<String, Boolean> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(hasSendMessage)){
            return false;
        }else {
            operations.set(hasSendMessage,true);
        }
        redisTemplate.expire(hasSendMessage, 2, TimeUnit.HOURS);
        return true;

    }

}
