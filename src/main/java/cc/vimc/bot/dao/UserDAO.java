package cc.vimc.bot.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    RedisTemplate redisTemplate;


    public String setUserToken(String name) {
        String key = "user:" + name;
        String token = "";
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            if (redisTemplate.hasKey(key)) {
                return operations.get(key);
            }
            operations.set(key, UUID.randomUUID().toString());
            redisTemplate.expire(key, 3, TimeUnit.DAYS);
        } catch (Exception e) {
            logger.error("Redis 缓存token失败", e);
        }
        return getUserToken(name);
    }


    public String getUserToken(String name) {
        String key = "user:" + name;
        if (redisTemplate.hasKey(key)) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            return operations.get(key);
        }
        return null;
    }
}
