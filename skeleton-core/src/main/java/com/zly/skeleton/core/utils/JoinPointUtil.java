package com.zly.skeleton.core.utils;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ZLY
 * @since 2026/11/12 16:54
 */
public class JoinPointUtil
{
    public static Method getMethod(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getMethod();
    }

    public static Object[] getMethodArgs(ProceedingJoinPoint point)
    {
        return point.getArgs();
    }

    public static Parameter[] getParameters(ProceedingJoinPoint point)
    {
        Method method = getMethod(point);
        return method.getParameters();
    }

    public static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint point, Class<T> annotationClass)
    {
        Method method = getMethod(point);
        return method.getAnnotation(annotationClass);
    }

    public static String getMethodParam(ProceedingJoinPoint point)
    {
        Parameter[] parameters = getParameters(point);
        Object[] args = getMethodArgs(point);
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++)
        {
            if (parameters[i].getType().equals(HttpServletRequest.class))
            {
                paramMap.put(parameters[i].getName(), "HttpServletRequest");
            } else if (parameters[i].getType().equals(HttpServletResponse.class))
            {
                paramMap.put(parameters[i].getName(), "HttpServletResponse");
            } else
            {
                paramMap.put(parameters[i].getName(), args[i]);
            }
        }
        return JSON.toJSONString(paramMap);
    }

}
