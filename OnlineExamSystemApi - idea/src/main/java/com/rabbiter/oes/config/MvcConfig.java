package com.rabbiter.oes.config;

import com.rabbiter.oes.security.RoleAuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MvcConfig.class);

    /**
     * 允许的前端来源，多个用逗号分隔。
     * 原实现为 "*" 且 allowCredentials=true，等于允许任意站点携带凭据发起请求；
     * 现改为显式白名单，部署时通过 app.cors.allowed-origins 覆盖。
     */
    @Value("${app.cors.allowed-origins:http://localhost:9202,http://127.0.0.1:9202,http://localhost:9201,http://127.0.0.1:9201}")
    private String allowedOrigins;

    /**
     * 注册登录态 + 角色鉴权拦截器：
     * 除登录、登出外的所有接口都需要有效会话；越权访问返回 403。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RoleAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout", "/error");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> origins = new ArrayList<>();
        for (String origin : allowedOrigins.split(",")) {
            if (!origin.trim().isEmpty()) {
                origins.add(origin.trim());
            }
        }
        log.info("CORS 允许来源: {}", origins);
        config.setAllowedOrigins(origins);
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
