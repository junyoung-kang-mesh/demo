package com.study.aop.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAspect {
  @Around("@annotation(com.study.aop.demo.annotation.A)")
  public Object a(ProceedingJoinPoint pjp) throws Throwable {
    log.info("1");
    log.info("2");
    Object result = pjp.proceed();
    log.info("3");
    log.info("4");
    return result;
  }

  @Around("@annotation(com.study.aop.demo.annotation.B)")
  public Object b(ProceedingJoinPoint pjp) throws Throwable {
    log.info("5");
    log.info("6");
    Object result = pjp.proceed();
    log.info("7");
    log.info("8");
    return result;
  }
}
