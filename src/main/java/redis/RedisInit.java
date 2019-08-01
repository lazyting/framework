package redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import system.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class RedisInit {
    private static ShardedJedisPool shardedJedisPool;
    private static int maxTotal = SystemConfig.getIntProperty("redis.maxtotal"); //最多实例数
    private static int maxIdle = SystemConfig.getIntProperty("redis.maxidle"); //最多空闲数
    private static int maxWait = SystemConfig.getIntProperty("redis.maxwait"); //最大等待数 毫秒级
    private static String redisMasterHost = SystemConfig.getProperty("redis.master.host");
    private static String redisMasterPassword = SystemConfig.getProperty("redis.master.password");

    /**
     * 初始化redis连接池
     */
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);

        List<JedisShardInfo> shardInfos = new ArrayList<>();
        JedisShardInfo shardInfo = new JedisShardInfo(redisMasterHost.trim());
        shardInfo.setPassword(redisMasterPassword);
        shardInfos.add(shardInfo);
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shardInfos);


    }

    /**
     * 获取单个jedis实例
     *
     * @return
     */
    public static ShardedJedis getShardedJedis() {
        return shardedJedisPool.getResource();
    }

    /**
     * 归还jedis实例
     *
     * @param shardedJedis
     */
    public static void returnResourceObject(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResourceObject(shardedJedis);
    }
}
