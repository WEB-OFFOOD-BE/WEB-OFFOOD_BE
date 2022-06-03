package com.web.offood.redis;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Lazy
@Component
public class RedisComponent extends BaseService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // run when start project
        // put to hashmap with key/ (key redis, key hash map, object)

        //flush db redis
        redisTemplate.delete(Arrays.stream(RedisKey.values()).collect(Collectors.toList()));
    }


    /**
     * Get value by hashKey with Hashmap
     *
     * @param redisKey
     * @param hashKey
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String redisKey, Object hashKey) {
        Object ob;
        try {
            ob = redisTemplate.opsForHash().get(redisKey, hashKey);
        } catch (Exception e) {
            throw new ApiException(ApiErrorCode.GET_OBJECT_REDIS_FAIL);
        }
        return (T) ob;
    }

    public <T> Map<String, T> getAll(String redisKey) {
        Object ob;
        try {
            ob = redisTemplate.opsForHash().entries(redisKey);
        } catch (Exception e) {
            throw new ApiException(ApiErrorCode.GET_OBJECT_REDIS_FAIL);
        }

        return (Map<String, T>) ob;
    }

    @SuppressWarnings("unchecked")
    public <T> T getIgnoreException(String redisKey, Object hashKey) {
        Object ob;
        try {
            ob = redisTemplate.opsForHash().get(redisKey, hashKey);
        } catch (Exception e) {
            return null;
        }
        return (T) ob;
    }

    /**
     * set value by hashKey with Hashmap
     *
     * @param redisKey
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public void set(String redisKey, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
    }

    /**
     * set value by hashKey with Hashmap
     *
     * @param redisKey
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public void set(String redisKey, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
    }

    /**
     * set value by hashKey with Hashmap
     *
     * @param redisKey
     * @param hashKey
     * @param hashValue
     * @param expireInSeconds
     * @return
     */
    @SuppressWarnings("unchecked")
    public void set(String redisKey, String hashKey, Object hashValue, long expireInSeconds) {
        redisTemplate.expire(redisKey, Duration.ofSeconds(expireInSeconds));
        redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
    }
}
