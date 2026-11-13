package com.zly.skeleton.core.aspect;

import com.alibaba.fastjson2.JSON;
import com.zly.skeleton.common.web.annotation.OperLog;
import com.zly.skeleton.common.web.util.HttpUtils;
import com.zly.skeleton.core.entity.OperLogInfo;
import com.zly.skeleton.core.handler.log.OperLogHandler;
import com.zly.skeleton.core.utils.JoinPointUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect
{
    private final OperLogHandler operLogHandler;

    @Pointcut("@annotation(com.zly.skeleton.common.web.annotation.OperLog)")
    public void pointCut()
    {
    }

    // 环绕通知：最强，包裹目标方法，可以控制是否执行、修改入参、修改返回值
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
        long start = System.currentTimeMillis();
        OperLog operLogAnn = JoinPointUtil.getMethodAnnotation(pjp, OperLog.class);
        OperLogInfo operLogInfo = createOperLogInfo(pjp, operLogAnn);
        try
        {
            Object proceed = pjp.proceed();
            operLogInfo.setCostTime(System.currentTimeMillis() - start);
            boolean saveResult = operLogAnn.isSaveResult();
            if (saveResult) {
                operLogInfo.setJsonResult(JSON.toJSONString(proceed));
            }
            return proceed;
        } catch (Exception e)
        {
            log.error("[LogAspect.around]-异常:{}", e.getMessage(), e);
            operLogInfo.setErrorMsg(e.getMessage());
            operLogInfo.setStatus(0);
            throw e;
        } finally
        {
            operLogHandler.handle(operLogInfo);
        }
    }

    private OperLogInfo createOperLogInfo(ProceedingJoinPoint pjp, OperLog operLogAnn)
    {
        Method method = JoinPointUtil.getMethod(pjp);
        String name = method.getName();
        String title = operLogAnn.title();
        int businessType = operLogAnn.businessType();
        boolean saveParam = operLogAnn.isSaveParam();
        String requestMethod = HttpUtils.getRequestMethod();
        String requestIp = HttpUtils.getRequestIp();
        String requestUri = HttpUtils.getRequestUri();
        String ipAddress = HttpUtils.getIpAddress(requestIp);

        OperLogInfo operLogInfo = OperLogInfo.builder().method(name).title(title)
                .requestMethod(requestMethod).businessType(businessType).operIp(requestIp)
                .operLocation(ipAddress).operUrl(requestUri).status(1).build();

        if (saveParam) {
            String methodParam = JoinPointUtil.getMethodParam(pjp);
            operLogInfo.setOperParam(methodParam);
        }

        return operLogInfo;

    }
}
