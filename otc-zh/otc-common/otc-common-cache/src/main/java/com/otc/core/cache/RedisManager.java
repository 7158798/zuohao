package com.otc.core.cache;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.SerializeHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * RedisManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/19.
 */
final class RedisManager {

    private static Pool jedisPool;

    private static final void e(String msg, Throwable ex) {
        LOG.e(RedisManager.class, msg, ex);
    }

    /**
     * Gets jedis pool.
     *
     * @return the jedis pool
     */
    public static Pool getJedisPool() {
        return jedisPool;
    }

    /**
     * Sets jedis pool manual.
     *
     * @param jedisPool the jedis pool
     */
    public static void setJedisPoolManual(JedisPool jedisPool) {
        RedisManager.jedisPool = jedisPool;
    }

    /**
     * Sets jedis pool.
     *
     * @param jedisPool the jedis pool
     */
    public void setJedisPool(JedisPool jedisPool) {
        RedisManager.jedisPool = jedisPool;
    }

    private static void releaseResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 删除Redis中的所有key
     */
    public static void flushAll() {
        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            jedis.flushAll();
        } catch (Exception e) {
            e("Redis Cache清空失败：", e);
        } finally {
            releaseResource(jedis);
        }
    }

    /**
     * 判断一个key是否存在
     *
     * @param <T> the type parameter
     * @param key the key
     * @return boolean boolean
     */
    public static <T extends Serializable> Boolean exists(T key) {
        Jedis jedis = null;
        Boolean result = false;

        try {
            jedis = (Jedis) jedisPool.getResource();
            result = jedis.exists(getKeyBytes(key));

        } catch (Exception e) {
            e("Cache获取失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }


    /**
     * Save boolean.
     *
     * @param <K>    the type parameter
     * @param <V>    the type parameter
     * @param key    the key
     * @param object the object
     * @return the boolean
     */
    public static <K extends Serializable, V extends Serializable> Boolean save(K key, V object) {
        return save(key, object, -1);
    }

    /**
     * Save boolean.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param key     the key
     * @param object  the object
     * @param seconds the seconds
     * @return the boolean
     */
    public static <K extends Serializable, V extends Serializable> Boolean save(K key, V object, int seconds) {

        Boolean result = false;

        Jedis jedis = null;
        try {
            byte[] keyBytes = getKeyBytes(key);
            jedis = (Jedis) jedisPool.getResource();
            jedis.set(keyBytes, SerializeHelper.serialize(object));
            if (seconds == -1) {
                jedis.persist(keyBytes);
            } else {
                jedis.expire(keyBytes, seconds);
            }
            result = true;
        } catch (Exception e) {
            e("Cache保存失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }

    /**
     * Get v.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param key the key
     * @return the v
     */
    public static <K extends Serializable, V extends Serializable> V get(K key) {
        Jedis jedis = null;
        V v = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            byte[] keyBytes = getKeyBytes(key);
            byte[] obj = jedis.get(keyBytes);
            v = obj == null ? null : SerializeHelper.unSerialize(obj);
        } catch (Exception e) {
            e("Cache获取失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return v;
    }

    /**
     * Gets left time seconds.
     *
     * @param key the key
     * @return the left time seconds
     */
    public static <K extends Serializable> Long getLeftTimeSeconds(K key) {
        Jedis jedis = null;
        Long leftTimeSeconds = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            leftTimeSeconds = jedis.ttl(getKeyBytes(key));
        } catch (Exception e) {
            e("获取有效剩余时间失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return leftTimeSeconds;
    }

    /**
     * Del boolean.
     *
     * @param <K> the type parameter
     * @param key the key
     * @return the boolean
     */
    public static <K extends Serializable> Boolean del(K key) {
        Boolean result = false;
        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            jedis.del(getKeyBytes(key));
            result = true;
        } catch (Exception e) {
            e("Cache删除失败：", e);
        } finally {
            releaseResource(jedis);
        }
        return result;
    }

    /**
     * Del boolean.
     *
     * @param <K>  the type parameter
     * @param keys the keys
     * @return the boolean
     */
    public static <K extends Serializable> Boolean del(K... keys) {
        Boolean result = false;
        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            jedis.del(SerializeHelper.serialize(keys));
            result = true;
        } catch (Exception e) {
            e("Cache删除失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }

    /**
     * Expire boolean.
     *
     * @param key     the key
     * @param seconds the seconds
     * @return the boolean
     */
    public static Boolean expire(Serializable key, int seconds) {

        Boolean result = false;
        Jedis jedis = null;

        try {
            jedis = (Jedis) jedisPool.getResource();
            jedis.expire(getKeyBytes(key), seconds);
            result = true;
        } catch (Exception e) {
            e("Cache设置超时时间失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }


    /**
     * 获得hash中的所有key value
     *
     * @param key the key
     * @return all hash
     */
    public static Map<byte[], byte[]> getAllHash(Serializable key) {
        Jedis jedis = null;
        Map<byte[], byte[]> map = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            map = jedis.hgetAll(getKeyBytes(key));

        } catch (Exception e) {
            e("Cache获取失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return map;
    }

    /**
     * Keys set.
     *
     * @param pattern the pattern
     * @return the set
     */
    public static Set<byte[]> keys(String pattern) {

        Set<byte[]> allKey = new HashSet<>();

        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            allKey = jedis.keys(("*" + pattern + "*").getBytes());

        } catch (Exception e) {
            e("Cache获取失败：", e);
        } finally {
            releaseResource(jedis);
        }
        return allKey;
    }

    /**
     * Del hash field.
     *
     * @param <K>   the type parameter
     * @param <F>   the type parameter
     * @param key   the key
     * @param field the field
     * @return the boolean
     */
    public static <K extends Serializable, F extends Serializable> Boolean delHashField(K key, F field) {

        Boolean result = false;

        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();

            long count = jedis.hdel(getKeyBytes(key), SerializeHelper.serialize(field));

            result = count == 1 ? true : false;

        } catch (Exception e) {
            e("Cache删除失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }


    /**
     * Add hash field.
     *
     * @param <K>   the type parameter
     * @param <F>   the type parameter
     * @param <V>   the type parameter
     * @param key   the key
     * @param field the field
     * @param value the value
     * @return the boolean
     */
    public static <K extends Serializable, F extends Serializable, V extends Serializable> Boolean addHashField(K key, F field, V value) {
        Boolean result = false;
        Jedis jedis = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            jedis.hset(getKeyBytes(key), SerializeHelper.serialize(field), SerializeHelper.serialize(value));
            result = true;
        } catch (Exception e) {
            e("Cache保存失败：", e);
        } finally {
            releaseResource(jedis);
        }

        return result;
    }

    /**
     * Gets hash field.
     *
     * @param key   the key
     * @param field the field
     * @return the hash field
     */
    public static <K extends Serializable, F extends Serializable, V extends Serializable> Object getHashField(K key, F field) {
        Jedis jedis = null;
        V v = null;
        try {
            jedis = (Jedis) jedisPool.getResource();
            byte[] obj = jedis.hget(getKeyBytes(key), SerializeHelper.serialize(field));
            v = SerializeHelper.unSerialize(obj);
        } catch (Exception e) {
            e("Cache读取失败：", e);
        } finally {
            releaseResource(jedis);
        }
        return v;
    }

    /**
     * Get key bytes.
     *
     * @param <K> the type parameter
     * @param key the key
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static final <K extends Serializable> byte[] getKeyBytes(K key) throws Exception {
        byte[] keyBytes;
//        if (key instanceof String) {
//            keyBytes = ((String) key).getBytes();
//        } else {
        keyBytes = SerializeHelper.serialize(key);
//        }
        return keyBytes;
    }
}
