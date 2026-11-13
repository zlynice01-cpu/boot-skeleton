package com.zly.skeleton.common.web.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.InputStream;

/**
 *
 * @author ZLY
 * @since 2026/11/12 17:24
 */
@Slf4j
public class HttpUtils
{
    private static Searcher searcher;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("ip2region/ip2region.xdb");
            try (InputStream is = resource.getInputStream()) {
                byte[] buff = is.readAllBytes();
                // 内存模式，最快，推荐
                searcher = Searcher.newWithBuffer(buff);
            }
        } catch (Exception e) {
            log.error("ip2region初始化失败", e);
        }
    }

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static String getRequestMethod() {
        ServletRequestAttributes attributes = getRequestAttributes();
        return attributes == null ? "" : attributes.getRequest().getMethod();
    }

    public static String getRequestUri() {
        ServletRequestAttributes attributes = getRequestAttributes();
        return attributes == null ? "" : attributes.getRequest().getRequestURI();
    }

    public static String getServletUri() {
        ServletRequestAttributes attributes = getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        HttpServletRequest request = attributes.getRequest();
        return request.getServletPath();
    }

    public static String getRequestIp() {
        ServletRequestAttributes attributes = getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        HttpServletRequest request = attributes.getRequest();
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }

    /**
     * 根据ip获取地址，返回：国家 省份 城市 运营商
     * @param ip 客户端ip
     * @return 地址信息，失败返回空串
     */
    public static String getIpAddress(String ip) {
        if (searcher == null || ip == null || "127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return "内网";
        }
        try {
            // 格式：中国|0|河南省|郑州市|联通
            String region = searcher.search(ip);
            if (region == null) {
                return "未知";
            }
            // 简单清洗，去掉0
            return region.replace("|0", "").replace("0|", "");
        } catch (Exception e) {
            log.warn("ip解析失败 ip:{}", ip, e);
            return "未知";
        }
    }
}
