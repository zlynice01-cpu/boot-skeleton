package com.zly.skeleton.common.web.util;

import com.alibaba.fastjson2.JSON;
import com.zly.skeleton.common.web.constants.HttpConstants;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * HTTP 响应工具类
 *
 * @author ZLY
 */
@Slf4j
public class ResponseUtils
{

    private ResponseUtils()
    {
        // 私有构造器，防止实例化
    }

    /**
     * 直接向客户端输出 JSON 响应
     *
     * @param response 响应对象
     * @param result   统一返回结果对象
     */
    public static void writeJson(HttpServletResponse response, Result<?> result)
    {
        try
        {
            // 1. 设置响应头和状态码
            response.setStatus(HttpConstants.STATUS_CODE_OK);
            response.setCharacterEncoding(HttpConstants.CHARSET_UTF8);
            response.setContentType(HttpConstants.CONTENT_TYPE_JSON_UTF8);
            // 2. 序列化对象并写出
            String json = JSON.toJSONString(result);
            try (PrintWriter out = response.getWriter())
            {
                out.write(json);
                out.flush();
            }
        } catch (IOException e)
        {
            log.error("写入HTTP响应失败, result: {}", result, e);
            // 在 Filter 中如果连响应都写不出去，通常只能抛出运行时异常中断请求
            throw new RuntimeException("写入HTTP响应失败");
        }
    }

    /**
     * 直接向客户端输出图片响应
     *
     * @param response 响应对象
     * @param image    图片对象
     */
    public static void writeImage(HttpServletResponse response, byte[] image)
    {
        // 1. 设置响应头和状态码
        response.setStatus(HttpConstants.STATUS_CODE_OK);
        response.setCharacterEncoding(HttpConstants.CHARSET_UTF8);
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON_UTF8);

        // 2. 序列化对象并写出
        response.setContentType(HttpConstants.CONTENT_TYPE_IMG_JPEG);
        response.setContentLength(image.length);
        try (ServletOutputStream out = response.getOutputStream())
        {
            out.write(image);
            out.flush();
        } catch (IOException e)
        {
            log.error("写入HTTP响应失败, image: {}", image, e);
            // 在 Filter 中如果连响应都写不出去，通常只能抛出运行时异常中断请求
            throw new RuntimeException("写入HTTP响应失败");
        }
    }

}