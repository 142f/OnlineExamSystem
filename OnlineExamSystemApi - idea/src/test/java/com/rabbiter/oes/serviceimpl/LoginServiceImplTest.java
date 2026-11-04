package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.mapper.LoginMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 登录服务单元测试：验证 BCrypt 校验、历史明文兼容与自动升级。
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginServiceImplTest {

    @Mock
    private LoginMapper loginMapper;

    @InjectMocks
    private LoginServiceImpl loginService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void injectEncoder() {
        ReflectionTestUtils.setField(loginService, "passwordEncoder", encoder);
    }

    @Test
    public void bcryptPasswordMatches() {
        Admin admin = new Admin();
        admin.setAdminId(9991);
        when(loginMapper.findAdminPwd(9991)).thenReturn(encoder.encode("123456"));
        when(loginMapper.findAdminById(9991)).thenReturn(admin);

        Admin result = loginService.adminLogin(9991, "123456");

        assertNotNull(result);
        verify(loginMapper, never()).updateAdminPwd(any(), anyString());
    }

    @Test
    public void bcryptPasswordRejectsWrongInput() {
        when(loginMapper.findAdminPwd(9991)).thenReturn(encoder.encode("123456"));

        assertNull(loginService.adminLogin(9991, "bad-password"));
        verify(loginMapper, never()).findAdminById(any());
    }

    @Test
    public void legacyPlaintextPasswordWorksAndGetsUpgraded() {
        Admin admin = new Admin();
        admin.setAdminId(9991);
        when(loginMapper.findAdminPwd(9991)).thenReturn("123456");
        when(loginMapper.findAdminById(9991)).thenReturn(admin);

        Admin result = loginService.adminLogin(9991, "123456");

        assertNotNull(result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(loginMapper).updateAdminPwd(eq(9991), captor.capture());
        String stored = captor.getValue();
        assertTrue(stored.startsWith("$2"), "升级后的密码应为 BCrypt 密文");
        assertTrue(encoder.matches("123456", stored));
    }

    @Test
    public void nullInputReturnsNull() {
        assertNull(loginService.adminLogin(null, "123456"));
        assertNull(loginService.adminLogin(9991, null));
        verify(loginMapper, never()).findAdminPwd(any());
    }

    @Test
    public void missingUserReturnsNull() {
        when(loginMapper.findStudentPwd(20224001)).thenReturn(null);
        assertNull(loginService.studentLogin(20224001, "123456"));
    }

    @Test
    public void studentPasswordVerificationUsesSameRules() {
        when(loginMapper.findStudentPwd(20224001)).thenReturn(encoder.encode("abc123"));
        assertNull(loginService.studentLogin(20224001, "123456"));
        assertEquals(null, loginService.studentLogin(20224001, "wrong"));
    }
}
