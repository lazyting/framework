package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisHashAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisHashAccessorUtil.class);

    /**
     * 向名称为key的hash中添加元素field<—>value
     *
     * @param key
     * @param field
     * @param value
     */
    public static boolean hset(String key, String field, String value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hset(key, field, value) == 1L;
        } catch (Exception e) {
            logger.error("Hash hset fail ", e);
        }
        return false;
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     * @param field
     */
    public static String hget(String key, String field) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hget(key, field);
        } catch (Exception e) {
            logger.error("Hash hget fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     * @param field
     * @return
     */
    public static List<String> hmget(String key, String... field) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hmget(key, field);
        } catch (Exception e) {
            logger.error("Hash hmget fail ", e);
        }
        return null;
    }

    /**
     * 向名称为key的hash中添加元素field i<—>value i
     *
     * @param key
     * @param paramMap
     */
    public static boolean hmset(String key, Map<String, String> paramMap) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hmset(key, paramMap).equalsIgnoreCase("OK");
        } catch (Exception e) {
            logger.error("Hash hmset fail ", e);
        }
        return false;
    }

    /**
     * 将名称为key的hash中field的value增加integer
     *
     * @param key
     * @param field
     * @param integer
     */
    public static Long hincrBy(String key, String field, long integer) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hincrBy(key, field, integer);//返回计算结果
        } catch (Exception e) {
            logger.error("Hash hincrBy fail ", e);
        }
        return null;
    }

    /**
     * 名称为key的hash中是否存在键为field的域
     *
     * @param key
     * @param field
     * @return
     */
    public static boolean hexists(String key, String field) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hexists(key, field);
        } catch (Exception e) {
            logger.error("Hash hexists fail ", e);
        }
        return false;
    }

    /**
     * 删除名称为key的hash中键为field的域
     *
     * @param key
     * @param field
     * @return
     */
    public static boolean hdel(String key, String... field) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hdel(key, field) == 1L;
        } catch (Exception e) {
            logger.error("Hash hdel fail ", e);
        }
        return false;
    }

    /**
     * 返回名称为key的hash中元素个数
     *
     * @param key
     * @return
     */
    public static Long hlen(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hlen(key);
        } catch (Exception e) {
            logger.error("Hash hlen fail ", e);
        }
        return 0L;
    }

    /**
     * 返回名称为key的hash中所有键
     *
     * @param key
     */
    public static Set<String> hkeys(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hkeys(key);
        } catch (Exception e) {
            logger.error("Hash hkeys fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的hash中所有键对应的value
     *
     * @param key
     */
    public static List<String> hvals(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hvals(key);
        } catch (Exception e) {
            logger.error("Hash hvals fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的hash中所有的键（field）及其对应的value
     *
     * @param key
     */
    public static Map<String, String> hgetall(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("Hash hgetall fail " + e);
        }
        return null;
    }
}
