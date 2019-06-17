package com.idoss.support.util.abstracted;

import com.idoss.api.exception.BizException;

import java.util.List;
import java.util.Set;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
public interface AbstractJedis {

    void set(String key, String value) throws BizException;

    void set(String key, String value, int expireTime) throws BizException;

    String getset(String key, String value) throws BizException;

    String get(String key) throws BizException;

    void del(String key) throws BizException;

    void hash(String key, String field, String value, int expireTime) throws BizException;

    void hash(String key, String field, String value) throws BizException;

    List<String> getTime() throws BizException;

    long setnx(String key, String value) throws BizException;

    String getHashField(String key, String field) throws BizException;

    void rList(String list, String value, int expireTime) throws BizException;

    void rList(String list, String value) throws BizException;

    void lTirm(String list, int start, int end) throws BizException;

    void hDel(String key, String field) throws BizException;

    Long getTTL(String key) throws BizException;

    Long incr(String key, int expireTime) throws BizException;

    Set<String> keys(String preStr) throws BizException;

    List<String> lrange(String key, long start, long end) throws BizException;

}
