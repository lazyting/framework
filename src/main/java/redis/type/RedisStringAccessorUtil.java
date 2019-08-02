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
    public static boolean del(String key) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.del(key); //1:success 0:faile
            return result == 1L;
        } catch (Exception e) {
            logger.error("String key delete faile " + e);
        }
        return false;
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
            logger.error("String key set faile " + e);
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

    public static boolean setnx(String key, String value) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.setnx(key, value);//1:success 0:faile
            return result == 1L;
        } catch (Exception e) {
            logger.error("String key setnx faile " + e);
        }
        return false;
    }

    /**
     * 名称为key的string的值附加value
     * 返回结果的长度（一个汉字做3个字节）
     *
     * @param key
     * @param appendValue
     */
    public static Long append(String key, String appendValue) {
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            Long result = shardedJedis.append(key, appendValue);
            return result;
        } catch (Exception e) {
            logger.error("String key append faile " + e);
        }
        return null;
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
        if (start < 0 || end < start) {
            return null;
        }
        try (ShardedJedis shardedJedis = RedisInit.getShardedJedis()) {
            return shardedJedis.substr(key, start, end);
        } catch (Exception e) {
            logger.error("String key append faile " + e);
        }
        return null;
    }
}
