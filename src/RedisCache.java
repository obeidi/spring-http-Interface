import redis.clients.jedis.Jedis;
import java.io.Serializable;
import java.util.*;

public class RedisCache implements Cache {
    private final Jedis jedis;
    
    public RedisCache(String redisHost, int redisPort) {
        this.jedis = new Jedis(redisHost, redisPort);
    }
    
    @Override
    public void activateApplicationCache() throws CacheException {
        // Redis benötigt keine explizite Aktivierung
    }
    
    @Override
    public void inactiveApplicationCache() throws CacheException {
        jedis.close();
    }
    
    @Override
    public Collection<String> getAllApplication() {
        return jedis.keys("*");
    }
    
    @Override
    public long getSize() throws CacheException {
        return jedis.dbSize();
    }
    
    @Override
    public Serializable getCacheValue(String pApplicationType, Serializable pKey) throws CacheException {
        return (Serializable) jedis.hget(pApplicationType, pKey.toString());
    }
    
    @Override
    public void setCacheValue(String pApplicationType, Serializable pKey, Serializable pValue) throws CacheException {
        jedis.hset(pApplicationType, pKey.toString(), pValue.toString());
    }
    
    @Override
    public Map<String, String> getCacheMap(String pApplicationType) throws CacheException {
        return jedis.hgetAll(pApplicationType);
    }
    
    @Override
    public void clearCache(String pApplicationType) {
        jedis.del(pApplicationType);
    }
    
    @Override
    public void printCache() {
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("Cache Key: " + key + " -> Value: " + jedis.hgetAll(key));
        }
    }
}
