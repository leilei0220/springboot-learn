package com.leilei.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : leilei
 * @date : 16:59 2020/2/18
 * @desc : Redis  String  Map  set list 存储操作工具类
 */
@Component
public class RedisUtil {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  /**
   * 获取一个redis分布锁
   *
   * @param lockKey        锁住的key
   * @param lockExpireMils 锁住的时长。如果超时未解锁，视为加锁线程死亡，其他线程可夺取锁
   * @return
   */
  public boolean lock(String lockKey, long lockExpireMils) {
    return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
      long nowTime = System.currentTimeMillis();
      Boolean acquire = connection.setNX(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
      if (acquire) {
        return Boolean.TRUE;
      } else {
        byte[] value = connection.get(lockKey.getBytes());
        if (Objects.nonNull(value) && value.length > 0) {
          long oldTime = Long.parseLong(new String(value));
          if (oldTime < nowTime) {
            //connection.getSet：返回这个key的旧值并设置新值。
            byte[] oldValue = connection.getSet(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
            //当key不存时会返回空，表示key不存在或者已在管道中使用
            return oldValue == null ? false : Long.parseLong(new String(oldValue)) < nowTime;
          }
        }
      }
      return Boolean.FALSE;
    });
  }

  /**
   * 指定缓存失效时间
   *
   * @param key  键
   * @param time 时间(秒)
   * @return
   */
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 根据key 获取过期时间
   *
   * @param key 键 不能为null
   * @return 时间(秒) 返回0代表为永久有效
   */
  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * 判断key是否存在
   *
   * @param key 键
   * @return true 存在 false不存在
   */
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 删除缓存
   *
   * @param key 可以传一个值 或多个
   */
  @SuppressWarnings("unchecked")
  public void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(CollectionUtils.arrayToList(key));
      }
    }
  }
  // ============================String=============================

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * 普通缓存放入
   *
   * @param key   键
   * @param value 值
   * @return true成功 false失败
   */
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存放入并设置时间
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期 单位为秒
   * @return true成功 false 失败
   */
  public boolean set(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 递增
   *
   * @param key   键
   * @param delta 要增加几(大于0)
   * @return
   */
  public long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * 递减
   *
   * @param key   键
   * @param delta 要减少几(小于0)
   * @return
   */
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key, -delta);
  }
  // ================================Map=================================

  /**
   * HashGet
   *
   * @param key  键 不能为null
   * @param item 项 不能为null
   * @return 值
   */
  public Object hget(String key, String item) {
    return redisTemplate.opsForHash().get(key, item);
  }

  /**
   * 获取hashKey对应的所有键值
   *
   * @param key 键
   * @return 对应的多个键值
   */
  public Map<Object, Object> hmget(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * HashSet
   *
   * @param key 键
   * @param map 对应多个键值
   * @return true 成功 false 失败
   */
  public boolean hmset(String key, Map<String, Object> map) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * HashSet 并设置时间
   *
   * @param key  键
   * @param map  对应多个键值
   * @param time 时间(秒)
   * @return true成功 false失败
   */
  public boolean hmset(String key, Map<String, Object> map, long time) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @return true 成功 false失败
   */
  public boolean hset(String key, String item, Object value) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
   * @return true 成功 false失败
   */
  public boolean hset(String key, String item, Object value, long time) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 删除hash表中的值
   *
   * @param key  键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public void hdel(String key, Object... item) {
    redisTemplate.opsForHash().delete(key, item);
  }

  /**
   * 判断hash表中是否有该项的值
   *
   * @param key  键 不能为null
   * @param item 项 不能为null
   * @return true 存在 false不存在
   */
  public boolean hHasKey(String key, String item) {
    return redisTemplate.opsForHash().hasKey(key, item);
  }

  /**
   * hash递增 如果不存在,就会创建一个 并把新增后的值返回
   *
   * @param key  键
   * @param item 项
   * @param by   要增加几(大于0)
   * @return
   */
  public double hincr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, by);
  }

  /**
   * hash递减
   *
   * @param key  键
   * @param item 项
   * @param by   要减少记(小于0)
   * @return
   */
  public double hdecr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, -by);
  }
  // ============================set=============================

  /**
   * 根据key获取Set中的所有值
   *
   * @param key 键
   * @return
   */
  public Set<Object> sGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key   键
   * @param value 值
   * @return true 存在 false不存在
   */
  public boolean sHasKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将数据放入set缓存
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long sSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 将set数据放入缓存
   *
   * @param key    键
   * @param time   时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long sSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0) {
        expire(key, time);
      }
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 获取set缓存的长度
   *
   * @param key 键
   * @return
   */
  public long sGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 移除值为value的
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public long setRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(key, values);
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * set 骚操作  并集
   * @param key1
   * @param key2
   * @return
   */
  public Set<Object> unionAll(String key1, String key2) {
    try {
      return redisTemplate.opsForSet().union(key1, key2);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * set 骚操作  交集
   * @param key1
   * @param key2
   * @return
   */
  public Set<Object> intersect(String key1, String key2) {
    try {
      return redisTemplate.opsForSet().intersect(key1, key2);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  //=================================zSet=============================

  /**
   * 添加一个
   *
   * @param key   zset 集合名
   * @param value 存放的值
   * @param score zet 中分数 默认升序排列
   * @return
   */
  public boolean sZset(String key, Object value, double score) {
    try {
      return redisTemplate.opsForZSet().add(key, value, score);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 向zset集合中批量添加数据
   * @param key
   * @param tuples
   * @return
   */
  public Long sZsetList(String key, Set<TypedTuple<Object>> tuples) {
    try {
      return redisTemplate.opsForZSet().add(key, tuples);
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    }
  }


  /**
   * 删除元素 zrem 可同时删除多个值
   *
   * @param key   zset 集合名
   * @param value 要删除的value值 可变参数
   */
  public Long zRemove(String key, Object... value) {
    try {
      return redisTemplate.opsForZSet().remove(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    }
  }

  /**
   * score的增加or减少 zincrby 正数为增 负数为减
   *
   * @param key
   * @param value
   * @param score
   */
  public Double zIncrScore(String key, String value, double score) {
    try {
      return redisTemplate.opsForZSet().incrementScore(key, value, score);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 查询value对应的score   zscore
   *
   * @param key
   * @param value
   * @return
   */
  public Double zScore(String key, String value) {
    try {
      return redisTemplate.opsForZSet().score(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * 判断value在zset中的排名  zrank 返回的是位于为zet集合中索引值
   *
   * @param key
   * @param value
   * @return
   */
  public Long zRank(String key, String value) {
    try {
      return redisTemplate.opsForZSet().rank(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return -1L;
    }
  }
  /**
   * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
   *
   * 返回有序的集合，score小的在前面  （正序）
   *
   * @param key
   * @param start 开始
   * @param end 结束
   * @return value的set集合
   */
  public Set<Object> zRange(String key, int start, int end) {
    return redisTemplate.opsForZSet().range(key, start, end);
  }

  /**
   * 查询集合中指定顺序的值  zrevrange  0 -1 表示获取全部的集合内容
   *
   * 返回有序的集合中，score大的在前面  （倒序）
   *
   * @param key
   * @param start
   * @param end
   * @return
   */
  public Set<Object> reverseRange(String key, int start, int end) {
    return redisTemplate.opsForZSet().reverseRange(key, start, end);
  }


  /**
   * 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
   *
   * @param key
   * @param start
   * @param end
   * @return
   */
  public Set<ZSetOperations.TypedTuple<Object>> rangeWithScore(String key, int start, int end) {
    return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
  }


  /**
   * 根据score的值，来获取满足条件的集合  zrangebyscore
   *
   * @param key
   * @param min
   * @param max
   * @return
   */
  public Set<Object> rangeByScore(String key, double min, double max) {
    return redisTemplate.opsForZSet().rangeByScore(key, min, max);
  }

  /**
   * 根据score的值，来获取满足条件的集合  取出结果后根据索引 以及count 拿到元素集合
   * @param key zset键
   * @param min score 最小值  （条件）
   * @param max score 最大值  （条件）
   * @param offset  从score 拿到集合后 从offset 索引处取值
   * @param count 总共取多少个元素
   * @return 返回新的集合
   */
  public Set<Object> rangeByScore(String key, double min, double max,Long offset,Long count) {
    return redisTemplate.opsForZSet().rangeByScore(key, min, max,offset,count);
  }

  /**
   * 返回集合的长度
   *
   * @param key
   * @return
   */
  public Long zSize(String key) {
    return redisTemplate.opsForZSet().zCard(key);
  }


  // ===============================list=================================

  /**
   * 获取list缓存的内容
   *
   * @param key   键
   * @param start 开始
   * @param end   结束 0 到 -1代表所有值
   * @return
   */
  public List<Object> lGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取list缓存的长度
   *
   * @param key 键
   * @return
   */
  public long lGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 通过索引 获取list中的值
   *
   * @param key   键
   * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   * @return
   */
  public Object lGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key   键
   * @param value 值
   * @return
   */
  public boolean lSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   * @return
   */
  public boolean lSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key   键
   * @param value 值
   * @return
   */
  public boolean lSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   * @return
   */
  public boolean lSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 根据索引修改list中的某条数据
   *
   * @param key   键
   * @param index 索引
   * @param value 值
   * @return
   */
  public boolean lUpdateIndex(String key, long index, Object value) {
    try {
      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 移除N个值为value
   *
   * @param key   键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public long lRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
}
