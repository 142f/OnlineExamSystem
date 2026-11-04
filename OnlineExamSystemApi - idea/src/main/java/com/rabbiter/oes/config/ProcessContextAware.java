package com.rabbiter.oes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动时释放端口占用（开发便利功能）。
 *
 * 原实现有两处风险：
 * 1) 默认开启，且用 findstr 做“包含匹配”，凡是行内出现端口数字的进程（含无关 PID、
 *    甚至已建立的对端连接）都会被 taskkill /F；解析失败时拿到的是空串或表头字段；
 * 2) 未排除自身进程，devtools 热重启场景可能把自己杀掉。
 *
 * 现改为：默认关闭（app.kill-port-on-startup=true 才启用），只处理真正处于
 * LISTENING 状态、且不是本进程的 PID。
 */
@Component
public class ProcessContextAware implements ServletContextAware {

    private static final Logger log = LoggerFactory.getLogger(ProcessContextAware.class);

    @Value("${server.port}")
    private String port;

    /**
     * 是否在启动时结束占用当前端口的进程，默认 false。
     */
    @Value("${app.kill-port-on-startup:false}")
    private boolean killPortOnStartup;

    @Override
    public void setServletContext(ServletContext servletContext) {
        if (!killPortOnStartup) {
            return;
        }
        String os = System.getProperty("os.name", "").toLowerCase();
        try {
            if (os.contains("win")) {
                killOnWindows();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("/bin/bash", "-c", "lsof -ti:" + port + " | xargs -r kill -9").start();
            }
        } catch (Exception e) {
            log.warn("释放端口 {} 失败：{}", port, e.getMessage());
        }
    }

    private void killOnWindows() throws Exception {
        Process process = new ProcessBuilder("cmd.exe", "/c", "netstat -ano -p tcp").start();
        List<String> pids = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.startsWith("TCP")) {
                    continue;
                }
                String[] tokens = trimmed.split("\\s+");
                // 形如：TCP  0.0.0.0:9201  0.0.0.0:0  LISTENING  12345
                if (tokens.length < 5 || !"LISTENING".equalsIgnoreCase(tokens[3])) {
                    continue;
                }
                if (!tokens[1].endsWith(":" + port)) {
                    continue;
                }
                String pid = tokens[tokens.length - 1];
                if (!pid.matches("\\d+")) {
                    continue;
                }
                pids.add(pid);
            }
        }
        process.waitFor();
        String selfPid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        for (String pid : pids) {
            if (pid.equals(selfPid)) {
                continue;
            }
            log.warn("端口 {} 已被进程 {} 占用，按配置结束该进程", port, pid);
            new ProcessBuilder("cmd.exe", "/c", "taskkill /F /PID " + pid).start();
        }
    }
}
