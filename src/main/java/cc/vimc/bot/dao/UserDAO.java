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

    public boolean setUserToken(String name) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            if (redisTemplate.hasKey(name)) {
                return false;
            }
            operations.set(name, UUID.randomUUID().toString());
            redisTemplate.expire(name, 3, TimeUnit.DAYS);
            result = true;
        } catch (Exception e) {
            logger.error("Redis 缓存token失败", e);
        }
        return result;
    }


    public String getUserToken(String name) {
        if (redisTemplate.hasKey(name)) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            return operations.get(name);
        }
        return null;
    }
}
