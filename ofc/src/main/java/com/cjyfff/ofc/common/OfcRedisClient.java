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
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/6.
 */
@Component
@Slf4j
public class OfcRedisClient {
    private RedissonClient redisson;

    private void getRedissionClient() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);
    }

    public LockObject tryLock(long waitTime, long leaseTime, TimeUnit unit, String lockKey) throws Exception {
        if (redisson == null) {
            getRedissionClient();
        }

        final RLock lock = redisson.getLock(lockKey);

        boolean res =  lock.tryLock(waitTime, leaseTime, unit);

        return new LockObject(lock, res);
    }

    public void tryUnLock(LockObject lockObject) {
        if (redisson == null) {
            log.warn("redisson is null");
            return;
        }

        if (lockObject == null) {
            log.warn("lockObject is null");
            return;
        }

        if (! lockObject.lockSuccess) {
            log.warn("lockObject.lockResult is false");
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
