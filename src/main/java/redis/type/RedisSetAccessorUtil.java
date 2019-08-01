package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;


public class RedisSetAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisSetAccessorUtil.class);

    /**
     * 向名称为key的set中添加元素member
     *
     * @param key
     * @param member
     */
    public static boolean sadd(String key, String... member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.sadd(key, member) == 1L;
        } catch (Exception e) {
            logger.error("Set sadd fail ", e);
        }
        return false;
    }

    /**
     * 删除名称为key的set中的元素member
     *
     * @param key
     * @param member
     */
    public static boolean srem(String key, String... member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.srem(key, member) == 1L;
        } catch (Exception e) {
            logger.error("Set srem fail ", e);
        }
        return false;
    }

    /**
     * 返回并删除名称为key的set中一个元素
     *
     * @param key
     */
    public static String spop(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.spop(key);
        } catch (Exception e) {
            logger.error("Set spop fail ", e);
        }
        return null;
    }

    public boolean del(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = RedisInit.getShardedJedis();
            return shardedJedis.del(key) == 1L;
        } catch (Exception e) {
            logger.error("Set spop fail ", e);
        }
        return false;
    }
}
