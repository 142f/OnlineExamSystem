package com.rabbiter.oes.security;

/**
 * BCrypt 密文识别工具。
 *
 * Spring Security 的 BCryptPasswordEncoder 生成的密文形如：
 * $2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
 * 历史数据为明文，登录成功后可自动升级为密文。
 */
public final class PasswordSupport {

    private static final String BCRYPT_PREFIX = "$2";

    private PasswordSupport() {
    }

    /**
     * 是否已经是 BCrypt 密文。
     */
    public static boolean isEncoded(String password) {
        return password != null && password.startsWith(BCRYPT_PREFIX) && password.length() == 60;
    }
}
