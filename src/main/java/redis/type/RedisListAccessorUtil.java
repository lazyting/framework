package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

public class RedisListAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisListAccessorUtil.class);

    /**
     * 在名称为key的list尾添加一个值为value的元素
     *
     * @param key
     * @param value
     */
    public static boolean rpush(String key, String... value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.rpush(key, value);
            return result == 1L;
        } catch (Exception e) {
            logger.error("List rpush fail " + e);
        }
        return false;
    }

    /**
     * 在名称为key的list头添加一个值为value的元素
     *
     * @param key
     * @param value
     */
    public static boolean lpush(String key, String... value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.lpush(key, value);
            return result == 1L;
        } catch (Exception e) {
            logger.error("List Lpush fail " + e);
        }
        return false;
    }

    /**
     * 返回名称为key的list的长度
     *
     * @param key
     */
    public static Long llen(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.llen(key);
        } catch (Exception e) {
            logger.error("List llen fail " + e);
        }
        return null;
    }

    /**
     * 返回名称为key的list中start至end之间的元素（下标从0开始，下同）
     *
     * @param key
     * @param start
     * @param end
     */
    public static List<String> lrange(String key, long start, long end) {
        if (start < 0 || end < start) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("List lrange fail " + e);
        }
        return null;
    }

    /**
     * 截取名称为key的list，保留start至end之间的元素
     *
     * @param key
     * @param start
     * @param end
     */
    public static String ltrim(String key, long start, long end) {
        if (start < 0 || end < start) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.ltrim(key, start, end);
        } catch (Exception e) {
            logger.error("List ltrim fail " + e);
        }
        return null;
    }

    /**
     * 返回名称为key的list中index位置的元素
     *
     * @param key
     * @param index
     */
    public static String lindex(String key, long index) {
        if (index < 0) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.lindex(key, index);
        } catch (Exception e) {
            logger.error("List lindex fail " + e);
        }
        return null;
    }

    /**
     * 给名称为key的list中index位置的元素赋值为value
     *
     * @param key
     * @param index
     * @param value
     */
    public static String lset(String key, long index, String value) {
        if (index < 0) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.lset(key, index, value);
        } catch (Exception e) {
            logger.error("List lset fail " + e);
        }
        return null;
    }

    /**
     * 删除count个名称为key的list中值为value的元素。
     * count=0,删除所有值为value的元素;
     * count>0,从头至尾删除count个值为value的元素;
     * count<0,从尾到头删除count个值为value的元素。
     *
     * @param key
     * @param count
     * @param value
     */
    public static Long lrem(String key, long count, String value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.lrem(key, count, value);
        } catch (Exception e) {
            logger.error("List lrem fail " + e);
        }
        return null;
    }

    /**
     * 返回并删除名称为key的list中的首元素
     *
     * @param key
     */
    public static String lpop(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.lpop(key);
        } catch (Exception e) {
            logger.error("List lrem fail " + e);
        }
        return null;
    }

    /**
     * 返回并删除名称为key的list中的尾元素
     *
     * @param key
     */
    public static String rpop(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.rpop(key);
        } catch (Exception e) {
            logger.error("List lrem fail " + e);
        }
        return null;
    }
}
