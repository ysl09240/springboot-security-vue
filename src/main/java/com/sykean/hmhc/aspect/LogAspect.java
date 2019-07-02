package com.sykean.hmhc.aspect;

import com.sykean.hmhc.util.IPUtils;
import com.sykean.hmhc.util.JSONUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 日志打印
 */

@Aspect
@Order(3)
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义一个切入点
     */
    @Around("execution(* com.sykean.hmhc.controller..*.*(..))")
    private Object saveLog(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // URL 路径
        String url = request.getRequestURL().toString();
        String random = RandomStringUtils.randomAlphanumeric(8);
        //参数
        Object[] objects = point.getArgs();
        Object[] params = new Object[objects.length];
        //ip
        String ip = IPUtils.getIpAddr(request);
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Model) {
                continue;
            } else if (objects[i] instanceof HttpSession) {
                continue;
            } else if (objects[i] instanceof HttpServletRequest) {
                continue;
            } else if (objects[i] instanceof HttpServletResponse) {
                continue;
            } else if (objects[i] instanceof MultipartFile) {
                continue;
            }
            params[i] = objects[i];
        }
        logger.info("controller begin: request url：" + url + ", params:" + JSONUtils.beanToJson(params) + ", ip:" + ip
                + ", random: " + random);
        //计算方法执行时间
        StopWatch watch = new StopWatch();
        watch.start();
        Object object = point.proceed();
        watch.stop();
        long runTime = watch.getTotalTimeMillis();
        String returnData = JSONUtils.beanToJson(object);
        logger.info("controller end:request url: " + url + "，runtime: " + runTime + " ms, ip:" + ip + ", random: "
                + random + ", return data:" + returnData);
        return object;
    }
}
