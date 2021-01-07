package com.mainlevel.monitoring.common.monitoring.aspect;

import static java.lang.System.currentTimeMillis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.monitoring.jmx.PerformanceMonitorJMX;

/**
 * The performance aspect measures the time of an invocation and stores it in JMX.
 */
@Aspect
@Component
public class PerformanceAspect {

    @Autowired
    private PerformanceMonitorJMX performanceMonitorJMX;

    /**
     * Pointcut operation.
     */
    @Pointcut("@annotation(com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor)")
    protected void performanceMonitorPointcut() {

    }

    /**
     * Intercepts the given invocation.
     *
     * @param proceedingJoinPoint the joinpoint
     * @return the result
     * @throws Throwable when an error during invocation occurs
     */
    @Around("performanceMonitorPointcut()")
    protected Object monitor(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        performanceMonitorJMX.setMethodName(proceedingJoinPoint.getSignature().toShortString());

        final long startTime = currentTimeMillis();
        final Object result = proceedingJoinPoint.proceed();

        performanceMonitorJMX.setDuration(currentTimeMillis() - startTime);
        return result;
    }
}
