package com.rabbiter.oes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理。
 *
 * 说明：原实现直接对 e.getMessage() 做 contains 判断，异常message为 null 时
 * （例如 NullPointerException）会在处理器内部再次抛出 NPE，导致返回 Spring
 * 默认错误页并把堆栈信息暴露给前端；此处做空值保护，异常详情只写入日志。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 路径参数类型不匹配（例如 /api/daQuestionall/1/6/null）应返回 400，而不是 500。
     */
    @ExceptionHandler(value = org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<String> handleTypeMismatch(
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException e) {
        log.warn("请求参数类型错误: {}", e.getMessage());
        return new ResponseEntity<>("请求参数格式错误", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        log.error("请求处理失败: {}", e.getMessage(), e);
        return new ResponseEntity<>(buildFriendlyMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 404：Spring Boot 3 起，未匹配到任何 Controller 的请求会由静态资源处理器抛出
     * NoResourceFoundException；若仍落到上面的 Exception 兜底，会被改写成 500，
     * 与升级前（Boot 2.1 直接返回 404）的行为不一致，故单独处理。
     */
    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    @ResponseBody
    public ResponseEntity<String> handleNotFound(Exception e) {
        log.debug("资源不存在: {}", e.getMessage());
        return new ResponseEntity<>("请求的资源不存在", HttpStatus.NOT_FOUND);
    }

    /**
     * 保持与前端既有约定一致的提示文案（前端直接把响应体作为错误消息展示）。
     */
    private String buildFriendlyMessage(Exception e) {
        String raw = e.getMessage() == null ? "" : e.getMessage();
        if (raw.contains("(using password: YES)")) {
            if (raw.contains("'root'@'localhost'")) {
                return "P Request failed with status code 500";
            }
            return "PU Request failed with status code 500";
        } else if (raw.contains("Table") && raw.contains("doesn't exist")) {
            return "T Request failed with status code 500";
        } else if (raw.contains("Unknown database")) {
            return "U Request failed with status code 500";
        } else if (raw.contains("edis")) {
            return "R Request failed with status code 500";
        } else if (raw.contains("Failed to obtain JDBC Connection")) {
            return "C Request failed with status code 500";
        } else if (raw.contains("SQLSyntaxErrorException") || raw.contains("Unknown column")) {
            return "S Request failed with status code 500";
        }
        return "SYS Request failed with status code 500";
    }
}
