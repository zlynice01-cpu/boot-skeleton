package com.zly.skeleton.core.aspect;

import com.alibaba.fastjson2.JSON;
import com.zly.skeleton.common.web.util.HttpUtils;
import com.zly.skeleton.core.utils.JoinPointUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * @author ZLY
 * @since 2026/11/12 16:13
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ParamLogAspect
{
    @Pointcut("execution(* com.zly.skeleton.core.controller.**.*.*(..))")
    public void pointCut()
    {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
        String methodParam = JoinPointUtil.getMethodParam(pjp);
        Method method = JoinPointUtil.getMethod(pjp);
        String name = method.getName();
        String servletUri = HttpUtils.getServletUri();

        log.info("[ParamLogAspect.around]-{},方法名:{},入参:{}", servletUri, name, methodParam);
        try
        {
            Object res = pjp.proceed();
            String param = null;
            try
            {
                param = JSON.toJSONString(res);
            } catch (Exception e)
            {
                log.warn("[ParamLogAspect.around]-{},方法名:{},出参序列化失败", servletUri, name, e);
            }
            log.info("[ParamLogAspect.around]-{},方法名:{},出参:{}", servletUri, name, param);
            return res;
        } catch (Exception e)
        {
            log.error("[ParamLogAspect.around]-{},方法名:{},异常", servletUri, name, e);
            throw e;
        }
    }

}
