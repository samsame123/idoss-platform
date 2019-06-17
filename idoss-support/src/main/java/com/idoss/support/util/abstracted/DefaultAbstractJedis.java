package com.idoss.support.util.abstracted;

import com.idoss.api.exception.BizException;
import com.idoss.support.error.IdossMainErrorCode;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Slf4j
public abstract class DefaultAbstractJedis implements AbstractJedis{

    private JedisPool pool;

    public DefaultAbstractJedis(){}

    public DefaultAbstractJedis(JedisPool pool){
        this.pool = pool;
    }

    /**
     * 获取字符串
     * @param key
     * @return
     */
    @Override
    public String get(String key) throws BizException {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 存储字符串
     * @param key
     * @param value
     * @param expireTime expireTime<0时，不会设置失效时间
     */
    @Override
    public void set(String key, String value, int expireTime) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            if (0 < expireTime) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 存储字符串
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) throws BizException {
        set(key, value, -1);
    }

    /**
     * 获取hash类型value
     * @param key   hash的键
     * @param field hash的域
     */
    @Override
    public String getHashField(String key, String field) throws BizException {
        String value;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * hash类型存储
     * @param field      hash域
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间，小于0不设置失效时间
     */
    @Override
    public void hash(String key, String field, String value, int expireTime) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hset(key, field, value);
            if (0 < expireTime) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void hash(String key, String field, String value) throws BizException{
        hash(key, field, value, -1);
    }

    /**
     * 从右存储list
     * @param list list名
     * @param value 值
     * @param expireTime  过期时间
     */
    @Override
    public void rList(String list, String value, int expireTime) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.rpush(list, value);
            if (0 < expireTime) {
                jedis.expire(list, expireTime);
            }
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void rList(String list, String value) throws BizException {
        rList(list, value, -1);
    }

    /**
     * 修剪list（>0正序，<0倒序）
     * @param list 需要修剪的list
     * @param start 开始位置
     * @param end 解释位置
     */
    @Override
    public void lTirm(String list, int start, int end) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.ltrim(list, start, end);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除键
     * @param key 需删除的键
     */
    @Override
    public void del(String key) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除hash里面的field
     * @param key   键
     * @param field 域
     */
    @Override
    public void hDel(String key, String field) throws BizException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取失效时间
     * @param key
     * @return
     */
    @Override
    public Long getTTL(String key) throws BizException {
        Jedis jedis = null;
        long ttl = 0l;
        try {
            jedis = pool.getResource();
            ttl = jedis.ttl(key);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return ttl;
    }

    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @param expireTime
     * @return
     */
    @Override
    public Long incr(String key, int expireTime) throws BizException {
        Jedis jedis = null;
        long value = 0L;
        try {
            jedis = pool.getResource();
            value = jedis.incr(key);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取指定前缀的key
     * @param preStr 指定前缀
     */
    @Override
    public Set<String> keys(String preStr) throws BizException {
        Jedis jedis = null;
        Set<String> keys = new TreeSet<>();
        try {
            jedis = pool.getResource();
            keys = jedis.keys(preStr + "*");

        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return keys;
    }

    /**
     * 获取List列表
     * @param key
     * @param start long，开始索引
     * @param end   long， 结束索引
     * @return List<String>
     */
    @Override
    public List<String> lrange(String key, long start, long end) throws BizException {
        Jedis jedis = null;
        List<String> list = new ArrayList<>();
        try {
            jedis = pool.getResource();
            list = jedis.lrange(key, start, end);

        } catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return list;

    }

    /**
     * 获取统一时间戳
     * result.get(0)UNIX秒,result.get(1)UNIX微秒
     */
    @Override
    public List<String> getTime() throws BizException {
        Jedis jedis = null;
        List<String> result = null;
        try{
            jedis = pool.getResource();
            result = jedis.time();
        }catch (Exception e) {
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public long setnx(String key, String value) throws BizException {
        Jedis jedis = null;
        long result = 0l;
        try{
            jedis = pool.getResource();
            result = jedis.setnx(key, value);
        }catch (Exception e){
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        }finally {
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public String getset(String key, String value) throws BizException {
        Jedis jedis = null;
        String result =  null;
        try{
            jedis = pool.getResource();
            result = jedis.getSet(key, value);
        }catch (Exception e){
            log.error(IdossMainErrorCode.REDIS_ERROR.getMessage(), e);
            throw new BizException(IdossMainErrorCode.REDIS_ERROR);
        }finally {
            returnResource(jedis);
        }
        return result;
    }


    private void returnResource(final Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }

}
