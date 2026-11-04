package com.rabbiter.oes.security;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录失败限流（内存实现）。
 *
 * 同一账号 + 来源 IP 连续失败 {@value #MAX_ATTEMPTS} 次后锁定一段时间，
 * 期间直接拒绝登录尝试，避免在线暴力破解。重启后计数清空，属于轻量防护；
 * 若部署多实例，需替换为 Redis 等共享存储。
 */
@Component
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;

    private static final long LOCK_MILLIS = 5 * 60 * 1000L;

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        Attempt attempt = attempts.get(key);
        if (attempt == null) {
            return false;
        }
        // 尚未触发锁定：保留失败计数（lockedUntil 为 0 时不能清理，否则计数永远累积不起来）
        if (attempt.lockedUntil == 0L) {
            return false;
        }
        if (attempt.lockedUntil <= System.currentTimeMillis()) {
            attempts.remove(key);
            return false;
        }
        return true;
    }

    public long remainingSeconds(String key) {
        Attempt attempt = attempts.get(key);
        if (attempt == null) {
            return 0L;
        }
        long remain = (attempt.lockedUntil - System.currentTimeMillis()) / 1000L;
        return remain > 0 ? remain : 0L;
    }

    public void loginFailed(String key) {
        attempts.compute(key, (k, old) -> {
            Attempt attempt = old == null ? new Attempt() : old;
            attempt.failures++;
            if (attempt.failures >= MAX_ATTEMPTS) {
                attempt.lockedUntil = System.currentTimeMillis() + LOCK_MILLIS;
                attempt.failures = 0;
            }
            return attempt;
        });
    }

    public void loginSucceeded(String key) {
        attempts.remove(key);
    }

    private static final class Attempt {
        private int failures;
        private long lockedUntil;
    }
}
