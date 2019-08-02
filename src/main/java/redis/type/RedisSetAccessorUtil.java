package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;


public class RedisSetAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisSetAccessorUtil.class);

    /**
     * 向名称为key的set中添加元素member，返回插入的数据个数
     *
     * @param key
     * @param member
     */
    public static Long sadd(String key, String... member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.sadd(key, member);
            return res;
        } catch (Exception e) {
            logger.error("Set sadd fail ", e);
        }
        return null;
    }

    /**
     * 删除名称为key的set中的元素member，返回删除的数据个数
     *
     * @param key
     * @param member
     */
    public static Long srem(String key, String... member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.srem(key, member);
            return res;
        } catch (Exception e) {
            logger.error("Set srem fail ", e);
        }
        return null;
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
    /**
     * 删除一个String key
     *
     * @param key
     */
    public static boolean del(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.del(key); //1:success 0:faile
            return result == 1L;
        } catch (Exception e) {
            logger.error("String key delete faile " + e);
        }
        return false;
    }
}
