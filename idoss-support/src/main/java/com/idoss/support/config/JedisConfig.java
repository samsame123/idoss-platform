package com.idoss.support.config;

import com.idoss.support.util.Jedis4DB0;
import com.idoss.support.util.abstracted.AbstractJedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Slf4j
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Value("${spring.redis.pool.max-active}")
    private int redisPoolMaxActive;

    @Value("${spring.redis.pool.max-idle}")
    private int redisPoolMaxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int redisPoolMinIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long redisPoolMaxWait;

    private JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisPoolMaxIdle);
        poolConfig.setMinIdle(redisPoolMinIdle);
        poolConfig.setMaxWaitMillis(redisPoolMaxWait);
        poolConfig.setMaxTotal(redisPoolMaxActive);
        log.info(this.toString());
        return poolConfig;
    }

    private JedisPool getJedisPool0() {
        return new JedisPool(getJedisPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword, 0);
    }

    @Bean(name = "jedis0")
    public AbstractJedis getJedisUtil() {
        return new Jedis4DB0(getJedisPool0());
    }

}
