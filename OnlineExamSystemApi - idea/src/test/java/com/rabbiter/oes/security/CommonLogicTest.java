package com.rabbiter.oes.security;

import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.util.ApiResultHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 公共组件逻辑测试：BCrypt 密文识别、统一返回结构、空值边界。
 * 这些是跨模块复用的基础逻辑，任何改动都会影响全站，因此单独覆盖。
 */
public class CommonLogicTest {

    @Test
    public void bcryptCipherIsRecognized() {
        String cipher = "$2a$10$WqcbjOiItLfnRA.wog3mu.F7lvAAqcK1zXX4yaLAEwVYns0dzzx0C";
        assertTrue(PasswordSupport.isEncoded(cipher));
    }

    @Test
    public void plainTextIsNotTreatedAsCipher() {
        assertFalse(PasswordSupport.isEncoded("123456"));
        assertFalse(PasswordSupport.isEncoded("$2a$10$short"));
        assertFalse(PasswordSupport.isEncoded(null));
    }

    @Test
    public void successResultCarriesCodeAndMessage() {
        ApiResult result = ApiResultHandler.success("payload");
        assertEquals(200, result.getCode());
        assertEquals("请求成功", result.getMessage());
        assertEquals("payload", result.getData());
    }

    @Test
    public void apiResultEqualsHandlesNullMessageAndData() {
        ApiResult a = new ApiResult();
        ApiResult b = new ApiResult();
        // 修复前：message / data 为 null 时 equals 抛 NPE
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void buildApiResultKeepsGivenValues() {
        ApiResult result = ApiResultHandler.buildApiResult(400, "参数错误", null);
        assertEquals(400, result.getCode());
        assertEquals("参数错误", result.getMessage());
        assertNull(result.getData());
        assertNotNull(result.toString());
    }
}
