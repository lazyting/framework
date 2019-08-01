package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;

public class RedisZSetAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisZSetAccessorUtil.class);
    /**
     * //向名称为key的zset中添加元素member，score用于排序。如果该元素已经存在，则根据score更新该元素的顺序。
     * @param key
     * @param score
     * @param member
     */
    public static boolean zadd(String key, double score, String member) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.zadd(key,score,member)==1L;
        } catch (Exception e) {
            logger.error("Hash sadd fail " , e);
        }
        return false;
    }

    public static void zrem(String key, String member) {
    } //删除名称为key的zset中的元素member

    public static void zincrby(String key, int increment, String member) {
    } //如果在名称为key的zset中已经存在元素member，则该元素的score增加increment；否则向集合中添加该元素，其score的值为increment

    public static void zrank(String key, String member) {
    } //返回名称为key的zset（元素已按score从小到大排序）中member元素的rank（即index，从0开始），若没有member元素，返回“nil”

    public static void zrevrank(String key, String member) {
    } //返回名称为key的zset（元素已按score从大到小排序）中member元素的rank（即index，从0开始），若没有member元素，返回“nil”

    public static void zrange(String key, int start, int end) {
    } //返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素

    public static void zrevrange(String key, int start, int end) {
    } //返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素

    public static void zrangebyscore(String key, int min, int max) {
    } //返回名称为key的zset中score >= min且score <= max的所有元素

    public static void zcard(String key) {
    } //返回名称为key的zset的基数

    public static void zscore(String key, String element) {
    } //返回名称为key的zset中元素element的score

    public static void zremrangebyrank(String key, int min, int max) {
    } //删除名称为key的zset中rank >= min且rank <= max的所有元素

    public static void zremrangebyscore(String key, int min, int max) {
    } //删除名称为key的zset中score >= min且score <= max的所有元素
}
