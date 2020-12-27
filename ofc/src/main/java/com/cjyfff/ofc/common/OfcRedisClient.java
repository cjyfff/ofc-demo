package com.cjyfff.ofc.common;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/6.
 */
@Component
@Slf4j
public class OfcRedisClient {

    private RedissonClient redisson;

    public OfcRedisClient(@Value("${redis-address}") String redisAddress) {
        log.info("redission client init...");
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress(redisAddress);
        
        // 这里我根据((核心数 * 2) + 有效磁盘数)配置 min
        config.useSingleServer().setConnectionMinimumIdleSize(10);
        config.useSingleServer().setConnectionPoolSize(30);
        config.useSingleServer().setIdleConnectionTimeout(20000);
        config.useSingleServer().setConnectTimeout(10000);
        config.useSingleServer().setTimeout(10000);
        
        this.redisson = Redisson.create(config);
    }

    public LockObject tryLock(long waitTime, long leaseTime, TimeUnit unit, String lockKey) throws Exception {

        final RLock lock = this.redisson.getLock(lockKey);

        boolean res =  lock.tryLock(waitTime, leaseTime, unit);

        return new LockObject(lock, res);
    }

    public void tryUnLock(LockObject lockObject) {
        if (this.redisson == null) {
            log.warn("redisson is null");
            return;
        }

        if (lockObject == null) {
            log.info("lockObject is null");
            return;
        }

        if (! lockObject.lockSuccess) {
            log.info("lockObject.lockResult is false, do not need unlock.");
            return;
        }

        if (lockObject.getLock() == null) {
            log.warn("lockObject.getLock() is null");
            return;
        }

        lockObject.getLock().unlock();
    }


    @Getter
    @Setter
    public static class LockObject {
        public LockObject(RLock lock, boolean lockSuccess) {
            this.lock = lock;
            this.lockSuccess = lockSuccess;
        }

        private RLock lock;

        private boolean lockSuccess;
    }
}
