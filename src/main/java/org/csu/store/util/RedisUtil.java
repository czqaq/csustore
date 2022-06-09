package org.csu.store.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedisPool;

@Component
@Data
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> template;

    private static ShardedJedisPool shardedJedisPool;
}
