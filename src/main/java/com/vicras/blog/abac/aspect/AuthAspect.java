package com.vicras.blog.abac.aspect;

import com.vicras.blog.abac.aspect.annotation.AbacSecure;
import com.vicras.blog.abac.aspect.service.AspectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final AspectService service;

    @Around(value = "com.vicras.blog.abac.aspect.pointcut.AbacUsePointcuts.abacSecure(abacSecure)",
            argNames = "pjp,abacSecure")
    Object docServiceAspect(ProceedingJoinPoint pjp, AbacSecure abacSecure) throws Throwable {
        return service.checkRights(pjp);
    }
}
