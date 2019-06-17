package com.idoss.support.util;

import com.idoss.support.util.abstracted.DefaultAbstractJedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
public class Jedis4DB0 extends DefaultAbstractJedis {

    public Jedis4DB0(JedisPool jedisPool) {
        super(jedisPool);
    }

}
