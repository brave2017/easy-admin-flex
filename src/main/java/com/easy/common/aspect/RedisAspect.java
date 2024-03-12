package com.easy.common.aspect;

import com.easy.common.exception.ErrorCode;
import com.easy.common.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 *
 * @author Maw
 */
@Aspect
@Component
public class RedisAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    @Value("${easy-admin.redis.open: false}")
    private boolean open;

    @Around("execution(* com.easy.common.redis.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                logger.error("redis error", e);
                throw new ServiceException(ErrorCode.REDIS_ERROR);
            }
        }
        return result;
    }
}
