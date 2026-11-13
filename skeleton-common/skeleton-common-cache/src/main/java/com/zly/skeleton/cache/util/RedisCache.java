package com.zly.skeleton.cache.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作工具类。
 * <p>
 * RedisTemplate 的 key 使用 String 序列化，value 使用 JSON 序列化（带类型信息），
 * 因此存取对象时无需再做手工转换。
 *
 * @author ZLY
 * @since 2026/10/27 17:11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisCache
{
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间（秒），-1 表示永不过期，-2 表示 key 不存在
     */
    public long getExpire(final String key)
    {
        Long expire = redisTemplate.getExpire(key);
        return expire == null ? -2L : expire;
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(final String key)
    {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 获得缓存的基本对象。
     * <p>
     * 泛型仅由调用方约定，实际类型由 JSON 中的类型信息还原，类型不符时会在赋值处抛
     * {@link ClassCastException}，需要强类型请使用 {@link #getCacheObject(String, Class)}。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return (T) operation.get(key);
    }

    /**
     * 获得缓存的基本对象，并做类型校验
     *
     * @param key  缓存键值
     * @param type 期望的类型
     * @return 缓存键值对应的数据，key 不存在或类型不匹配时返回 null
     */
    public <T> T getCacheObject(final String key, final Class<T> type)
    {
        Object value = getCacheObject(key);
        if (value == null)
        {
            return null;
        }
        if (!type.isInstance(value))
        {
            log.warn("[RedisCache.getCacheObject]:缓存类型不匹配, key={}, expect={}, actual={}",
                    key, type.getName(), value.getClass().getName());
            return null;
        }
        return type.cast(value);
    }

    /**
     * 删除单个对象
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean deleteObject(final String key)
    {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     *
     * @param keys 多个 key
     * @return 是否删除成功
     */
    public boolean deleteObject(final Collection<String> keys)
    {
        if (keys == null || keys.isEmpty())
        {
            return false;
        }
        Long count = redisTemplate.delete(keys);
        return count != null && count > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象数量
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        if (dataList == null || dataList.isEmpty())
        {
            return 0;
        }
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList.toArray());
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getCacheList(final String key)
    {
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        if (range == null)
        {
            return Collections.emptyList();
        }
        return (List<T>) range;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> long setCacheSet(final String key, final Set<T> dataSet)
    {
        if (dataSet == null || dataSet.isEmpty())
        {
            return 0;
        }
        Long count = redisTemplate.opsForSet().add(key, dataSet.toArray());
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的set
     *
     * @param key 缓存键值
     * @return 缓存的数据
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> getCacheSet(final String key)
    {
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (members == null)
        {
            return Collections.emptySet();
        }
        return (Set<T>) members;
    }

    /**
     * 缓存Map
     *
     * @param key     缓存键值
     * @param dataMap 缓存的数据
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null && !dataMap.isEmpty())
        {
            Map<String, Object> map = new HashMap<>(dataMap);
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存键值
     * @return 缓存的数据
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getCacheMap(final String key)
    {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Map<String, T> result = new HashMap<>(entries.size());
        entries.forEach((k, v) -> result.put(String.valueOf(k), (T) v));
        return result;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
        return (T) opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<String> hKeys)
    {
        List<Object> values = redisTemplate.opsForHash().multiGet(key, new ArrayList<>(hKeys));
        return (List<T>) values;
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        Long count = redisTemplate.opsForHash().delete(key, hKey);
        return count != null && count > 0;
    }

    /**
     * 按前缀匹配 key（生产环境 key 较多时会阻塞 Redis，慎用）
     *
     * @param pattern 匹配模式，如 sys_config:*
     * @return key 集合
     */
    public Collection<String> keys(final String pattern)
    {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys == null ? Collections.emptySet() : keys;
    }
}
