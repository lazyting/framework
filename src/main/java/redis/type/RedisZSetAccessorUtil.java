package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;

import java.util.Set;

public class RedisZSetAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisZSetAccessorUtil.class);

    /**
     * 向名称为key的zset中添加元素member，score用于排序。如果该元素已经存在，则根据score更新该元素的顺序。
     *
     * @param key
     * @param score
     * @param member
     */
    public static boolean zadd(String key, double score, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zadd(key, score, member); //1成功；0失败
            return res == 1L;
        } catch (Exception e) {
            logger.error("Hash sadd fail ", e);
        }
        return false;
    }

    /**
     * 删除名称为key的zset中的元素member
     *
     * @param key
     * @param member
     * @return
     */
    public static boolean zrem(String key, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zrem(key, member); //1成功；0失败
            return res == 1L;
        } catch (Exception e) {
            logger.error("ZSet zrem fail ", e);
        }
        return false;
    }

    /**
     * 如果在名称为key的zset中已经存在元素member，则该元素的score增加increment；否则向集合中添加该元素，其score的值为increment
     *
     * @param key
     * @param increment
     * @param member
     * @return
     */
    public static Double zincrby(String key, double increment, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Double res = shardedJedis.zincrby(key, increment, member);//返回增加后的score，不存在数据就插入数据返回increment
            return res;
        } catch (Exception e) {
            logger.error("ZSet zincrby fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset（元素已按score从小到大排序）中member元素的index（从0开始），若没有member元素，返回“null”
     *
     * @param key
     * @param member
     */
    public static Long zrank(String key, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zrank(key, member);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrank fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset（元素已按score从大到小排序）中member元素的index（从0开始），若没有member元素，返回“null”
     *
     * @param key
     * @param member
     * @return
     */
    public static Long zrevrank(String key, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zrevrank(key, member);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrevrank fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素(从0开始，全闭区间)
     *
     * @param key
     * @param start
     * @param end
     */
    public static Set<String> zrange(String key, long start, long end) {
        if (start < 0 || end < start) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Set<String> res = shardedJedis.zrange(key, start, end);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrange fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素（从0开始，全闭区间）
     *
     * @param key
     * @param start
     * @param end
     */
    public static Set<String> zrevrange(String key, int start, int end) {
        if (start < 0 || end < start) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Set<String> res = shardedJedis.zrevrange(key, start, end);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrevrange fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset中score >= min且score <= max的所有元素
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<String> zrangeByScore(String key, double min, double max) {
        if (min < 0 || max < min) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Set<String> res = shardedJedis.zrangeByScore(key, min, max);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrangebyscore fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset的值的数量
     *
     * @param key
     */
    public static Long zcard(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zcard(key);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zcard fail ", e);
        }
        return null;
    }

    /**
     * 返回名称为key的zset中元素element的score
     *
     * @param key
     * @param element
     */
    public static Double zscore(String key, String element) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Double res = shardedJedis.zscore(key, element);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zscore fail ", e);
        }
        return null;
    }

    /**
     * 删除名称为key的zset中index >= min且index <= max的所有元素，返回删除的数量
     *
     * @param key
     * @param min
     * @param max
     */
    public static Long zremrangeByRank(String key, long min, long max) {
        if (min < 0 || max < min) {
            return 0L;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zremrangeByRank(key, min, max);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zrangebyscore fail ", e);
        }
        return 0L;
    }

    /**
     * 删除名称为key的zset中score >= min且score <= max的所有元素，返回删除的数量
     *
     * @param key
     * @param start
     * @param end
     */
    public static Long zremrangeByScore(String key, int start, int end) {
        if (start < 0 || end < start) {
            return 0L;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long res = shardedJedis.zremrangeByScore(key, start, end);
            return res;
        } catch (Exception e) {
            logger.error("ZSet zremrangebyscore fail ", e);
        }
        return 0l;
    }
}
