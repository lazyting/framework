package redis.type;

import org.apache.log4j.Logger;
import redis.RedisInit;
import redis.clients.jedis.ShardedJedis;

public class RedisStringAccessorUtil {
    private static Logger logger = Logger.getLogger(RedisStringAccessorUtil.class);

    /**
     * 确认一个String key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.exists(key);
        } catch (Exception e) {
            logger.error("操作失败：" + e);
        }
        return false;
    }

    /**
     * 删除一个String key
     *
     * @param key
     */
    public static void del(String key) {

    }

    /**
     * 给数据库中名称为key的string赋予值value
     *
     * @param key
     * @param value
     */
    public static boolean set(String key, String value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            String result = shardedJedis.set(key, value);
            return result.equalsIgnoreCase("OK");
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 返回数据库中名称为key的string的value
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            String result = shardedJedis.get(key);
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            logger.error("获取出错：" + e);
        }
        return null;
    }

    /**
     * 如果不存在名称为key的string，则向库中添加string，名称为key，值为value
     *
     * @param key
     * @param value
     */

    public static void setnx(String key, String value) {
    }

    /**
     * 向库中添加string（名称为key，值为value）同时，设定过期时间time
     *
     * @param key
     * @param time
     * @param value
     */
    public static void setex(String key, Integer time, String value) {
    }

    /**
     * 名称为key的string的值附加value
     *
     * @param key
     * @param value
     */
    public static void append(String key, String value) {
    }

    /**
     * 返回名称为key的string的value的子串
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static String substr(String key, int start, int end) {
        return null;
    }

    //    mget(String key1, key2,…, key N); //返回库中多个string（它们的名称为key1，key2…）的value
    //    getset(String key, String value); //给名称为key的string赋予上一次的value
    //    decrby(String key, integer); //名称为key的string减少integer
    //    decr(String key); //名称为key的string减1操作
    //    incrby(String key, integer); //名称为key的string增加integer
    //    incr(String key); //名称为key的string增1操作
    //    msetnx(String key1, value1, key2, value2,…key N, value N); //如果所有名称为key i的string都不存在，则向库中添加string，名称           key i赋值为value i
    //    mset(String key1, value1, key2, value2,…key N, value N); //同时给多个string赋值，名称为key i的string赋值value i
}